package ng.com.ninepsb.nibss_nip_lib.model.requests;

import lombok.Data;
import lombok.EqualsAndHashCode;
import ng.com.ninepsb.nibss_nip_lib.annotations.NipRequestItem;
import ng.com.ninepsb.nibss_nip_lib.model.BaseTransaction;
import ng.com.ninepsb.nibss_nip_lib.model.response.AccountBlockResponse;
import ng.com.ninepsb.nibss_nip_lib.model.response.AccountUnblockResponse;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

/**
 * 030 Account Unblock Request (AccountUnblockRequest)
 * Extends BaseTransaction.
 */
@Data
@EqualsAndHashCode(callSuper = true)
@XmlRootElement(name = "AccountUnblockRequest", namespace = "http://core.nip.nibss/")
@XmlAccessorType(XmlAccessType.FIELD)
@NipRequestItem(request = AccountUnblockRequest.class, response = AccountUnblockResponse.class)
public class AccountUnblockRequest extends BaseTransaction {

    // SessionID, DestinationInstitutionCode, ChannelCode are inherited from BaseTransaction.
    // transactionReference is directly mapped to "ReferenceCode" in BaseTransaction.

    @XmlElement(name = "TargetAccountName", namespace = "http://core.nip.nibss/", required = true)
    private String targetAccountName;

    @XmlElement(name = "TargetBankVerificationNumber", namespace = "http://core.nip.nibss/", required = true)
    private String targetBankVerificationNumber;

    @XmlElement(name = "TargetAccountNumber", namespace = "http://core.nip.nibss/", required = true)
    private String targetAccountNumber;

    @XmlElement(name = "ReasonCode", namespace = "http://core.nip.nibss/", required = true)
    private String reasonCode;

    @XmlElement(name = "Narration", namespace = "http://core.nip.nibss/", required = true)
    private String narration;
}
