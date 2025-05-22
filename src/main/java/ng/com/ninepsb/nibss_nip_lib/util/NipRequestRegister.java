package ng.com.ninepsb.nibss_nip_lib.util;

import ng.com.ninepsb.nibss_nip_lib.exception.NipClientException;
import ng.com.ninepsb.nibss_nip_lib.model.NipTransaction;
import ng.com.ninepsb.nibss_nip_lib.model.response.NipResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class NipRequestRegister {

    private final Map<String, Class<? extends NipResponse>> TRANSACTION_RESPONSE_MAPPER = new ConcurrentHashMap<>();
    private final Logger log = LoggerFactory.getLogger(NipRequestRegister.class);

    public void register(Class<? extends NipTransaction> transaction, Class<? extends NipResponse> response) {
        log.info(":::Registering transaction: {} with response: {}", transaction.getName(), response.getName());
        TRANSACTION_RESPONSE_MAPPER.put(transaction.getSimpleName(), response);
    }

    public Class<? extends NipResponse> getResponse(String transaction) {
        return TRANSACTION_RESPONSE_MAPPER.get(transaction);
    }

    @SuppressWarnings("unchecked")
    public Class<? extends NipTransaction> getTransaction(String response) {
        return TRANSACTION_RESPONSE_MAPPER.entrySet()
                .stream()
                .filter(entry -> entry.getValue().getSimpleName().equals(response))
                .findFirst()
                .map(entry -> {
                    try {
                        return (Class<? extends NipTransaction>) Class.forName(entry.getKey());
                    } catch (ClassNotFoundException e) {
                        throw new NipClientException("Transaction class not found: " + entry.getKey(), e);
                    }
                })
                .orElseThrow(() -> new NipClientException("No transaction registered for response: " + response));
    }
}