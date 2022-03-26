package com.turkcell.rentACar.core.bankServices;

import com.turkcell.rentACar.business.requests.creates.CreateCreditCardRequest;

public interface PosService {
    boolean isCardValid(CreateCreditCardRequest creditCardRequest);
    boolean makePayment(double amount);
}
