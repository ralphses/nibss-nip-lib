package ng.com.ninepsb.nibss_nip_lib.service;

import java.security.KeyPair;
import java.security.NoSuchAlgorithmException;

public interface BankKeyManager {
    KeyPair generateKeyPair(String algorithm) throws NoSuchAlgorithmException;
}
