package ng.com.ninepsb.nibss_nip_lib.model.response;

import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.math.BigDecimal;

/**
 * 023 Fund Transfer Advice Response (Direct Debit) (FTAdviceDebitResponse)
 * Extends BaseResponse.
 */
@Data
@EqualsAndHashCode(callSuper = true)
@XmlRootElement(name = "FTAdviceDebitResponse", namespace = "http://core.nip.nibss/")
@XmlAccessorType(XmlAccessType.FIELD)
public class FTAdviceDebitResponse extends BaseResponse {

    // SessionID and ResponseCode are inherited from BaseResponse.
    // ResponseMessage will be null as it's not present in the original XML.

    @XmlElement(name = "NameEnquiryRef", namespace = "http://core.nip.nibss/", required = false) // Optional field
    private String nameEnquiryRef;

    @XmlElement(name = "DestinationInstitutionCode", namespace = "http://core.nip.nibss/", required = true)
    private String destinationInstitutionCode;

    @XmlElement(name = "ChannelCode", namespace = "http://core.nip.nibss/", required = true)
    private int channelCode;

    @XmlElement(name = "DebitAccountName", namespace = "http://core.nip.nibss/", required = true)
    private String debitAccountName;

    @XmlElement(name = "DebitAccountNumber", namespace = "http://core.nip.nibss/", required = true)
    private String debitAccountNumber;

    @XmlElement(name = "DebitBankVerificationNumber", namespace = "http://core.nip.nibss/", required = true)
    private String debitBankVerificationNumber;

    @XmlElement(name = "DebitKYCLevel", namespace = "http://core.nip.nibss/", required = true)
    private int debitKYCLevel;

    @XmlElement(name = "BeneficiaryAccountName", namespace = "http://core.nip.nibss/", required = true)
    private String beneficiaryAccountName;

    @XmlElement(name = "BeneficiaryAccountNumber", namespace = "http://core.nip.nibss/", required = true)
    private String beneficiaryAccountNumber;

    @XmlElement(name = "BeneficiaryBankVerificationNumber", namespace = "http://core.nip.nibss/", required = true)
    private String beneficiaryBankVerificationNumber;

    @XmlElement(name = "BeneficiaryKYCLevel", namespace = "http://core.nip.nibss/", required = true)
    private int beneficiaryKYCLevel;

    @XmlElement(name = "TransactionLocation", namespace = "http://core.nip.nibss/", required = true)
    private String transactionLocation;

    @XmlElement(name = "Narration", namespace = "http://core.nip.nibss/", required = true)
    private String narration;

    @XmlElement(name = "PaymentReference", namespace = "http://core.nip.nibss/", required = true)
    private String paymentReference;

    @XmlElement(name = "MandateReferenceNumber", namespace = "http://core.nip.nibss/", required = true)
    private String mandateReferenceNumber;

    @XmlElement(name = "TransactionFee", namespace = "http://core.nip.nibss/", required = true)
    private BigDecimal transactionFee;

    @XmlElement(name = "Amount", namespace = "http://core.nip.nibss/", required = true)
    private BigDecimal amount;
}
