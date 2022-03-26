package com.turkcell.rentACar.business.abstracts;

import com.turkcell.rentACar.api.models.RentalCarModel;
import com.turkcell.rentACar.business.dtos.additionalServiceDtos.AdditionalServiceDto;
import com.turkcell.rentACar.business.dtos.additionalServiceDtos.AdditionalServiceListDto;
import com.turkcell.rentACar.business.dtos.orderedAdditionalServiceDtos.OrderedAdditionalServiceDto;
import com.turkcell.rentACar.business.dtos.orderedAdditionalServiceDtos.OrderedAdditionalServiceListDto;
import com.turkcell.rentACar.business.requests.creates.CreateOrderedAdditionalServiceRequest;
import com.turkcell.rentACar.business.requests.deletes.DeleteOrderedAdditionalServiceRequest;
import com.turkcell.rentACar.business.requests.updates.UpdateOrderedAdditionalServiceRequest;
import com.turkcell.rentACar.core.utilities.exceptions.BusinessException;
import com.turkcell.rentACar.core.utilities.exceptions.NotFoundException;
import com.turkcell.rentACar.core.utilities.exceptions.UpdateHasNoChangesException;
import com.turkcell.rentACar.core.utilities.results.DataResult;
import com.turkcell.rentACar.core.utilities.results.Result;
import com.turkcell.rentACar.entities.concretes.OrderedAdditionalService;

import java.util.List;

public interface OrderedAdditionalServiceService {
    void add(List<CreateOrderedAdditionalServiceRequest> createOrderedAdditionalServiceRequests,int invoiceNo) throws BusinessException;
    //Result update(UpdateOrderedAdditionalServiceRequest updateOrderedAdditionalServiceRequest) throws NotFoundException, UpdateHasNoChangesException;
    //Result delete(DeleteOrderedAdditionalServiceRequest deleteOrderedAdditionalServiceRequest) throws NotFoundException;
    DataResult<OrderedAdditionalServiceDto> getById(int id) throws NotFoundException;
    List<OrderedAdditionalService> getOrderedAdditionalServicesByRentalId(int rentalId);
}
