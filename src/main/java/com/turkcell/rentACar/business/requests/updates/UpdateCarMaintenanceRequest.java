package com.turkcell.rentACar.business.requests.updates;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.*;
import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UpdateCarMaintenanceRequest {

    @NotNull
    @Positive
    private int maintenanceId;

    @Size(min=2,max=50)
    @NotNull
    @NotBlank
    private String maintenanceDescription;

    private LocalDate returnDate;

    @NotNull
    @Positive
    private int carId;
}
