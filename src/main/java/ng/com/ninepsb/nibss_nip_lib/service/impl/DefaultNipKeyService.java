package ng.com.ninepsb.nibss_nip_lib.service.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import ng.com.ninepsb.nibss_nip_lib.config.NipProperties;
import ng.com.ninepsb.nibss_nip_lib.service.BankKeyManager;
import ng.com.ninepsb.nibss_nip_lib.service.NipKeyService;
import org.springframework.core.io.ResourceLoader;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.security.KeyFactory;
import java.security.KeyPair;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;
import java.util.Base64;

@Slf4j
@Component
@RequiredArgsConstructor
public class DefaultNipKeyService implements NipKeyService {

    private static final String BEGIN_PRIVATE_KEY = "-----BEGIN PRIVATE KEY-----";
    private static final String END_PRIVATE_KEY = "-----END PRIVATE KEY-----";
    private static final String BEGIN_PUBLIC_KEY = "-----BEGIN PUBLIC KEY-----";
    private static final String END_PUBLIC_KEY = "-----END PUBLIC KEY-----";
    private static final int LINE_LENGTH = 64; // Standard line length for PEM encoding

    private final ResourceLoader resourceLoader;
    private final BankKeyManager bankKeyManager;


    @Override
    public void ensureKeyExists(NipProperties.SecurityConfig security) throws IOException, NoSuchAlgorithmException {
        Path keysDir = Paths.get(security.getGeneratedKeysDirectory());
        if (!Files.exists(keysDir)) {
            Files.createDirectories(keysDir);
            log.info(":::Created key generation directory: {}", keysDir);
        }

        if (!fileExists(security.getBankPrivateKeyPath())) {
            log.warn(":::Generating new RSA key pair for bank (algorithm: {}, format: {})",
                    security.getAlgorithm(), security.getKeyFormat());
            generateAndSaveKeyPair(security, keysDir);
        } else {
            log.debug(":::Bank private key already exists at: {}", security.getBankPrivateKeyPath());
        }
    }

    @Override
    public void generateAndSaveKeyPair(NipProperties.SecurityConfig security, Path keysDir) throws NoSuchAlgorithmException, IOException {
        KeyPair keyPair = bankKeyManager.generateKeyPair(security.getAlgorithm());
        String keyExtension = security.getKeyFormat().toLowerCase();

        Path privateKeyPath = keysDir.resolve("bank_private_key." + keyExtension);
        Path publicKeyPath = keysDir.resolve("bank_public_key." + keyExtension);

        savePrivateKey(keyPair.getPrivate(), privateKeyPath);
        savePublicKey(keyPair.getPublic(), publicKeyPath);

        // Update the security config with the absolute paths
        security.setBankPrivateKeyPath(privateKeyPath.toAbsolutePath().toString());
        security.setBankPublicKeyPath(publicKeyPath.toAbsolutePath().toString());

        log.info(":::Bank key pair successfully generated and saved to: {}", keysDir.toAbsolutePath());
    }


    @Override
    public PrivateKey loadPrivateKey(String path, String algorithm) throws IOException, NoSuchAlgorithmException, InvalidKeySpecException {
        try (InputStream is = resourceLoader.getResource(path).getInputStream()) {
            return loadPrivateKey(is, algorithm);
        }
    }


    @Override
    public PublicKey loadPublicKey(String path, String algorithm) throws IOException, NoSuchAlgorithmException, InvalidKeySpecException {
        try (InputStream is = resourceLoader.getResource(path).getInputStream()) {
            return loadPublicKey(is, algorithm);
        }
    }

    @Override
    public PublicKey loadNibssPublicKey(String path) throws IOException {
        try (InputStream is = resourceLoader.getResource(path).getInputStream()) {
            return loadNibssPublicKey(is);
        }
    }

    private boolean fileExists(String path) {
        return resourceLoader.getResource(path).exists();
    }

    public PrivateKey loadPrivateKey(InputStream inputStream, String algorithm)
            throws IOException, NoSuchAlgorithmException, InvalidKeySpecException {

        String keyContent = extractKeyContent(inputStream, BEGIN_PRIVATE_KEY, END_PRIVATE_KEY);
        byte[] decoded = Base64.getDecoder().decode(keyContent);
        PKCS8EncodedKeySpec keySpec = new PKCS8EncodedKeySpec(decoded);
        KeyFactory factory = KeyFactory.getInstance(algorithm);

        return factory.generatePrivate(keySpec);
    }


    public PublicKey loadPublicKey(InputStream inputStream, String algorithm)
            throws IOException, NoSuchAlgorithmException, InvalidKeySpecException {

        String keyContent = extractKeyContent(inputStream, BEGIN_PUBLIC_KEY, END_PUBLIC_KEY);
        byte[] decoded = Base64.getDecoder().decode(keyContent);
        X509EncodedKeySpec keySpec = new X509EncodedKeySpec(decoded);
        KeyFactory factory = KeyFactory.getInstance(algorithm);

        return factory.generatePublic(keySpec);
    }


    public void savePrivateKey(PrivateKey privateKey, Path path) throws IOException {
        saveKeyToFile(privateKey.getEncoded(), BEGIN_PRIVATE_KEY, END_PRIVATE_KEY, path);
    }

    public void savePublicKey(PublicKey publicKey, Path path) throws IOException {
        saveKeyToFile(publicKey.getEncoded(), BEGIN_PUBLIC_KEY, END_PUBLIC_KEY, path);
    }

    private String extractKeyContent(InputStream inputStream, String begin, String end) throws IOException {
        String content = new String(inputStream.readAllBytes(), StandardCharsets.UTF_8);
        return content.replace(begin, "")
                .replace(end, "")
                .replaceAll("\\s+", "") // Remove all whitespace characters
                .strip(); // Remove leading/trailing whitespace specifically
    }

    private void saveKeyToFile(byte[] keyBytes, String begin, String end, Path path) throws IOException {
        String base64Encoded = Base64.getEncoder().encodeToString(keyBytes);
        StringBuilder builder = new StringBuilder(begin).append("\n");

        // Format the Base64 string with line breaks
        for (int i = 0; i < base64Encoded.length(); i += LINE_LENGTH) {
            int endIdx = Math.min(i + LINE_LENGTH, base64Encoded.length());
            builder.append(base64Encoded, i, endIdx).append("\n"); // Use substring for clarity
        }

        builder.append(end);
        Files.writeString(path, builder.toString(), StandardCharsets.UTF_8);
    }

    /**
     * Load the NIBSS public key from an InputStream.
     * TODO: Implement based on NIBSS key format.
     */
    private PublicKey loadNibssPublicKey(InputStream is) {
        throw new UnsupportedOperationException("Method not implemented yet");
    }
}