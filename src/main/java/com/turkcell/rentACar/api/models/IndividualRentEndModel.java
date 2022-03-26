package com.turkcell.rentACar.api.models;

import com.turkcell.rentACar.business.requests.creates.CreateCreditCardRequest;
import com.turkcell.rentACar.business.requests.ends.EndCarRentalRequest;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.Valid;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class IndividualRentEndModel {

    @Valid
    private EndCarRentalRequest endCarRentalRequest;

    @Valid
    private CreateCreditCardRequest creditCardRequest;

}
