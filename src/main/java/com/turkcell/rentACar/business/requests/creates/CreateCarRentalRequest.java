package com.turkcell.rentACar.business.requests.creates;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.time.LocalDate;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CreateCarRentalRequest {

    @Size(min=2,max=50)
    @NotNull
    @NotBlank
    private String rentDescription;

    private LocalDate rentalDate;

    private LocalDate rentalReturnDate;

    @NotNull
    private int startedKilometer;

    @NotNull
    private int carId;

    @NotNull
    private int cityId;

    @NotNull
    private int customerId;
}
