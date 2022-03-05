package com.turkcell.rentACar.business.requests.creates;

import com.turkcell.rentACar.entities.concretes.Brand;
import com.turkcell.rentACar.entities.concretes.Color;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.*;

@Data
@AllArgsConstructor
@NoArgsConstructor

public class CreateCarRequest {

    @NotNull
    @Min(50)
    @Max(500)
    private double carDailyPrice;

    @NotNull
    @Min(2000)
    @Max(2021)
    private int carModelYear;

    @Size(min=20,max=200)
    private String carDescription;

    @NotNull
    @PositiveOrZero
    private int colorId;

    @NotNull
    @PositiveOrZero
    private int brandId;
}
