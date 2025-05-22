package ng.com.ninepsb.nibss_nip_lib.model.requests;

import lombok.Data;
import lombok.EqualsAndHashCode;
import ng.com.ninepsb.nibss_nip_lib.annotations.NipRequestItem;
import ng.com.ninepsb.nibss_nip_lib.model.BaseTransaction;
import ng.com.ninepsb.nibss_nip_lib.model.Header;
import ng.com.ninepsb.nibss_nip_lib.model.Record;
import ng.com.ninepsb.nibss_nip_lib.model.response.FinancialInstitutionListResponse;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.List;

/**
 * 032 Financial Institution List Request (FinancialInstitutionListRequest)
 * Extends BaseTransaction.
 * Note: This request uses 'BatchNumber' as its primary identifier, not 'SessionID'.
 * Also, 'ChannelCode' is nested within 'Header' rather than at the root.
 * The 'SessionID', 'DestinationInstitutionCode', 'ChannelCode', and 'transactionReference'
 * inherited from BaseTransaction will not be directly mapped from the XML for this request.
 */
@Data
@EqualsAndHashCode(callSuper = true)
@XmlRootElement(name = "FinancialInstitutionListRequest", namespace = "http://core.nip.nibss/")
@XmlAccessorType(XmlAccessType.FIELD)
@NipRequestItem(request = FinancialInstitutionListRequest.class, response = FinancialInstitutionListResponse.class)
public class FinancialInstitutionListRequest extends BaseTransaction {

    // SessionID, DestinationInstitutionCode, ChannelCode, transactionReference
    // inherited from BaseTransaction are not directly used in this XML structure.
    // They will be null unless explicitly set programmatically.
    // To prevent JAXB from trying to map them to non-existent root elements,
    // we can mark them as transient for XML binding purposes if needed, but
    // for simplicity and adherence to the base class, we let them be.
    // The @XmlElement(required=true) on SessionID in BaseTransaction might cause issues
    // if not handled by JAXB context configuration or if not actually present.
    // For now, we assume a flexible JAXB context or that these fields will be null.

    @XmlElement(name = "Header", namespace = "http://core.nip.nibss/", required = true)
    private Header header;

    @XmlElement(name = "Record", namespace = "http://core.nip.nibss/", required = true)
    private List<Record> records; // List of records

    // Override getSessionId to return BatchNumber for this specific request,
    // as it acts as the primary identifier.
    @Override
    public String getSessionId() {
        return this.header != null ? this.header.getBatchNumber() : super.getSessionId();
    }

    // Override getTransactionReference if there's a specific reference for this transaction
    // that should be exposed via the interface. Otherwise, it will be null.
    @Override
    public String getTransactionReference() {
        // FinancialInstitutionListRequest doesn't have a direct "ReferenceCode" field.
        // Returning null or a relevant identifier if one exists.
        return null;
    }
}
