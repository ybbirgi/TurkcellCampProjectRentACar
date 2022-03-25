package com.turkcell.rentACar.api.models;

import com.turkcell.rentACar.business.requests.creates.CreateCarRentalRequest;
import com.turkcell.rentACar.business.requests.creates.CreateOrderedAdditionalServiceRequest;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class RentalCarModel {
    CreateCarRentalRequest createCarRentalRequest;

    List<CreateOrderedAdditionalServiceRequest> createOrderedAdditionalServiceRequestList;
}
