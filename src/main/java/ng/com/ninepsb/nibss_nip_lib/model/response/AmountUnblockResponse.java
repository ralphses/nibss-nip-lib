package ng.com.ninepsb.nibss_nip_lib.model.response;

import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.math.BigDecimal;


/**
 * 027 Amount Unblock Response (AmountUnblockResponse)
 * Extends BaseResponse.
 */
@Data
@EqualsAndHashCode(callSuper = true)
@XmlRootElement(name = "AmountUnblockResponse", namespace = "http://core.nip.nibss/")
@XmlAccessorType(XmlAccessType.FIELD)
public class AmountUnblockResponse extends BaseResponse {

    // SessionID and ResponseCode are inherited from BaseResponse.
    // ResponseMessage will be null as it's not present in the original XML.

    @XmlElement(name = "DestinationInstitutionCode", namespace = "http://core.nip.nibss/", required = true)
    private String DestinationInstitutionCode;

    @XmlElement(name = "ChannelCode", namespace = "http://core.nip.nibss/", required = true)
    private int ChannelCode;

    @XmlElement(name = "ReferenceCode", namespace = "http://core.nip.nibss/", required = true)
    private String ReferenceCode;

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

    @XmlElement(name = "Amount", namespace = "http://core.nip.nibss/", required = true)
    private BigDecimal Amount;
}
