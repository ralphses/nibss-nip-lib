package ng.com.ninepsb.nibss_nip_lib.model;

import lombok.Data;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;

/**
 * Nested class for Header in FinancialInstitutionListRequest
 */
@Data
@XmlAccessorType(XmlAccessType.FIELD)
public class Header {
    @XmlElement(name = "BatchNumber", namespace = "http://core.nip.nibss/", required = true)
    private String batchNumber;

    @XmlElement(name = "NumberOfRecords", namespace = "http://core.nip.nibss/", required = true)
    private int numberOfRecords;

    @XmlElement(name = "ChannelCode", namespace = "http://core.nip.nibss/", required = true)
    private int channelCode;

    @XmlElement(name = "TransactionLocation", namespace = "http://core.nip.nibss/", required = true)
    private String transactionLocation;
}
