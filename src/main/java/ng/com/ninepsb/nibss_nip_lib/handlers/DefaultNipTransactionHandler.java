package ng.com.ninepsb.nibss_nip_lib.handlers;

import lombok.RequiredArgsConstructor;
import ng.com.ninepsb.nibss_nip_lib.model.NipTransaction;
import ng.com.ninepsb.nibss_nip_lib.model.requests.AccountBlockRequest;
import ng.com.ninepsb.nibss_nip_lib.model.requests.AccountUnblockRequest;
import ng.com.ninepsb.nibss_nip_lib.model.requests.AmountBlockRequest;
import ng.com.ninepsb.nibss_nip_lib.model.response.AccountBlockResponse;
import ng.com.ninepsb.nibss_nip_lib.model.response.AccountUnblockResponse;
import ng.com.ninepsb.nibss_nip_lib.model.response.AmountBlockResponse;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class DefaultNipTransactionHandler implements NipTransactionHandler {

    @Override
    public String handle(NipTransaction nipTransaction) {
        return "";
    }

    @Override
    public AccountBlockResponse handleAccountBlock(AccountBlockRequest accountBlockRequest) {
        return null;
    }

    @Override
    public AccountUnblockResponse handleAccountUnblock(AccountUnblockRequest accountUnblockRequest) {
        return null;
    }

    @Override
    public AmountBlockResponse handleAmountBlock(AmountBlockRequest amountBlockRequest) {
        return null;
    }
}
