package com.turkcell.rentACar.business.requests.updates;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.PositiveOrZero;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UpdatePaymentRequest {
    @NotNull
    @PositiveOrZero
    private int paymentId;

    @NotNull
    @PositiveOrZero
    private int rentalId;
}
