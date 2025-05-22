package ng.com.ninepsb.nibss_nip_lib.model.requests;

import lombok.Data;
import lombok.EqualsAndHashCode;
import ng.com.ninepsb.nibss_nip_lib.annotations.NipRequestItem;
import ng.com.ninepsb.nibss_nip_lib.model.BaseTransaction;
import ng.com.ninepsb.nibss_nip_lib.model.response.NESingleResponse;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

/**
 * 010 Name Enquiry Request (NESingleRequest)
 * Also used for 010A Name Enquiry Request for Virtual Accounts
 * Extends BaseTransaction.
 */
@Data
@EqualsAndHashCode(callSuper = true) // Include superclass fields in equals/hashCode
@XmlRootElement(name = "NESingleRequest", namespace = "http://core.nip.nibss/")
@XmlAccessorType(XmlAccessType.FIELD)
@NipRequestItem(request = NESingleRequest.class, response = NESingleResponse.class)
public class NESingleRequest extends BaseTransaction {

    @XmlElement(name = "AccountNumber", namespace = "http://core.nip.nibss/", required = true)
    private String accountNumber;
}
