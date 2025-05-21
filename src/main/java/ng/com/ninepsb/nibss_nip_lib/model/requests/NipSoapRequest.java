package ng.com.ninepsb.nibss_nip_lib.model.requests;

import lombok.Data;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@Data
@XmlRootElement(name = "NipRequest")
@XmlAccessorType(XmlAccessType.FIELD)
public class NipSoapRequest {

    @XmlElement(name = "EncryptedRequest")
    private String encryptedRequest;
}
