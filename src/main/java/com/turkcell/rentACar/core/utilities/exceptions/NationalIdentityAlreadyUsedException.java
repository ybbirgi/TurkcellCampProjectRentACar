package com.turkcell.rentACar.core.utilities.exceptions;

public class NationalIdentityAlreadyUsedException extends BusinessException{
    public NationalIdentityAlreadyUsedException(String message) {
        super(message);
    }
}
