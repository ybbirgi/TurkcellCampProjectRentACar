package com.turkcell.rentACar.business.requests.updates;

import com.turkcell.rentACar.entities.concretes.City;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UpdateCarRequest {

    @NotNull
    @Positive
    private int carId;

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
    private int carKilometer;

    @NotNull
    @PositiveOrZero
    private int colorId;

    @NotNull
    @PositiveOrZero
    private int brandId;

    @NotNull
    private int cityId;
}
