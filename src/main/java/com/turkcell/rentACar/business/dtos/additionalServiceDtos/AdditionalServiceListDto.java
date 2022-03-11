package com.turkcell.rentACar.business.dtos.additionalServiceDtos;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AdditionalServiceListDto {
    private String serviceName;

    private double dailyPrice;
}
