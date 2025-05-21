package ng.com.ninepsb.nibss_nip_lib.model;

import lombok.Data;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;


@Data
@XmlAccessorType(XmlAccessType.FIELD)
public abstract class BaseTransaction implements NipTransaction {

    @XmlElement(name = "SessionID", namespace = "http://core.nip.nibss/", required = true)
    protected String sessionId; // Changed to protected

    // Note: This field is not present in all request types (e.g., TSQuerySingleRequest).
    // For such cases, it will be null or require custom mapping if strict XML adherence is needed.
    @XmlElement(name = "DestinationInstitutionCode", namespace = "http://core.nip.nibss/", required = false)
    protected String destinationInstitutionCode; // Changed to protected, made optional

    // Note: This field's location might vary or not be present at the root for all request types
    // (e.g., FinancialInstitutionListRequest has it nested in a Header).
    @XmlElement(name = "ChannelCode", namespace = "http://core.nip.nibss/", required = false)
    protected int channelCode; // Changed to protected, made optional

    // Note: This field is specifically for 'ReferenceCode' XML element (used in block/unblock transactions).
    // Other transaction types use different reference fields (e.g., PaymentReference, MandateReferenceNumber).
    // For requests that do not have a 'ReferenceCode' XML element, this field will be null.
    @XmlElement(name = "ReferenceCode", namespace = "http://core.nip.nibss/", required = false)
    protected String transactionReference; // Changed to protected, made optional

    @Override
    public String getSessionId() {
        return sessionId;
    }

    @Override
    public String getTransactionReference() {
        return transactionReference;
    }
}
