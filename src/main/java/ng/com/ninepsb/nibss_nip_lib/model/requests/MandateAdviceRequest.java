package ng.com.ninepsb.nibss_nip_lib.model.requests;

import lombok.Data;
import lombok.EqualsAndHashCode;
import ng.com.ninepsb.nibss_nip_lib.model.BaseTransaction;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.math.BigDecimal;
/**
 * 034 Mandate Advice Request (MandateAdviceRequest)
 * Extends BaseTransaction.
 */
@Data
@EqualsAndHashCode(callSuper = true)
@XmlRootElement(name = "MandateAdviceRequest", namespace = "http://core.nip.nibss/")
@XmlAccessorType(XmlAccessType.FIELD)
public class MandateAdviceRequest extends BaseTransaction {

    // SessionID, DestinationInstitutionCode, ChannelCode are inherited from BaseTransaction.
    // transactionReference (mapped to ReferenceCode) will be null as it's not applicable here.

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

    // Override getTransactionReference to return MandateReferenceNumber
    @Override
    public String getTransactionReference() {
        return this.MandateReferenceNumber;
    }
}
