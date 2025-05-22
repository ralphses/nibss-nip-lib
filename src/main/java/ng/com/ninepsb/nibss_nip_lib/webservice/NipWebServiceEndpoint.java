package ng.com.ninepsb.nibss_nip_lib.webservice;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import ng.com.ninepsb.nibss_nip_lib.config.NipProperties;
import ng.com.ninepsb.nibss_nip_lib.model.requests.NipSoapRequest;
import ng.com.ninepsb.nibss_nip_lib.model.response.NipErrorResponse;
import ng.com.ninepsb.nibss_nip_lib.model.response.NipSoapResponse;
import ng.com.ninepsb.nibss_nip_lib.service.MessageEncryptionService;
import ng.com.ninepsb.nibss_nip_lib.handlers.NipRequestHandler;
import ng.com.ninepsb.nibss_nip_lib.service.NipXmlConverter;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.ws.server.endpoint.annotation.Endpoint;
import org.springframework.ws.server.endpoint.annotation.PayloadRoot;
import org.springframework.ws.server.endpoint.annotation.RequestPayload;
import org.springframework.ws.server.endpoint.annotation.ResponsePayload;

@Slf4j
@Endpoint
@RequiredArgsConstructor
@ConditionalOnProperty(value = "nip.web-service.enabled", havingValue = "true")
public class NipWebServiceEndpoint {

    private static final String NAMESPACE_URI = "http://nibss.com/nip";

    private final NipProperties nipProperties;
    private final NipXmlConverter nipXmlConverter;
    private final MessageEncryptionService messageEncryptionService;
    private final NipRequestHandler nipRequestHandler;

    @PayloadRoot(namespace = NAMESPACE_URI, localPart = "NipRequest")
    @ResponsePayload
    public NipSoapResponse handleNipRequest(@RequestPayload NipSoapRequest request) {
        try {
            // Get the encrypted request
            String encryptedRequest = request.getEncryptedRequest();

            // Decrypt the request
            String decryptedXml = messageEncryptionService.decrypt(encryptedRequest);

            // Determine the transaction type and process accordingly
            String response = nipRequestHandler.handle(decryptedXml);

            // Encrypt the response
            String encryptedResponse = messageEncryptionService.encrypt(response);

            // Create and return the response wrapper
            NipSoapResponse soapResponse = new NipSoapResponse();
            soapResponse.setEncryptedResponse(encryptedResponse);
            return soapResponse;
        } catch (Exception e) {
            // Handle errors
            return createErrorResponse(e);
        }
    }

    /**
     * Create error response
     *
     * @param e Exception that occurred
     * @return Encrypted error response
     */
    private NipSoapResponse createErrorResponse(Exception e) {
        try {
            // Create generic error response
            NipErrorResponse errorResponse = new NipErrorResponse();
            errorResponse.setResponseCode("96");
            errorResponse.setResponseMessage("System error: " + e.getMessage());

            // Convert to XML and encrypt
            String xml = nipXmlConverter.toXml(errorResponse);
            String encryptedResponse = messageEncryptionService.encrypt(xml);

            // Create response wrapper
            NipSoapResponse response = new NipSoapResponse();
            response.setEncryptedResponse(encryptedResponse);
            return response;
        } catch (Exception ex) {
            // Fallback error response if everything else fails
            NipSoapResponse response = new NipSoapResponse();
            response.setEncryptedResponse("ERROR: " + e.getMessage());
            return response;
        }
    }
}
