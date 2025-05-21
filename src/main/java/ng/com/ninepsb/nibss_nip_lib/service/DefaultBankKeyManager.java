package ng.com.ninepsb.nibss_nip_lib.service;

import lombok.RequiredArgsConstructor;
import ng.com.ninepsb.nibss_nip_lib.config.NipProperties;
import org.springframework.stereotype.Component;

import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.NoSuchAlgorithmException;

@Component
@RequiredArgsConstructor
public class DefaultBankKeyManager implements BankKeyManager {
    private final NipProperties properties;

    @Override
    public KeyPair generateKeyPair(String algorithm) throws NoSuchAlgorithmException {
        KeyPairGenerator keyPairGenerator = KeyPairGenerator.getInstance(algorithm);
        keyPairGenerator.initialize(properties.getSecurity().getKeySize());
        return keyPairGenerator.generateKeyPair();
    }
}
