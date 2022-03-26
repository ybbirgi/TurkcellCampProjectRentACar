package com.turkcell.rentACar.business.concretes;

import com.turkcell.rentACar.business.abstracts.AdditionalServiceService;
import com.turkcell.rentACar.business.abstracts.CarRentalService;
import com.turkcell.rentACar.business.abstracts.InvoiceService;
import com.turkcell.rentACar.business.abstracts.OrderedAdditionalServiceService;
import com.turkcell.rentACar.business.constants.messages.BusinessMessages;
import com.turkcell.rentACar.business.dtos.additionalServiceDtos.AdditionalServiceListDto;
import com.turkcell.rentACar.business.dtos.orderedAdditionalServiceDtos.OrderedAdditionalServiceDto;
import com.turkcell.rentACar.business.dtos.orderedAdditionalServiceDtos.OrderedAdditionalServiceListDto;
import com.turkcell.rentACar.business.requests.creates.CreateOrderedAdditionalServiceRequest;
import com.turkcell.rentACar.business.requests.deletes.DeleteOrderedAdditionalServiceRequest;
import com.turkcell.rentACar.business.requests.updates.UpdateOrderedAdditionalServiceRequest;
import com.turkcell.rentACar.core.utilities.exceptions.BusinessException;
import com.turkcell.rentACar.core.utilities.exceptions.NotFoundException;
import com.turkcell.rentACar.core.utilities.exceptions.UpdateHasNoChangesException;
import com.turkcell.rentACar.core.utilities.mapping.ModelMapperService;
import com.turkcell.rentACar.core.utilities.results.DataResult;
import com.turkcell.rentACar.core.utilities.results.Result;
import com.turkcell.rentACar.core.utilities.results.SuccessDataResult;
import com.turkcell.rentACar.core.utilities.results.SuccessResult;
import com.turkcell.rentACar.dataAccess.abstracts.OrderedAdditionalServiceDao;
import com.turkcell.rentACar.entities.concretes.AdditionalService;
import com.turkcell.rentACar.entities.concretes.OrderedAdditionalService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class OrderedAdditionalServiceManager implements OrderedAdditionalServiceService {
    private OrderedAdditionalServiceDao orderedAdditionalServiceDao;
    private ModelMapperService modelMapperService;
    private CarRentalService carRentalService;
    private InvoiceService invoiceService;
    private AdditionalServiceService additionalServiceService;
    @Autowired
    public OrderedAdditionalServiceManager(OrderedAdditionalServiceDao orderedAdditionalServiceDao,
                                           ModelMapperService modelMapperService,
                                           CarRentalService carRentalService,
                                           @Lazy InvoiceService invoiceService,
                                           AdditionalServiceService additionalServiceService) {
        this.orderedAdditionalServiceDao = orderedAdditionalServiceDao;
        this.modelMapperService = modelMapperService;
        this.carRentalService = carRentalService;
        this.invoiceService = invoiceService;
        this.additionalServiceService = additionalServiceService;
    }

    @Override
    public void add(List<CreateOrderedAdditionalServiceRequest> createOrderedAdditionalServiceRequests,int rentalId) throws BusinessException {
        for (CreateOrderedAdditionalServiceRequest requests: createOrderedAdditionalServiceRequests) {
            OrderedAdditionalService orderedAdditionalService = this.modelMapperService.forRequest().map(requests,OrderedAdditionalService.class);
            orderedAdditionalService.setCarRental(this.carRentalService.getByRentalId(rentalId));
            this.orderedAdditionalServiceDao.save(orderedAdditionalService);
        }
    }

    @Override
    public DataResult<OrderedAdditionalServiceDto> getById(int id) throws NotFoundException {
        checkIfOrderedAdditionalServiceExistsById(id);

        OrderedAdditionalService orderedAdditionalService = this.orderedAdditionalServiceDao.getById(id);

        OrderedAdditionalServiceDto orderedAdditionalServiceDto = this.modelMapperService.forDto().map(orderedAdditionalService,OrderedAdditionalServiceDto.class);

        return new SuccessDataResult<OrderedAdditionalServiceDto>(orderedAdditionalServiceDto, BusinessMessages.GlobalMessages.DATA_LISTED_SUCCESSFULLY);
    }


    @Override
    public List<OrderedAdditionalService> getOrderedAdditionalServicesByRentalId(int rentalId) {
        return this.orderedAdditionalServiceDao.getAllByCarRental_RentalId(rentalId);
    }

    public void checkIfOrderedAdditionalServiceExistsById(int serviceId) throws NotFoundException {
        if(!this.orderedAdditionalServiceDao.existsById(serviceId))
            throw new NotFoundException(BusinessMessages.AdditionalServiceMessages.ADDITIONAL_SERVICE_NOT_FOUND);
    }

    /*
    public void checkIfOrderedAdditionalServiceUpdateHasNoChanges(Integer quantity,Integer serviceId) throws UpdateHasNoChangesException{
        if(this.orderedAdditionalServiceDao.existsByQuantityAndAdditionalService_ServiceId(quantity,serviceId))
            throw new UpdateHasNoChangesException("There is No Changes On Ordered Additional Service Update");
    }*/

    /*@Override
    public Result update(UpdateOrderedAdditionalServiceRequest updateOrderedAdditionalServiceRequest) throws NotFoundException , UpdateHasNoChangesException {
        checkIfOrderedAdditionalServiceExistsById(updateOrderedAdditionalServiceRequest.getOrderedServiceId());
        OrderedAdditionalService orderedAdditionalService = this.orderedAdditionalServiceDao.getById(updateOrderedAdditionalServiceRequest.getOrderedServiceId());
        checkIfOrderedAdditionalServiceUpdateHasNoChanges(orderedAdditionalService.getQuantity(),orderedAdditionalService.getAdditionalService().getServiceId());

        double servicePriceBefore = orderedAdditionalService.getQuantity()*(this.additionalServiceService.getServiceByServiceId(orderedAdditionalService.getOrderedServiceId()).getDailyPrice());
        double servicePriceAfter = updateOrderedAdditionalServiceRequest.getQuantity()*this.additionalServiceService.getServiceByServiceId(updateOrderedAdditionalServiceRequest.getOrderedServiceId()).getDailyPrice();
        double priceChange = servicePriceAfter - servicePriceBefore;

        this.invoiceService.updateInvoiceIfOrderedAdditionalServiceUpdates(orderedAdditionalService.getInvoice().getInvoiceNo(),priceChange);

        return new SuccessResult("Ordered Additional Service Deleted Successfully");

    }*/

    /*
    @Override
    public Result delete(DeleteOrderedAdditionalServiceRequest deleteOrderedAdditionalServiceRequest) throws NotFoundException {
        OrderedAdditionalService orderedAdditionalService = this.orderedAdditionalServiceDao.getById(deleteOrderedAdditionalServiceRequest.getOrderedServiceId());

        double servicePrice = orderedAdditionalService.getQuantity()*(this.additionalServiceService.getServiceByServiceId(orderedAdditionalService.getOrderedServiceId()).getDailyPrice());

        this.orderedAdditionalServiceDao.delete(orderedAdditionalService);

        this.invoiceService.updateInvoiceIfOrderedAdditionalServiceDeletes(orderedAdditionalService.getInvoice().getInvoiceNo(),servicePrice);

        return new SuccessResult("Ordered Additional Service Deleted Successfully");
    }*/

}

