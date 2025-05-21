package ng.com.ninepsb.nibss_nip_lib.exception;

public class NipClientException extends RuntimeException{
    public NipClientException(String message) {
        super(message);
    }

    public NipClientException(String failedToProcessXml, Exception e) {
        super(failedToProcessXml, e);
    }
}
