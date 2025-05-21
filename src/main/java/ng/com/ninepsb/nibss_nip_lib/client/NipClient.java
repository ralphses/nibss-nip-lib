package ng.com.ninepsb.nibss_nip_lib.client;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import ng.com.ninepsb.nibss_nip_lib.config.NipProperties;
import ng.com.ninepsb.nibss_nip_lib.exception.NipClientException;
import ng.com.ninepsb.nibss_nip_lib.exception.XmlConversionException;


import ng.com.ninepsb.nibss_nip_lib.model.NipTransaction;
import ng.com.ninepsb.nibss_nip_lib.model.response.NipResponse;
import ng.com.ninepsb.nibss_nip_lib.service.NipTransactionProcessor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.oxm.jaxb.Jaxb2Marshaller;
import org.springframework.stereotype.Component;
import org.springframework.ws.client.core.support.WebServiceGatewaySupport;
import org.springframework.ws.transport.http.HttpComponentsMessageSender;

import ng.com.ninepsb.nibss_nip_lib.model.requests.*;
import ng.com.ninepsb.nibss_nip_lib.model.response.*;

import java.time.Duration;

@Slf4j
@Component
@AllArgsConstructor
@ConditionalOnProperty(value = "nip.client.enabled", havingValue = "true")
public class NipClient extends WebServiceGatewaySupport {

    private final NipProperties properties;
    private final NipTransactionProcessor transactionProcessor;

    @Autowired
    public void init(Jaxb2Marshaller marshaller) {

        setMarshaller(marshaller);
        setUnmarshaller(marshaller);

        HttpComponentsMessageSender messageSender = new HttpComponentsMessageSender();
        messageSender.setConnectionTimeout((int) Duration.ofSeconds(properties.getClient().getConnectionTimeoutMs()).toMillis());
        messageSender.setReadTimeout((int) Duration.ofSeconds(properties.getClient().getReadTimeoutMs()).toMillis());
        setMessageSender(messageSender);

        log.info("NIP Client initialized with URL: {} Connection Timeout: {}ms, Read Timeout: {}ms",
                properties.getClient().getNipServiceUrl(),
                properties.getClient().getConnectionTimeoutMs(),
                properties.getClient().getReadTimeoutMs());
    }

    /**
     * Sends a NIP operation, handling encryption/decryption and XML conversion.
     *
     * @param transaction The NIP request object, extending NipTransaction.
     * @param responseClass The class of the expected NIP response, extending NipResponse.
     * @param operationName The name of the NIP operation (e.g., "nameenquirysingleitem").
     * @param <T> Type of the request object.
     * @param <R> Type of the response object.
     * @return The NIP response object.
     * @throws NipClientException If any error occurs during the NIP operation.
     */
    private <T extends NipTransaction, R extends NipResponse> R sendNipOperation(
            T transaction, Class<R> responseClass, String operationName) throws NipClientException {
        try {
            // Log the initiation of the NIP operation with the transaction reference.
            // Using getTransactionReference() from NipTransaction interface.
            log.info("Initiating NIP operation: {} for transaction ref: {}", operationName, transaction.getTransactionReference());

            // Process the outgoing transaction to get the encrypted payload.
            String encryptedRequestPayload = transactionProcessor.processOutgoingTransaction(transaction);
            log.debug("Encrypted request payload for {}: {}", operationName, encryptedRequestPayload.replaceAll("[\r\n]", ""));

            // Create the SOAP request wrapper.
            NipSoapRequest request = new NipSoapRequest();
            request.setEncryptedRequest(encryptedRequestPayload);

            // Marshal, send, and receive the SOAP message.
            NipSoapResponse soapResponse = (NipSoapResponse) getWebServiceTemplate()
                    .marshalSendAndReceive(properties.client.getNipServiceUrl(), request, message -> {
                        // The WSDL indicates empty soapAction, so no need to set explicitly unless required by NIBSS for specific reasons.
                        // SoapMessage soapMessage = (SoapMessage) message;
                        // soapMessage.setSoapAction("");
                    });

            // Validate the received SOAP response.
            if (soapResponse == null || soapResponse.getEncryptedResponse() == null || soapResponse.getEncryptedResponse().isEmpty()) {
                throw new NipClientException("Received empty or null encrypted response from NIP service for operation: " + operationName);
            }

            log.debug("Encrypted response payload for {}: {}", operationName, soapResponse.getEncryptedResponse().replaceAll("[\r\n]", ""));

            // Process the incoming encrypted response to get the NIP response object.
            R nipResponse = transactionProcessor.processIncomingResponse(soapResponse.getEncryptedResponse(), responseClass);

            // Log the completion of the operation with the response code.
            log.info("NIP operation: {} completed successfully. Response code: {}", operationName, nipResponse.getResponseCode());
            return nipResponse;

        } catch (XmlConversionException e) {
            log.error("XML conversion error during NIP operation {}: {}", operationName, e.getMessage(), e);
            throw new NipClientException("Failed to process XML for NIP operation: " + operationName, e);
        } catch (Exception e) {
            log.error("General error during NIP operation {}: {}", operationName, e.getMessage(), e);
            throw new NipClientException("Failed to send/receive NIP operation: " + operationName, e);
        }
    }

