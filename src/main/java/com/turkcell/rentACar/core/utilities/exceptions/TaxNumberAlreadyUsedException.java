package com.turkcell.rentACar.core.utilities.exceptions;

public class TaxNumberAlreadyUsedException extends BusinessException{
    public TaxNumberAlreadyUsedException(String message) {
        super(message);
    }
}
