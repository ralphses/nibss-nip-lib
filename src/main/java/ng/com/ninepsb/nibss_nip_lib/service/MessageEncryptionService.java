package ng.com.ninepsb.nibss_nip_lib.service;

public interface MessageEncryptionService {
    String encrypt(String data);
    String decrypt(String encryptedData);
}
