package com.turkcell.rentACar.business.concretes;

import com.turkcell.rentACar.business.abstracts.CarRentalService;
import com.turkcell.rentACar.business.abstracts.InvoiceService;
import com.turkcell.rentACar.business.abstracts.OrderedAdditionalServiceService;
import com.turkcell.rentACar.business.requests.creates.CreateOrderedAdditionalServiceRequest;
import com.turkcell.rentACar.business.requests.deletes.DeleteOrderedAdditionalServiceRequest;
import com.turkcell.rentACar.business.requests.updates.UpdateOrderedAdditionalServiceRequest;
import com.turkcell.rentACar.core.utilities.exceptions.BusinessException;
import com.turkcell.rentACar.core.utilities.exceptions.NotFoundException;
import com.turkcell.rentACar.core.utilities.exceptions.UpdateHasNoChangesException;
import com.turkcell.rentACar.core.utilities.mapping.ModelMapperService;
import com.turkcell.rentACar.core.utilities.results.Result;
import com.turkcell.rentACar.dataAccess.abstracts.OrderedAdditionalServiceDao;
import com.turkcell.rentACar.entities.concretes.OrderedAdditionalService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;

import java.util.List;
//todo
@Service
public class OrderedAdditionalServiceManager implements OrderedAdditionalServiceService {
    private OrderedAdditionalServiceDao orderedAdditionalServiceDao;
    private ModelMapperService modelMapperService;
    private CarRentalService carRentalService;
    private InvoiceService invoiceService;
    @Autowired
    public OrderedAdditionalServiceManager(OrderedAdditionalServiceDao orderedAdditionalServiceDao,
                                           ModelMapperService modelMapperService,
                                           CarRentalService carRentalService,
                                           @Lazy InvoiceService invoiceService) {
        this.orderedAdditionalServiceDao = orderedAdditionalServiceDao;
        this.modelMapperService = modelMapperService;
        this.carRentalService = carRentalService;
        this.invoiceService = invoiceService;
    }

    @Override
    public void add(List<CreateOrderedAdditionalServiceRequest> createOrderedAdditionalServiceRequests,int invoiceNo) throws BusinessException {
        for (CreateOrderedAdditionalServiceRequest requests: createOrderedAdditionalServiceRequests) {
            OrderedAdditionalService orderedAdditionalService = this.modelMapperService.forRequest().map(requests,OrderedAdditionalService.class);
            orderedAdditionalService.setInvoice(this.invoiceService.getInvoiceByInvoiceNo(invoiceNo));
            this.orderedAdditionalServiceDao.save(orderedAdditionalService);
        }
    }

    @Override
    public Result update(UpdateOrderedAdditionalServiceRequest updateOrderedAdditionalServiceRequest) throws NotFoundException , UpdateHasNoChangesException {
        checkIfOrderedAdditionalServiceExistsById(updateOrderedAdditionalServiceRequest.getOrderedServiceId());
        OrderedAdditionalService orderedAdditionalService = this.orderedAdditionalServiceDao.getById(updateOrderedAdditionalServiceRequest.getOrderedServiceId());
        checkIfOrderedAdditionalServiceUpdateHasNoChanges(orderedAdditionalService.getQuantity(),orderedAdditionalService.getAdditionalService().getServiceId());

        return null;
    }

    @Override
    public Result delete(DeleteOrderedAdditionalServiceRequest deleteOrderedAdditionalServiceRequest) throws NotFoundException {
        return null;
    }

    @Override
    public List<OrderedAdditionalService> getOrderedAdditionalServicesByInvoiceNo(int invoiceNo) {
        return this.orderedAdditionalServiceDao.getAllByInvoice_InvoiceNo(invoiceNo);
    }

    public void checkIfOrderedAdditionalServiceExistsById(int serviceId) throws NotFoundException {
        if(!this.orderedAdditionalServiceDao.existsById(serviceId))
            throw new NotFoundException("Additional Service Not Found");
    }

    public void checkIfOrderedAdditionalServiceUpdateHasNoChanges(Integer quantity,Integer serviceId) throws UpdateHasNoChangesException{
        if(this.orderedAdditionalServiceDao.existsByQuantityAndAdditionalService_ServiceId(quantity,serviceId))
            throw new UpdateHasNoChangesException("There is No Changes On Ordered Additional Service Update");
    }

}

