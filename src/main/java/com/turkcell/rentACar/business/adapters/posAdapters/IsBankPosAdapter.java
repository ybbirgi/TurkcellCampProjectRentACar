package com.turkcell.rentACar.business.adapters.posAdapters;

import com.turkcell.rentACar.business.outServices.IsBankManager;
import com.turkcell.rentACar.core.bankServices.PosService;
import org.springframework.stereotype.Service;

@Service
public class IsBankPosAdapter implements PosService {

    @Override
    public boolean makePayment(String cardOwnerName, String cardNumber, int cardCVV, int cardEndMonth, int cardEndYear, double amount) {
        IsBankManager isBankManager = new IsBankManager();
        boolean result = isBankManager.makePayment(cardOwnerName,cardNumber,cardCVV,cardEndMonth,cardEndYear,amount);
        return result;
    }
}
