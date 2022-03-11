package com.turkcell.rentACar.business.requests.creates;

import com.turkcell.rentACar.entities.concretes.City;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.util.Pair;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.PositiveOrZero;
import javax.validation.constraints.Size;
import java.time.LocalDate;

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
    private int carId;

    @NotNull
    private int cityId;

    @NotNull
    private int customerId;
}
