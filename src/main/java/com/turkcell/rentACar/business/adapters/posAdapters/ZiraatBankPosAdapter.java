package com.turkcell.rentACar.business.adapters.posAdapters;

import com.turkcell.rentACar.business.outServices.ZiraatBankManager;
import com.turkcell.rentACar.core.bankServices.PosService;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;

@Service
@Primary
public class ZiraatBankPosAdapter implements PosService{

    @Override
    public boolean makePayment(String cardOwnerName, String cardNumber, int cardCVV, int cardEndMonth, int cardEndYear, double amount) {
        ZiraatBankManager ziraatBankManager = new ZiraatBankManager();
        boolean result = ziraatBankManager.makePayment(cardOwnerName,cardNumber,cardCVV,cardEndMonth,cardEndYear,amount);
        return result;
    }
}
