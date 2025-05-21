package ng.com.ninepsb.nibss_nip_lib.model;

import lombok.Data;

import java.security.PrivateKey;
import java.security.PublicKey;

@Data
public class NipKeyStore {
    private PrivateKey bankPrivateKey;
    private PublicKey bankPublicKey;
    private PublicKey nibssPublicKey;
}
