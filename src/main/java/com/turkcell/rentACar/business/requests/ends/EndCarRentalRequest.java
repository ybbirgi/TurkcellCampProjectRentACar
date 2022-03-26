package com.turkcell.rentACar.business.requests.ends;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class EndCarRentalRequest {

    @NotNull
    @Positive
    private int rentalId;

    @NotNull
    private int endedKilometer;

    private LocalDate returnDate;
}

