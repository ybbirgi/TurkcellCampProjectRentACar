package com.turkcell.rentACar.business.requests.creates;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CreateCreditCardRequest {
    private String cardHolder;

    private String CardNo;

    private int expirationMonth;

    private int expirationYear;

    private int CVV;
}
