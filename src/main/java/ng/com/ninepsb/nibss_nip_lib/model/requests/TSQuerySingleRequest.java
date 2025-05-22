package ng.com.ninepsb.nibss_nip_lib.model.requests;

import lombok.Data;
import lombok.EqualsAndHashCode;
import ng.com.ninepsb.nibss_nip_lib.annotations.NipRequestItem;
import ng.com.ninepsb.nibss_nip_lib.model.BaseTransaction;
import ng.com.ninepsb.nibss_nip_lib.model.response.TSQuerySingleResponse;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

/**
 * 016 Transaction Status Query Request (Single Transaction) (TSQuerySingleRequest)
 * Extends BaseTransaction.
 * Note: BaseTransaction includes DestinationInstitutionCode and ReferenceCode,
 * which are not present in the XML for this request. SourceInstitutionCode is specific here.
 */
@Data
@EqualsAndHashCode(callSuper = true)
@XmlRootElement(name = "TSQuerySingleRequest", namespace = "http://core.nip.nibss/")
@XmlAccessorType(XmlAccessType.FIELD)
@NipRequestItem(request = TSQuerySingleRequest.class, response = TSQuerySingleResponse.class)
public class TSQuerySingleRequest extends BaseTransaction {

    @XmlElement(name = "SourceInstitutionCode", namespace = "http://core.nip.nibss/", required = true)
    private String sourceInstitutionCode;
}
