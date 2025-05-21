package ng.com.ninepsb.nibss_nip_lib.model.response;

import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
/**
 * 017 Transaction Status Query Response (Single Transaction) (TSQuerySingleResponse)
 * Extends BaseResponse.
 */
@Data
@EqualsAndHashCode(callSuper = true)
@XmlRootElement(name = "TSQuerySingleResponse", namespace = "http://core.nip.nibss/")
@XmlAccessorType(XmlAccessType.FIELD)
public class TSQuerySingleResponse extends BaseResponse {

    // SessionID and ResponseCode are inherited from BaseResponse.
    // ResponseMessage will be null as it's not present in the original XML.

    @XmlElement(name = "SourceInstitutionCode", namespace = "http://core.nip.nibss/", required = true)
    private String sourceInstitutionCode;

    @XmlElement(name = "ChannelCode", namespace = "http://core.nip.nibss/", required = true)
    private int channelCode;
}
