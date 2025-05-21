package ng.com.ninepsb.nibss_nip_lib.service;

import ng.com.ninepsb.nibss_nip_lib.config.NipProperties;

import java.io.IOException;
import java.nio.file.Path;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.spec.InvalidKeySpecException;

public interface NipKeyService {

    void ensureKeyExists(NipProperties.SecurityConfig security) throws IOException, NoSuchAlgorithmException;

    void generateAndSaveKeyPair(NipProperties.SecurityConfig security, Path keysDir)
            throws NoSuchAlgorithmException, IOException;

    PrivateKey loadPrivateKey(String path, String algorithm)
            throws IOException, NoSuchAlgorithmException, InvalidKeySpecException;

    PublicKey loadPublicKey(String path, String algorithm)
            throws IOException, NoSuchAlgorithmException, InvalidKeySpecException;
    PublicKey loadNibssPublicKey(String path) throws IOException;

}
