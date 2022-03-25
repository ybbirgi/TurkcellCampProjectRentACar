package com.turkcell.rentACar.core.bankServices;

public interface PosService {
    boolean makePayment(String cardOwnerName, String cardNumber, int cardCVV,int cardEndMonth, int cardEndYear,double amount);
}
