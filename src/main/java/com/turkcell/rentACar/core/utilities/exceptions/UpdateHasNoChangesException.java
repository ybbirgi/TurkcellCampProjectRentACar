package com.turkcell.rentACar.core.utilities.exceptions;

public class UpdateHasNoChangesException extends BusinessException{
    public UpdateHasNoChangesException(String message) {
        super(message);
    }
}
