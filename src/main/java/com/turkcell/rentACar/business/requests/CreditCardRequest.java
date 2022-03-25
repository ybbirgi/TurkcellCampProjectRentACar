package com.turkcell.rentACar.business.requests;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Size;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CreditCardRequest {
    private String cardHolder;

    @Size(min = 16,max = 16)
    private String cardNumber;

    private int cvv;

    private int month;

    private int year;

    private double balance;
}
