package ng.com.ninepsb.nibss_nip_lib.config;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import ng.com.ninepsb.nibss_nip_lib.exception.CryptographicKeyException;
import ng.com.ninepsb.nibss_nip_lib.model.NipKeyStore;
import ng.com.ninepsb.nibss_nip_lib.service.NipKeyService;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.oxm.jaxb.Jaxb2Marshaller;

@Slf4j
@Configuration
@RequiredArgsConstructor
@ConditionalOnProperty(prefix = "nip", name = "enabled", havingValue = "true", matchIfMissing = true)
public class NipConfigFactory {

    private final NipProperties properties;
    private final NipKeyService nipKeyService;

    @Bean
    public NipKeyStore keyStore() {
        log.info(":::Initializing NipKeyStore");

        var security = properties.getSecurity();
        NipKeyStore keyStore = new NipKeyStore();

        try {
            if (security.isGenerateMissingKeys()) {
                log.info(":::Attempting to ensure cryptographic keys exist and are valid...");
                nipKeyService.ensureKeyExists(security);
            }

            // The 'keyFormat' property is now re-included in NipConfigurationProperties.SecurityConfig
            // to support different key loading mechanisms.
            if (security.getKeyFormat().equalsIgnoreCase("PEM")) {
                log.info(":::Loading keys from PEM files...");
                keyStore.setBankPrivateKey(nipKeyService.loadPrivateKey(security.getBankPrivateKeyPath(), security.getAlgorithm()));
                keyStore.setBankPublicKey(nipKeyService.loadPublicKey(security.getBankPublicKeyPath(), security.getAlgorithm()));
                keyStore.setNibssPublicKey(nipKeyService.loadNibssPublicKey(security.getNibssPublicKeyPath()));
            } else {
                throw new CryptographicKeyException("Unsupported key format: " + security.getKeyFormat());
            }

        } catch (Exception e) {
            log.error(":::Failed to initialize cryptographic keys: {}", e.getMessage(), e);
            throw new CryptographicKeyException("Key store setup failed: " + e.getMessage(), e);
        }

        log.info(":::NipKeyStore initialized successfully.");
        return keyStore;
    }

    @Bean
    @ConditionalOnProperty(value = "nip.client.enabled", havingValue = "true")
    Jaxb2Marshaller jaxb2Marshaller() {
        return new Jaxb2Marshaller();
    }
}
