package com.turkcell.rentACar.business.dtos.carDtos;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CarDto {
    private double carDailyPrice;
    private int carModelYear;
    private String carDescription;
    private boolean carRentalStatus;
    private boolean carMaintenanceStatus;
    private String brandName;
    private String colorName;
}
