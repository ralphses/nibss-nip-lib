package ng.com.ninepsb.nibss_nip_lib.model.response;


import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.math.BigDecimal;

/**
 * 013 Fund Transfer Response (Direct Credit) (FTSingleCreditResponse)
 * Extends BaseResponse.
 */
@Data
@EqualsAndHashCode(callSuper = true)
@XmlRootElement(name = "FTSingleCreditResponse", namespace = "http://core.nip.nibss/")
@XmlAccessorType(XmlAccessType.FIELD)
public class FTSingleCreditResponse extends BaseResponse {

    // SessionID and ResponseCode are inherited from BaseResponse.
    // ResponseMessage will be null as it's not present in the original XML.

    @XmlElement(name = "NameEnquiryRef", namespace = "http://core.nip.nibss/", required = false) // Optional field
    private String nameEnquiryRef;

    @XmlElement(name = "DestinationInstitutionCode", namespace = "http://core.nip.nibss/", required = true)
    private String destinationInstitutionCode;

    @XmlElement(name = "ChannelCode", namespace = "http://core.nip.nibss/", required = true)
    private int channelCode;

    @XmlElement(name = "BeneficiaryAccountName", namespace = "http://core.nip.nibss/", required = true)
    private String beneficiaryAccountName;

    @XmlElement(name = "BeneficiaryAccountNumber", namespace = "http://core.nip.nibss/", required = true)
    private String beneficiaryAccountNumber;

    @XmlElement(name = "BeneficiaryBankVerificationNumber", namespace = "http://core.nip.nibss/", required = true)
    private String beneficiaryBankVerificationNumber;

    @XmlElement(name = "BeneficiaryKYCLevel", namespace = "http://core.nip.nibss/", required = true)
    private int beneficiaryKYCLevel;

    @XmlElement(name = "OriginatorAccountName", namespace = "http://core.nip.nibss/", required = true)
    private String originatorAccountName;

    @XmlElement(name = "OriginatorAccountNumber", namespace = "http://core.nip.nibss/", required = true)
    private String originatorAccountNumber;

    @XmlElement(name = "OriginatorBankVerificationNumber", namespace = "http://core.nip.nibss/", required = true)
    private String originatorBankVerificationNumber;

    @XmlElement(name = "OriginatorKYCLevel", namespace = "http://core.nip.nibss/", required = true)
    private int originatorKYCLevel;

    @XmlElement(name = "TransactionLocation", namespace = "http://core.nip.nibss/", required = true)
    private String transactionLocation;

    @XmlElement(name = "Narration", namespace = "http://core.nip.nibss/", required = true)
    private String narration;

    @XmlElement(name = "PaymentReference", namespace = "http://core.nip.nibss/", required = true)
    private String paymentReference;

    @XmlElement(name = "Amount", namespace = "http://core.nip.nibss/", required = true)
    private BigDecimal amount;
}