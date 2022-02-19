package com.turkcell.rentACar.business.requests.updates;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UpdateCarRequest {
    private int carId;
    private double carDailyPrice;
    private int carModelYear;
    private String carDescription;
    private int colorId;
    private int brandId;
}
