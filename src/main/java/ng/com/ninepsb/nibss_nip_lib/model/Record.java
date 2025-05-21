package ng.com.ninepsb.nibss_nip_lib.model;


import lombok.Data;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;

/**
 * Nested class for Record in FinancialInstitutionListRequest
 */
@Data
@XmlAccessorType(XmlAccessType.FIELD)
public class Record {
    @XmlElement(name = "InstitutionCode", namespace = "http://core.nip.nibss/", required = true)
    private String institutionCode;

    @XmlElement(name = "InstitutionName", namespace = "http://core.nip.nibss/", required = true)
    private String institutionName;

    @XmlElement(name = "Category", namespace = "http://core.nip.nibss/", required = true)
    private int category;
}

