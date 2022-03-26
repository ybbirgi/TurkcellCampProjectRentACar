package com.turkcell.rentACar.api.models;

import com.turkcell.rentACar.business.requests.creates.CreateCreditCardRequest;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.Valid;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CorporateCustomerPaymentModel {

    @Valid
    RentalCarModel rentalCarModel;

    @Valid
    CreateCreditCardRequest creditCardRequest;

}
