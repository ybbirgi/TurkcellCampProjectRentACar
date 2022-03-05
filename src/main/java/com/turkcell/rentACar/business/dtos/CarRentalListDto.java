package com.turkcell.rentACar.business.dtos;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CarRentalListDto {

    private String rentDescription;

    private LocalDate rentalDate;

    private LocalDate rentalReturnDate;

    private int carId;
}
