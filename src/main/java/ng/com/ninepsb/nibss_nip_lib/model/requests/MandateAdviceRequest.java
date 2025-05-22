package ng.com.ninepsb.nibss_nip_lib.model.requests;

import lombok.Data;
import lombok.EqualsAndHashCode;
import ng.com.ninepsb.nibss_nip_lib.annotations.NipRequestItem;
import ng.com.ninepsb.nibss_nip_lib.model.BaseTransaction;
import ng.com.ninepsb.nibss_nip_lib.model.response.MandateAdviceResponse;

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
@NipRequestItem(request = MandateAdviceRequest.class, response = MandateAdviceResponse.class)
public class MandateAdviceRequest extends BaseTransaction {

    // SessionID, DestinationInstitutionCode, ChannelCode are inherited from BaseTransaction.
    // transactionReference (mapped to ReferenceCode) will be null as it's not applicable here.

    @XmlElement(name = "MandateReferenceNumber", namespace = "http://core.nip.nibss/", required = true)
    private String mandateReferenceNumber;

    @XmlElement(name = "Amount", namespace = "http://core.nip.nibss/", required = true)
    private BigDecimal amount;

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

    // Override getTransactionReference to return MandateReferenceNumber
    @Override
    public String getTransactionReference() {
        return this.mandateReferenceNumber;
    }
}
