package ng.com.ninepsb.nibss_nip_lib.model.response;

import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.math.BigDecimal;
/**
 * 019 Balance Enquiry Response (BalanceEnquiryResponse)
 * Extends BaseResponse.
 */
@Data
@EqualsAndHashCode(callSuper = true)
@XmlRootElement(name = "BalanceEnquiryResponse", namespace = "http://core.nip.nibss/")
@XmlAccessorType(XmlAccessType.FIELD)
public class BalanceEnquiryResponse extends BaseResponse {

    // SessionID and ResponseCode are inherited from BaseResponse.
    // ResponseMessage will be null as it's not present in the original XML.

    @XmlElement(name = "DestinationInstitutionCode", namespace = "http://core.nip.nibss/", required = true)
    private String destinationInstitutionCode;

    @XmlElement(name = "ChannelCode", namespace = "http://core.nip.nibss/", required = true)
    private int channelCode;

    @XmlElement(name = "AuthorizationCode", namespace = "http://core.nip.nibss/", required = true)
    private String authorizationCode;

    @XmlElement(name = "TargetAccountName", namespace = "http://core.nip.nibss/", required = true)
    private String targetAccountName;

    @XmlElement(name = "TargetBankVerificationNumber", namespace = "http://core.nip.nibss/", required = true)
    private String targetBankVerificationNumber;

    @XmlElement(name = "TargetAccountNumber", namespace = "http://core.nip.nibss/", required = true)
    private String targetAccountNumber;

    @XmlElement(name = "AvailableBalance", namespace = "http://core.nip.nibss/", required = true)
    private BigDecimal availableBalance;
}

