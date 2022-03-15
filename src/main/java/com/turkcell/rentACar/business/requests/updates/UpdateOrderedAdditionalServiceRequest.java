package com.turkcell.rentACar.business.requests.updates;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import javax.validation.constraints.PositiveOrZero;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UpdateOrderedAdditionalServiceRequest {
    @NotNull
    @PositiveOrZero
    private int orderedServiceId;

    @NotNull
    @PositiveOrZero
    private int serviceId;

    @Positive
    @Min(1)
    private int quantity;
}
