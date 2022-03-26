package com.turkcell.rentACar.business.adapters.posAdapters;

import com.turkcell.rentACar.business.outServices.IsBankManager;
import com.turkcell.rentACar.business.requests.creates.CreateCreditCardRequest;
import com.turkcell.rentACar.core.bankServices.PosService;
import org.springframework.stereotype.Service;

@Service
public class IsBankPosAdapter implements PosService {

    @Override
    public boolean isCardValid(CreateCreditCardRequest creditCardRequest) {
        IsBankManager isBankManager = new IsBankManager();
        boolean result = isBankManager.isCardValid(creditCardRequest.getCardHolder(),
                creditCardRequest.getCardNo(), creditCardRequest.getCVV(),
                creditCardRequest.getExpirationMonth(), creditCardRequest.getExpirationYear());
        return result;
    }
    public boolean makePayment(double amount) {
        IsBankManager isBankManager = new IsBankManager();
        boolean result = isBankManager.makePayment(amount);
        return result;
    }
}
