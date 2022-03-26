package com.turkcell.rentACar.business.outServices;

public class IsBankManager {
    public boolean isCardValid(String cardOwnerName, String cardEndMonth, int cardEndYear, int cardNumber, int cardCVV) {
        return true;
    }
    public boolean makePayment(double amount){
        return true;
    }
}
