package com.turkcell.rentACar.business.concretes;

import com.turkcell.rentACar.business.abstracts.CarRentalService;
import com.turkcell.rentACar.business.abstracts.OrderedAdditionalServiceService;
import com.turkcell.rentACar.business.requests.creates.CreateOrderedAdditionalServiceRequest;
import com.turkcell.rentACar.core.utilities.exceptions.BusinessException;
import com.turkcell.rentACar.core.utilities.mapping.ModelMapperService;
import com.turkcell.rentACar.dataAccess.abstracts.OrderedAdditionalServiceDao;
import com.turkcell.rentACar.entities.concretes.OrderedAdditionalService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class OrderedAdditionalServiceManager implements OrderedAdditionalServiceService {
    private OrderedAdditionalServiceDao orderedAdditionalServiceDao;
    private ModelMapperService modelMapperService;
    private CarRentalService carRentalService;

    public OrderedAdditionalServiceManager(OrderedAdditionalServiceDao orderedAdditionalServiceDao, ModelMapperService modelMapperService,CarRentalService carRentalService) {
        this.orderedAdditionalServiceDao = orderedAdditionalServiceDao;
        this.modelMapperService = modelMapperService;
        this.carRentalService = carRentalService;
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
    public List<OrderedAdditionalService> getAllByRentalCarId(int rentalId) {
        return this.orderedAdditionalServiceDao.getAllByCarRental_RentalId(rentalId);
    }

    @Override
    public Double calculateTotalPriceOfAdditionalServices(List<OrderedAdditionalService> orderedAdditionalServices) {
        double servicePrice=0;
        for (OrderedAdditionalService orderedAdditionalService:orderedAdditionalServices) {
            servicePrice+= orderedAdditionalService.getQuantity()*orderedAdditionalService.getAdditionalService().getDailyPrice();
        }
        return servicePrice;
    }


}
