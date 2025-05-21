package ng.com.ninepsb.nibss_nip_lib.model.requests;

import lombok.Data;
import lombok.EqualsAndHashCode;
import ng.com.ninepsb.nibss_nip_lib.model.BaseTransaction;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

/**
 * 028 Account Block Request (AccountBlockRequest)
 * Extends BaseTransaction.
 */
@Data
@EqualsAndHashCode(callSuper = true)
@XmlRootElement(name = "AccountBlockRequest", namespace = "http://core.nip.nibss/")
@XmlAccessorType(XmlAccessType.FIELD)
public class AccountBlockRequest extends BaseTransaction {

    // SessionID, DestinationInstitutionCode, ChannelCode are inherited from BaseTransaction.
    // transactionReference is directly mapped to "ReferenceCode" in BaseTransaction.

    @XmlElement(name = "TargetAccountName", namespace = "http://core.nip.nibss/", required = true)
    private String TargetAccountName;

    @XmlElement(name = "TargetBankVerificationNumber", namespace = "http://core.nip.nibss/", required = true)
    private String TargetBankVerificationNumber;

    @XmlElement(name = "TargetAccountNumber", namespace = "http://core.nip.nibss/", required = true)
    private String TargetAccountNumber;

    @XmlElement(name = "ReasonCode", namespace = "http://core.nip.nibss/", required = true)
    private String ReasonCode;

    @XmlElement(name = "Narration", namespace = "http://core.nip.nibss/", required = true)
    private String Narration;
}