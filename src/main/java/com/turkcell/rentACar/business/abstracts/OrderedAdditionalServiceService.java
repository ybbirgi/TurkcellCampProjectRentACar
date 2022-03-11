package com.turkcell.rentACar.business.abstracts;

import com.turkcell.rentACar.business.requests.creates.CreateOrderedAdditionalServiceRequest;
import com.turkcell.rentACar.core.utilities.exceptions.BusinessException;
import com.turkcell.rentACar.entities.concretes.OrderedAdditionalService;

import java.util.List;

public interface OrderedAdditionalServiceService {
    void add(List<CreateOrderedAdditionalServiceRequest> createOrderedAdditionalServiceRequests,int rentalId) throws BusinessException;
    List<OrderedAdditionalService> getAllByRentalCarId(int rentalId);
    Double calculateTotalPriceOfAdditionalServices(List<OrderedAdditionalService> orderedAdditionalServices);
}
