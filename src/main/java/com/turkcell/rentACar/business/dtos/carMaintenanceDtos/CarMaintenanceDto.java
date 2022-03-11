package com.turkcell.rentACar.business.dtos.carMaintenanceDtos;

import com.turkcell.rentACar.entities.concretes.Car;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CarMaintenanceDto {

    private int maintenanceId;

    private String maintenanceDescription;

    private LocalDate returnDate;

    private int carId;

}