package com.turkcell.rentACar.business.requests.updates;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.util.Pair;

import javax.persistence.Column;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import javax.validation.constraints.Size;
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
    private String maintenanceDescription;

    private LocalDate rentalDate;

    private LocalDate rentalReturnDate;

    @NotNull
    @Positive
    private int carId;
}
