package ng.com.ninepsb.nibss_nip_lib.handlers;

import ng.com.ninepsb.nibss_nip_lib.annotations.NipRequestItem;
import ng.com.ninepsb.nibss_nip_lib.model.NipTransaction;
import ng.com.ninepsb.nibss_nip_lib.model.requests.AccountBlockRequest;
import ng.com.ninepsb.nibss_nip_lib.model.requests.AccountUnblockRequest;
import ng.com.ninepsb.nibss_nip_lib.model.requests.AmountBlockRequest;
import ng.com.ninepsb.nibss_nip_lib.model.response.AccountBlockResponse;
import ng.com.ninepsb.nibss_nip_lib.model.response.AccountUnblockResponse;
import ng.com.ninepsb.nibss_nip_lib.model.response.AmountBlockResponse;

public interface NipTransactionHandler {
    String handle(NipTransaction nipTransaction);
    AccountBlockResponse handleAccountBlock(AccountBlockRequest accountBlockRequest);
    AccountUnblockResponse handleAccountUnblock(AccountUnblockRequest accountUnblockRequest);
    AmountBlockResponse handleAmountBlock(AmountBlockRequest amountBlockRequest);


}