    /**
     * Sends a raw encrypted XML request to the NIP service.
     * This method bypasses the internal object-to-XML conversion and vice-versa.
     *
     * @param encryptedXml The already encrypted XML string to send.
     * @return The raw encrypted XML response from the NIP service.
     * @throws NipClientException If sending the raw request fails.
     */
    public String sendRawRequest(String encryptedXml) throws NipClientException {
        try {
            log.info("Sending raw NIP request.");
            log.debug("Raw encrypted request payload: {}", encryptedXml.replaceAll("[\r\n]", ""));

            NipSoapRequest request = new NipSoapRequest();
            request.setEncryptedRequest(encryptedXml);

            NipSoapResponse response = (NipSoapResponse) getWebServiceTemplate()
                    .marshalSendAndReceive(properties.client.getNipServiceUrl(), request);

            if (response == null || response.getEncryptedResponse() == null || response.getEncryptedResponse().isEmpty()) {
                throw new NipClientException("Received empty or null raw encrypted response from NIP service.");
            }

            log.debug("Raw encrypted response payload: {}", response.getEncryptedResponse().replaceAll("[\r\n]", ""));
            return response.getEncryptedResponse();
        } catch (Exception e) {
            log.error("Failed to send raw NIP request: {}", e.getMessage(), e);
            throw new NipClientException("Failed to send raw NIP request", e);
        }
    }

    /**
     * Initiates a Name Enquiry Single Item operation.
     * @param request The NESingleRequest object.
     * @return The NESingleResponse object.
     * @throws NipClientException If the operation fails.
     */
    public NESingleResponse nameEnquirySingleItem(NESingleRequest request) throws NipClientException {
        return sendNipOperation(request, NESingleResponse.class, "nameenquirysingleitem");
    }

    /**
     * Initiates a Fund Transfer Single Item Direct Credit operation.
     * @param request The FTSingleCreditRequest object.
     * @return The FTSingleCreditResponse object.
     * @throws NipClientException If the operation fails.
     */
    public FTSingleCreditResponse fundTransferSingleItemDc(FTSingleCreditRequest request) throws NipClientException {
        return sendNipOperation(request, FTSingleCreditResponse.class, "fundtransfersingleitem_dc");
    }

    /**
     * Initiates a Fund Transfer Single Item Direct Debit operation.
     * @param request The FTSingleDebitRequest object.
     * @return The FTSingleDebitResponse object.
     * @throws NipClientException If the operation fails.
     */
    public FTSingleDebitResponse fundTransferSingleItemDd(FTSingleDebitRequest request) throws NipClientException {
        return sendNipOperation(request, FTSingleDebitResponse.class, "fundtransfersingleitem_dd");
    }

