package com.turkcell.rentACar.business.requests.deletes;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.PositiveOrZero;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class DeleteCarDamageRequest {
    @NotNull
    @PositiveOrZero
    private int carDamageId;
}
