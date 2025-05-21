package ng.com.ninepsb.nibss_nip_lib.model.response;

import lombok.Data;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
/**
 * Base abstract class for all NIP Response models.
 * Provides common fields like SessionID, ResponseCode, and ResponseMessage.
 */
@Data
@XmlAccessorType(XmlAccessType.FIELD)
public abstract class BaseResponse implements NipResponse {

    @XmlElement(name = "SessionID", namespace = "http://core.nip.nibss/")
    protected String sessionId; // Changed to protected to be accessible by subclasses

    @XmlElement(name = "ResponseCode", namespace = "http://core.nip.nibss/")
    protected String responseCode; // Changed to protected

    @XmlElement(name = "ResponseMessage", namespace = "http://core.nip.nibss/")
    protected String responseMessage;

    @Override
    public String getSessionId() {
        return sessionId;
    }

    @Override
    public String getResponseCode() {
        return responseCode;
    }

    @Override
    public String getResponseMessage() {
        return responseMessage;
    }
}