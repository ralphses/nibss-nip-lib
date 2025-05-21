package ng.com.ninepsb.nibss_nip_lib.model.response;

import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.math.BigDecimal;

/**
 * 035 Mandate Advice Response (MandateAdviceResponse)
 * Extends BaseResponse.
 */
@Data
@EqualsAndHashCode(callSuper = true)
@XmlRootElement(name = "MandateAdviceResponse", namespace = "http://core.nip.nibss/")
@XmlAccessorType(XmlAccessType.FIELD)
public class MandateAdviceResponse extends BaseResponse {

    // SessionID and ResponseCode are inherited from BaseResponse.
    // ResponseMessage will be null as it's not present in the original XML.

    @XmlElement(name = "DestinationInstitutionCode", namespace = "http://core.nip.nibss/", required = true)
    private String DestinationInstitutionCode;

    @XmlElement(name = "ChannelCode", namespace = "http://core.nip.nibss/", required = true)
    private int ChannelCode;

    @XmlElement(name = "MandateReferenceNumber", namespace = "http://core.nip.nibss/", required = true)
    private String MandateReferenceNumber;

    @XmlElement(name = "Amount", namespace = "http://core.nip.nibss/", required = true)
    private BigDecimal Amount;

    @XmlElement(name = "DebitAccountName", namespace = "http://core.nip.nibss/", required = true)
    private String DebitAccountName;

    @XmlElement(name = "DebitAccountNumber", namespace = "http://core.nip.nibss/", required = true)
    private String DebitAccountNumber;

    @XmlElement(name = "DebitBankVerificationNumber", namespace = "http://core.nip.nibss/", required = true)
    private String DebitBankVerificationNumber;

    @XmlElement(name = "DebitKYCLevel", namespace = "http://core.nip.nibss/", required = true)
    private int DebitKYCLevel;

    @XmlElement(name = "BeneficiaryAccountName", namespace = "http://core.nip.nibss/", required = true)
    private String BeneficiaryAccountName;

    @XmlElement(name = "BeneficiaryAccountNumber", namespace = "http://core.nip.nibss/", required = true)
    private String BeneficiaryAccountNumber;

    @XmlElement(name = "BeneficiaryBankVerificationNumber", namespace = "http://core.nip.nibss/", required = true)
    private String BeneficiaryBankVerificationNumber;

    @XmlElement(name = "BeneficiaryKYCLevel", namespace = "http://core.nip.nibss/", required = true)
    private int BeneficiaryKYCLevel;
}
