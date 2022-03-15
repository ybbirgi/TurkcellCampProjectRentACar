package com.turkcell.rentACar.business.requests.updates;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.*;
@Data
@AllArgsConstructor
@NoArgsConstructor
public class UpdateAdditionalServiceRequest {
    @NotNull
    @Positive
    private int serviceId;

    @NotNull
    @NotBlank
    @Size(min=2,max=15)
    private String serviceName;

    @NotNull
    @Min(50)
    @Max(500)
    private double dailyPrice;
}
