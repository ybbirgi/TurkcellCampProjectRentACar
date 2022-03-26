package com.turkcell.rentACar.business.adapters.posAdapters;

import com.turkcell.rentACar.business.outServices.ZiraatBankManager;
import com.turkcell.rentACar.business.requests.creates.CreateCreditCardRequest;
import com.turkcell.rentACar.core.bankServices.PosService;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;

@Service
@Primary
public class ZiraatBankPosAdapter implements PosService{

    @Override
    public boolean isCardValid(CreateCreditCardRequest creditCardRequest) {
        ZiraatBankManager ziraatBankManager = new ZiraatBankManager();
        boolean result = ziraatBankManager.isCardValid(creditCardRequest.getCardHolder(),
                creditCardRequest.getCardNo(), creditCardRequest.getCVV(),
                creditCardRequest.getExpirationMonth(), creditCardRequest.getExpirationYear());
        return result;
    }

    @Override
    public boolean makePayment(double amount) {
        ZiraatBankManager ziraatBankManager = new ZiraatBankManager();
        boolean result = ziraatBankManager.makePayment(amount);
        return result;
    }
}
