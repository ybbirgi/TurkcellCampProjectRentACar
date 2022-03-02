package com.turkcell.rentACar.business.requests.creates;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.*;
import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CreateCarMaintenanceRequest {
    @Size(min=2,max=50)
    @NotNull
    @NotBlank
    private String maintenanceDescription;

    private LocalDate returnDate;

    @NotNull
    @NotBlank
    @Positive
    private int carId;

}
