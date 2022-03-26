package com.turkcell.rentACar.business.dtos.creditCardDto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CreditCardDto {
    private String cardHolder;

    private String CardNo;

    private int expirationMonth;

    private int expirationYear;

    private int CVV;

    private int customerId;
}
