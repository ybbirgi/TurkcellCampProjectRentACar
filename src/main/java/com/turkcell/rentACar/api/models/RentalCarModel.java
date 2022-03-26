package com.turkcell.rentACar.api.models;

import com.turkcell.rentACar.business.requests.creates.CreateCarRentalRequest;
import com.turkcell.rentACar.business.requests.creates.CreateOrderedAdditionalServiceRequest;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.Valid;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class RentalCarModel {

    @Valid
    CreateCarRentalRequest createCarRentalRequest;

    @Valid
    List<CreateOrderedAdditionalServiceRequest> createOrderedAdditionalServiceRequestList;
}
