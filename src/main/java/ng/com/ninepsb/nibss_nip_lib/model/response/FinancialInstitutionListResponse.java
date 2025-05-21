package ng.com.ninepsb.nibss_nip_lib.model.response;

import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

/**
 * 033 Financial Institution Response (FinancialInstitutionListResponse)
 * Extends BaseResponse.
 */
@Data
@EqualsAndHashCode(callSuper = true)
@XmlRootElement(name = "FinancialInstitutionListResponse", namespace = "http://core.nip.nibss/")
@XmlAccessorType(XmlAccessType.FIELD)
public class FinancialInstitutionListResponse extends BaseResponse {

    // SessionID and ResponseCode are inherited from BaseResponse.
    // ResponseMessage will be null as it's not present in the original XML.

    @XmlElement(name = "BatchNumber", namespace = "http://core.nip.nibss/", required = true)
    private String batchNumber;

    @XmlElement(name = "DestinationInstitutionCode", namespace = "http://core.nip.nibss/", required = true)
    private String destinationInstitutionCode;

    @XmlElement(name = "ChannelCode", namespace = "http://core.nip.nibss/", required = true)
    private int channelCode;

    @XmlElement(name = "NumberOfRecords", namespace = "http://core.nip.nibss/", required = true)
    private int numberOfRecords;

    // Override getSessionId to return BatchNumber for this specific response.
    @Override
    public String getSessionId() {
        return this.batchNumber;
    }
}
