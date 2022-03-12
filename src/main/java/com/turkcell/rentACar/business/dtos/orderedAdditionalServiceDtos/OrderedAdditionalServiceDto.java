package com.turkcell.rentACar.business.dtos.orderedAdditionalServiceDtos;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class OrderedAdditionalServiceDto {

    private String additionalServiceName;

    private int quantity;

    private int rentalId;
}
