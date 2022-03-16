package com.turkcell.rentACar.business.requests.updates;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.util.Pair;

import javax.persistence.Column;
import javax.validation.constraints.*;
import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UpdateCarRentalRequest {

    @NotNull
    @Positive
    private int rentalId;

    @Size(min=2,max=50)
    @NotNull
    @NotBlank
    private String rentDescription;

    private LocalDate rentalDate;

    private LocalDate rentalReturnDate;

    @NotNull
    private int startedKilometer;

    @NotNull
    @Positive
    private int carId;

    @NotNull
    @Positive
    private int cityId;

}
