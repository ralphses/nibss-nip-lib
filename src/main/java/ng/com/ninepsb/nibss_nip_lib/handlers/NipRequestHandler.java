package ng.com.ninepsb.nibss_nip_lib.handlers;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import ng.com.ninepsb.nibss_nip_lib.exception.NipClientException;
import ng.com.ninepsb.nibss_nip_lib.exception.XmlConversionException;
import ng.com.ninepsb.nibss_nip_lib.model.NipTransaction;
import ng.com.ninepsb.nibss_nip_lib.model.response.NipResponse;
import ng.com.ninepsb.nibss_nip_lib.service.NipXmlConverter;
import ng.com.ninepsb.nibss_nip_lib.util.NipRequestRegister;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class NipRequestHandler {

    private final NipRequestRegister nipRequestRegister;
    private final NipTransactionHandler transactionHandler;
    private final NipXmlConverter nipXmlConverter;

    public String handle(String xmlRequest) {
        try {
            // get the request name
            String rootElementName = nipXmlConverter.getRootElementName(xmlRequest);

            // get the response class
            Class<? extends NipResponse> responseType = nipRequestRegister.getResponse(rootElementName);

            // get the request class
            Class<? extends NipTransaction> transactionType = nipRequestRegister.getTransaction(responseType.getSimpleName());

            // convert xml to request object
            NipTransaction nipTransaction = nipXmlConverter.fromXml(xmlRequest, transactionType);

            // handle transaction
            return transactionHandler.handle(nipTransaction);
        } catch (XmlConversionException e) {
            log.warn(":::Failed to convert xml request", e);
            throw new NipClientException("Failed to process request");
        }

    }

}
