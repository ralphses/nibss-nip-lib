package ng.com.ninepsb.nibss_nip_lib.exception;

public class CryptographicKeyException extends RuntimeException {
    public CryptographicKeyException(String message) {
        super(message);
    }

    public CryptographicKeyException() {
        super("Failed to set up cryptographic keys");
    }

    public CryptographicKeyException(String message, Throwable cause) {
        super(message, cause);
    }
}
