package ng.com.ninepsb.nibss_nip_lib.service;

import ng.com.ninepsb.nibss_nip_lib.exception.XmlConversionException;
import ng.com.ninepsb.nibss_nip_lib.model.NipTransaction;
import ng.com.ninepsb.nibss_nip_lib.model.response.NipResponse;

public interface NipTransactionProcessor {
    <T extends NipTransaction> String processOutgoingTransaction(T transaction) throws XmlConversionException;
    <T extends NipResponse> T processIncomingResponse(String encryptedXml, Class<T> responseClass) throws XmlConversionException;
}
