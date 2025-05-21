package ng.com.ninepsb.nibss_nip_lib.model.response;

import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

/**
 * 011 Name Enquiry Response (NESingleResponse)
 * Also used for 011A Name Enquiry Response for Virtual Accounts
 * Extends BaseResponse.
 */
@Data
@EqualsAndHashCode(callSuper = true)
@XmlRootElement(name = "NESingleResponse", namespace = "http://core.nip.nibss/")
@XmlAccessorType(XmlAccessType.FIELD)
public class NESingleResponse extends BaseResponse {

    // SessionID and ResponseCode are inherited from BaseResponse.
    // ResponseMessage will be null as it's not present in the original XML.

    @XmlElement(name = "DestinationInstitutionCode", namespace = "http://core.nip.nibss/", required = true)
    private String destinationInstitutionCode;

    @XmlElement(name = "ChannelCode", namespace = "http://core.nip.nibss/", required = true)
    private int channelCode;

    @XmlElement(name = "AccountNumber", namespace = "http://core.nip.nibss/", required = true)
    private String accountNumber;

    @XmlElement(name = "AccountName", namespace = "http://core.nip.nibss/", required = true)
    private String accountName;

    @XmlElement(name = "BankVerificationNumber", namespace = "http://core.nip.nibss/", required = true)
    private String bankVerificationNumber;

    @XmlElement(name = "KYCLevel", namespace = "http://core.nip.nibss/", required = true)
    private int kycLevel;
}