    /**
     * Initiates a Transaction Status Query Single Item operation.
     * @param request The TSQuerySingleRequest object.
     * @return The TSQuerySingleResponse object.
     * @throws NipClientException If the operation fails.
     */
    public TSQuerySingleResponse txnStatusQuerySingleItem(TSQuerySingleRequest request) throws NipClientException {
        return sendNipOperation(request, TSQuerySingleResponse.class, "txnstatusquerysingleitem");
    }

    /**
     * Initiates a Balance Enquiry operation.
     * @param request The BalanceEnquiryRequest object.
     * @return The BalanceEnquiryResponse object.
     * @throws NipClientException If the operation fails.
     */
    public BalanceEnquiryResponse balanceEnquiry(BalanceEnquiryRequest request) throws NipClientException {
        return sendNipOperation(request, BalanceEnquiryResponse.class, "balanceenquiry");
    }

    /**
     * Initiates an Amount Block operation.
     * @param request The AmountBlockRequest object.
     * @return The AmountBlockResponse object.
     * @throws NipClientException If the operation fails.
     */
    public AmountBlockResponse amountBlock(AmountBlockRequest request) throws NipClientException {
        return sendNipOperation(request, AmountBlockResponse.class, "amountblock");
    }

    /**
     * Initiates an Amount Unblock operation.
     * @param request The AmountUnblockRequest object.
     * @return The AmountUnblockResponse object.
     * @throws NipClientException If the operation fails.
     */
    public AmountUnblockResponse amountUnblock(AmountUnblockRequest request) throws NipClientException {
        return sendNipOperation(request, AmountUnblockResponse.class, "amountunblock");
    }

    /**
     * Initiates an Account Block operation.
     * @param request The AccountBlockRequest object.
     * @return The AccountBlockResponse object.
     * @throws NipClientException If the operation fails.
     */
    public AccountBlockResponse accountBlock(AccountBlockRequest request) throws NipClientException {
        return sendNipOperation(request, AccountBlockResponse.class, "accountblock");
    }

    /**
     * Initiates an Account Unblock operation.
     * @param request The AccountUnblockRequest object.
     * @return The AccountUnblockResponse object.
     * @throws NipClientException If the operation fails.
     */
    public AccountUnblockResponse accountUnblock(AccountUnblockRequest request) throws NipClientException {
        return sendNipOperation(request, AccountUnblockResponse.class, "accountunblock");
    }

    /**
     * Initiates a Mandate Advice operation.
     * @param request The MandateAdviceRequest object.
     * @return The MandateAdviceResponse object.
     * @throws NipClientException If the operation fails.
     */
    public MandateAdviceResponse mandateAdvice(MandateAdviceRequest request) throws NipClientException {
        return sendNipOperation(request, MandateAdviceResponse.class, "mandateadvice");
    }

    /**
     * Initiates a Financial Institution List operation.
     * @param request The FinancialInstitutionListRequest object.
     * @return The FinancialInstitutionListResponse object.
     * @throws NipClientException If the operation fails.
     */
    public FinancialInstitutionListResponse financialInstitutionList(FinancialInstitutionListRequest request) throws NipClientException {
        return sendNipOperation(request, FinancialInstitutionListResponse.class, "financialinstitutionlist");
    }

    /**
     * Initiates a Fund Transfer Advice Direct Credit operation.
     * @param request The FTAdviceCreditRequest object.
     * @return The FTAdviceCreditResponse object.
     * @throws NipClientException If the operation fails.
     */
    public FTAdviceCreditResponse fundTransferAdviceDc(FTAdviceCreditRequest request) throws NipClientException {
        return sendNipOperation(request, FTAdviceCreditResponse.class, "fundtransferAdvice_dc");
    }

    /**
     * Initiates a Fund Transfer Advice Direct Debit operation.
     * @param request The FTAdviceDebitRequest object.
     * @return The FTAdviceDebitResponse object.
     * @throws NipClientException If the operation fails.
     */
    public FTAdviceDebitResponse fundTransferAdviceDd(FTAdviceDebitRequest request) throws NipClientException {
        return sendNipOperation(request, FTAdviceDebitResponse.class, "fundtransferAdvice_dd");
    }
}
