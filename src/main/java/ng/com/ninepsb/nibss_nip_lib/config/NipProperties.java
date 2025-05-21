package ng.com.ninepsb.nibss_nip_lib.config;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.validation.annotation.Validated;

import java.util.HashSet;
import java.util.Set;

@Data
@Validated
@ConfigurationProperties(prefix = "nip")
public class NipProperties {

    /**
     * Whether the NIP integration library is enabled.
     * Default: true
     */
    public boolean enabled = true;

    /**
     * General configuration for the NIP integration.
     */
    @NotNull
    public GeneralConfig general = new GeneralConfig();

    /**
     * Client-side configuration for making requests to NIBSS.
     */
    @NotNull
    public ClientConfig client = new ClientConfig();

    /**
     * Web service configuration for receiving requests from NIBSS.
     */
    @NotNull
    public WebServiceConfig webService = new WebServiceConfig();

    /**
     * Security configuration for encryption, decryption, and key management.
     */
    @NotNull
    public SecurityConfig security = new SecurityConfig();

    @Data
    public static class GeneralConfig {
        @NotBlank
        public String bankCode;

        public boolean validateXmlSchema = true;

        @NotBlank
        public String xmlSchemaPath = "classpath:schema/";

        @NotBlank
        public String xmlNamespaceUri = "http://core.nip.nibss/";

        public boolean logXml = false;
    }

    @Data
    public static class ClientConfig {
        public boolean enabled = true;

        @NotBlank
        public String nipServiceUrl = "http://196.6.103.10:86/NIPWS/NIPInterface";

        @Positive
        public int connectionTimeoutMs = 30000;

        @Positive
        public int readTimeoutMs = 60000;

        @Positive
        public int maxRetries = 3;

        @Positive
        public int retryDelayMs = 1000;
    }

    @Data
    public static class WebServiceConfig {
        public boolean enabled = true;

        @NotBlank
        public String basePath = "/nip";

        public Set<String> exposedEndpoints = new HashSet<>(Set.of());
    }

    @Data
    public static class SecurityConfig {
        @NotBlank
        public String bankPrivateKeyPath = "classpath:keys/bank_private_key.pem";

        @NotBlank
        public String bankPublicKeyPath = "classpath:keys/bank_public_key.pem";

        @NotBlank
        public String nibssPublicKeyPath = "classpath:keys/nibss_public_key.pem";

        @NotBlank
        public String algorithm = "RSA";

        @NotBlank
        public String padding = "PKCS1Padding";

        @NotBlank
        public String keyFormat = "PEM";

        @Positive
        public int keySize = 2048;

        public boolean generateMissingKeys = false;

        public String generatedKeysDirectory = "keys";

        public boolean encryptAll = true;

        public Set<String> encryptedTransactionTypes = new HashSet<>();
    }
}
