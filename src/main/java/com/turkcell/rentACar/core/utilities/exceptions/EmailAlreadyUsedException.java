package com.turkcell.rentACar.core.utilities.exceptions;

public class EmailAlreadyUsedException extends BusinessException{
    public EmailAlreadyUsedException(String message) {
        super(message);
    }
}
