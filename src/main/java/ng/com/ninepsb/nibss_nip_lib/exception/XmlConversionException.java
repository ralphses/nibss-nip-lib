package ng.com.ninepsb.nibss_nip_lib.exception;

/**
 * Exception thrown when XML conversion operations fail
 */
public class XmlConversionException extends Exception {

    /**
     * Constructs a new exception with the specified detail message
     *
     * @param message The detail message
     */
    public XmlConversionException(String message) {
        super(message);
    }

    /**
     * Constructs a new exception with the specified detail message and cause
     *
     * @param message The detail message
     * @param cause The cause of the exception
     */
    public XmlConversionException(String message, Throwable cause) {
        super(message, cause);
    }
}