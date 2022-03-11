package com.turkcell.rentACar.business.dtos.cityDtos;

import com.turkcell.rentACar.entities.concretes.Car;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CityDto {
    private String cityName;

    private List<Car> carList;
}
