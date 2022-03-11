package com.turkcell.rentACar.business.requests.creates;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.*;


@Data
@AllArgsConstructor
@NoArgsConstructor
public class CreateAdditionalServiceRequest {
    @NotNull
    @NotBlank
    @Size(min=2,max=15)
    private String serviceName;

    @NotNull
    @Min(50)
    @Max(500)
    private double dailyPrice;
}
