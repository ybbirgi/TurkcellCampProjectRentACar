package com.turkcell.rentACar.business.requests.updates;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.PositiveOrZero;
import javax.validation.constraints.Size;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UpdateCarDamageRequest {
    @NotNull
    @PositiveOrZero
    private int carDamageId;

    @NotNull
    @NotBlank
    @Size(min=2,max=50)
    private String damageRecord;

    @NotNull
    @PositiveOrZero
    private int carId;
}
