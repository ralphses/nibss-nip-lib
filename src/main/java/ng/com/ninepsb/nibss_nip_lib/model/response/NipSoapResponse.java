package ng.com.ninepsb.nibss_nip_lib.model.response;

import lombok.Data;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@Data
@XmlRootElement(name = "NipResponse")
@XmlAccessorType(XmlAccessType.FIELD)
public class NipSoapResponse {

    @XmlElement(name = "EncryptedResponse")
    private String encryptedResponse;
}