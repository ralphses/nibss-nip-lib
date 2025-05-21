package ng.com.ninepsb.nibss_nip_lib.service.impl;

import ng.com.ninepsb.nibss_nip_lib.exception.XmlConversionException;
import ng.com.ninepsb.nibss_nip_lib.model.NipTransaction;
import ng.com.ninepsb.nibss_nip_lib.model.response.NipResponse;
import ng.com.ninepsb.nibss_nip_lib.service.MessageEncryptionService;
import ng.com.ninepsb.nibss_nip_lib.service.NipTransactionProcessor;
import ng.com.ninepsb.nibss_nip_lib.service.NipXmlConverter;
import org.springframework.stereotype.Component;

@Component
public class DefaultNipTransactionProcessor implements NipTransactionProcessor {

    private final NipXmlConverter xmlConverter;
    private final MessageEncryptionService messageEncryptionService;

    public DefaultNipTransactionProcessor(NipXmlConverter xmlConverter, MessageEncryptionService messageEncryptionService) {
        this.xmlConverter = xmlConverter;
        this.messageEncryptionService = messageEncryptionService;
    }


    @Override
    public <T extends NipTransaction> String processOutgoingTransaction(T transaction) throws XmlConversionException {

        // Convert transaction to XML
        String xml = xmlConverter.toXml(transaction);

        // Encrypt XML using NIBSS public key
        return messageEncryptionService.encrypt(xml);
    }

    @Override
    public <T extends NipResponse> T processIncomingResponse(String encryptedXml, Class<T> responseClass) throws XmlConversionException {

        // Decrypt XML using bank private key
        String xml = messageEncryptionService.decrypt(encryptedXml);

        // Convert XML to response object
        return xmlConverter.fromXml(xml, responseClass);
    }
}
