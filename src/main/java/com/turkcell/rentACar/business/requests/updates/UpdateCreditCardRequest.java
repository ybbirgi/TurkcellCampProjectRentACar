package com.turkcell.rentACar.business.requests.updates;

import com.turkcell.rentACar.entities.concretes.Customer;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UpdateCreditCardRequest {
    private int creditCardId;

    private String cardHolder;

    private String CardNo;

    private int expirationMonth;

    private int expirationYear;

    private int CVV;
}
