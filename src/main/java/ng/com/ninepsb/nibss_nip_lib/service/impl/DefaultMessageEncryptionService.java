package ng.com.ninepsb.nibss_nip_lib.service.impl;

import lombok.RequiredArgsConstructor;
import ng.com.ninepsb.nibss_nip_lib.model.NipKeyStore;
import ng.com.ninepsb.nibss_nip_lib.service.MessageEncryptionService;
import org.springframework.stereotype.Component;

import javax.crypto.Cipher;
import java.util.Base64;

@Component
@RequiredArgsConstructor
public class DefaultMessageEncryptionService implements MessageEncryptionService {
    private static final String RSA_TRANSFORMATION = "RSA/ECB/PKCS1Padding";

    private final NipKeyStore nipKeyStore;

    @Override
    public String encrypt(String data) {
        try {
            Cipher cipher = Cipher.getInstance(RSA_TRANSFORMATION);
            cipher.init(Cipher.ENCRYPT_MODE, nipKeyStore.getNibssPublicKey());

            byte[] encryptedBytes = cipher.doFinal(data.getBytes());
            return Base64.getEncoder().encodeToString(encryptedBytes);
        } catch (Exception e) {
            throw new SecurityException("Failed to encrypt data", e);
        }

    }

    @Override
    public String decrypt(String encryptedData) {
        try {
            Cipher cipher = Cipher.getInstance(RSA_TRANSFORMATION);
            cipher.init(Cipher.DECRYPT_MODE, nipKeyStore.getBankPrivateKey());

            byte[] encryptedBytes = Base64.getDecoder().decode(encryptedData);
            byte[] decryptedBytes = cipher.doFinal(encryptedBytes);
            return new String(decryptedBytes);
        } catch (Exception e) {
            throw new SecurityException("Failed to decrypt data", e);
        }
    }

}
