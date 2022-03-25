package com.turkcell.rentACar.api.models;

import com.turkcell.rentACar.business.requests.CreditCardRequest;
import com.turkcell.rentACar.business.requests.creates.CreatePaymentRequest;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CorporateCustomerPaymentModel {
    CreatePaymentRequest createPaymentRequest;
    CreditCardRequest creditCardRequest;
}
