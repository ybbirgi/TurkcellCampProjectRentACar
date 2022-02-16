package com.turkcell.rentACar.business.requests;

import com.turkcell.rentACar.entities.concretes.Brand;
import com.turkcell.rentACar.entities.concretes.Color;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor

public class CreateCarRequest {
    private int carId;
    private double carDailyPrice;
    private int carModelYear;
    private String carDescription;
    private int colorId;
    private int brandId;
}
