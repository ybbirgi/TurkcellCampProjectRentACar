package com.turkcell.rentACar.business.dtos.customerDtos;

import com.turkcell.rentACar.entities.concretes.CarRental;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
@Data
@AllArgsConstructor
@NoArgsConstructor

public class CustomerDto {
    private String firstName;

    private String lastName;

    private List<CarRental> carRentalList;
}
