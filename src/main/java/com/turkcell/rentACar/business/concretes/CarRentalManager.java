package com.turkcell.rentACar.business.concretes;

import com.turkcell.rentACar.api.models.*;
import com.turkcell.rentACar.business.abstracts.*;
import com.turkcell.rentACar.business.constants.messages.BusinessMessages;
import com.turkcell.rentACar.business.dtos.carRentalDtos.CarRentalListDto;
import com.turkcell.rentACar.business.requests.creates.CreateCarRentalRequest;
import com.turkcell.rentACar.business.requests.creates.CreateOrderedAdditionalServiceRequest;
import com.turkcell.rentACar.business.requests.deletes.DeleteCarRentalRequest;
import com.turkcell.rentACar.business.requests.ends.EndCarRentalRequest;
import com.turkcell.rentACar.business.requests.updates.UpdateCarRentalRequest;
import com.turkcell.rentACar.core.utilities.exceptions.BusinessException;
import com.turkcell.rentACar.core.utilities.exceptions.NotFoundException;
import com.turkcell.rentACar.core.utilities.mapping.ModelMapperService;
import com.turkcell.rentACar.core.utilities.results.*;
import com.turkcell.rentACar.dataAccess.abstracts.CarRentalDao;
import com.turkcell.rentACar.entities.concretes.CarRental;
import com.turkcell.rentACar.entities.concretes.OrderedAdditionalService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
@Service
public class CarRentalManager implements CarRentalService {
    private CarRentalDao carRentalDao;
    private ModelMapperService modelMapperService;
    private CarService carService;
    private CarMaintenanceService carMaintenanceService;
    private CityService cityService;
    private OrderedAdditionalServiceService orderedAdditionalServiceService;
    private CustomerService customerService;
    private InvoiceService invoiceService;
    private PaymentService paymentService;

    @Autowired
    public CarRentalManager(CarRentalDao carRentalDao,
                            ModelMapperService modelMapperService,
                            CarService carService,
                            CarMaintenanceService carMaintenanceService,
                            CityService cityService,
                            @Lazy OrderedAdditionalServiceService orderedAdditionalServiceService,
                            CustomerService customerService,
                            @Lazy InvoiceService invoiceService,
                            @Lazy PaymentService paymentService) {
        this.carRentalDao = carRentalDao;
        this.modelMapperService = modelMapperService;
        this.carService = carService;
        this.carMaintenanceService = carMaintenanceService;
        this.cityService = cityService;
        this.orderedAdditionalServiceService = orderedAdditionalServiceService;
        this.customerService = customerService;
        this.invoiceService = invoiceService;
        this.paymentService = paymentService;
    }

    @Override
    public DataResult<List<CarRentalListDto>> getAll() {
        List<CarRental> carRentals = this.carRentalDao.findAll();

        List<CarRentalListDto> response = carRentals.stream().map(carRental->
                this.modelMapperService.forDto().map(carRental, CarRentalListDto.class)).collect(Collectors.toList());

        return new SuccessDataResult<List<CarRentalListDto>>(response, BusinessMessages.GlobalMessages.DATA_LISTED_SUCCESSFULLY);
    }

    @Transactional
    @Override
    public DataResult<CarRental> rentCarToCorporateCustomer(RentalCarModel rentalCarModel) throws BusinessException {
        updateAndCheckOperationsForCarRental(rentalCarModel.getCreateCarRentalRequest());

        CarRental carRental = this.modelMapperService.forRequest().map(rentalCarModel.getCreateCarRentalRequest(),CarRental.class);
        modelMapperCorrection(carRental,rentalCarModel.getCreateCarRentalRequest());

        carRental.setEndedKilometer(null);

        this.carRentalDao.save(carRental);

        this.carService.updateCarRentalStatus(rentalCarModel.getCreateCarRentalRequest().getCarId(),true);

        this.orderedAdditionalServiceService.add(rentalCarModel.getCreateOrderedAdditionalServiceRequestList(),carRental.getRentalId());

        return new SuccessDataResult<CarRental>(carRental,BusinessMessages.GlobalMessages.DATA_ADDED_SUCCESSFULLY);
    }

    @Transactional
    @Override
    public DataResult<CarRental> rentCarToIndividualCustomer(RentalCarModel rentalCarModel) throws BusinessException {
        updateAndCheckOperationsForCarRental(rentalCarModel.getCreateCarRentalRequest());

        CarRental carRental = this.modelMapperService.forRequest().map(rentalCarModel.getCreateCarRentalRequest(),CarRental.class);
        modelMapperCorrection(carRental,rentalCarModel.getCreateCarRentalRequest());

        carRental.setEndedKilometer(null);

        CarRental createdRental = this.carRentalDao.save(carRental);

        this.carService.updateCarRentalStatus(rentalCarModel.getCreateCarRentalRequest().getCarId(),true);

        this.orderedAdditionalServiceService.add(rentalCarModel.getCreateOrderedAdditionalServiceRequestList(),carRental.getRentalId());

        return new SuccessDataResult<CarRental>(createdRental,BusinessMessages.GlobalMessages.DATA_ADDED_SUCCESSFULLY);
    }



    @Override
    public DataResult<CarRental> endCarRentalForIndividual(IndividualRentEndModel individualRentEndModel) throws BusinessException {
        checkIfCarRentalExistsById(individualRentEndModel.getEndCarRentalRequest().getRentalId());

        CarRental carRental = this.carRentalDao.getById(individualRentEndModel.getEndCarRentalRequest().getRentalId());

        carRental.setEndedKilometer(individualRentEndModel.getEndCarRentalRequest().getEndedKilometer());

        CarRental createdRental = this.carRentalDao.save(carRental);

        this.carService.updateCarKilometer(carRental.getCar().getCarId(),individualRentEndModel.getEndCarRentalRequest().getEndedKilometer());

        checkIfReturnedDayIsOutOfDateForIndividual(individualRentEndModel, carRental);

        return new SuccessDataResult<CarRental>(createdRental, BusinessMessages.CarRentalMessages.CAR_RENTAL_ENDED_SUCCESSFULLY);
    }

    @Override
    public DataResult<CarRental> endCarRentalForCorporate(CorporateRentEndModel corporateRentEndModel) throws BusinessException {
        checkIfCarRentalExistsById(corporateRentEndModel.getEndCarRentalRequest().getRentalId());

        CarRental carRental = this.carRentalDao.getById(corporateRentEndModel.getEndCarRentalRequest().getRentalId());

        carRental.setEndedKilometer(corporateRentEndModel.getEndCarRentalRequest().getEndedKilometer());

        CarRental createdRental = this.carRentalDao.save(carRental);

        this.carService.updateCarKilometer(carRental.getCar().getCarId(),corporateRentEndModel.getEndCarRentalRequest().getEndedKilometer());

        checkIfReturnedDayIsOutOfDateForCorporate(corporateRentEndModel, carRental);

        return new SuccessDataResult<CarRental>(createdRental, BusinessMessages.CarRentalMessages.CAR_RENTAL_ENDED_SUCCESSFULLY);
    }

    @Override
    public Result update(UpdateCarRentalRequest updateCarRentalRequest) throws BusinessException{
        checkIfCarRentalExistsById(updateCarRentalRequest.getRentalId());
        updateAndCheckMaintenanceStatusBeforeOperation(updateCarRentalRequest.getCarId(),
                updateCarRentalRequest.getRentalDate(), updateCarRentalRequest.getRentalReturnDate());
        updateAndCheckRentalStatusBeforeOperation(updateCarRentalRequest.getCarId(),
                updateCarRentalRequest.getRentalDate(),updateCarRentalRequest.getRentalReturnDate());
        checkIfDatesAreCorrect(updateCarRentalRequest.getRentalDate(),updateCarRentalRequest.getRentalReturnDate());

        CarRental carRental = this.modelMapperService.forRequest().map(updateCarRentalRequest,CarRental.class);

        //checkIfCarRentalTimeChanged(updateCarRentalRequest);

        this.carRentalDao.save(carRental);

        return new SuccessResult(BusinessMessages.GlobalMessages.DATA_UPDATED_SUCCESSFULLY);
    }

    @Override
    public Result delete(DeleteCarRentalRequest deleteCarRentalRequest) throws BusinessException{
        checkIfCarRentalExistsById(deleteCarRentalRequest.getRentalId());

        CarRental carRental = this.carRentalDao.getById(deleteCarRentalRequest.getRentalId());

        this.carRentalDao.delete(carRental);

        return new SuccessResult(BusinessMessages.GlobalMessages.DATA_DELETED_SUCCESSFULLY);
    }

    @Override
    public DataResult<List<CarRentalListDto>> getByCarId(int id) {
        List<CarRental> carRentals = this.carRentalDao.getAllByCar_CarId(id);

        List<CarRentalListDto> response = carRentals.stream().map(carRental->this.modelMapperService.forDto().map(carRental,CarRentalListDto.class)).collect(Collectors.toList());

        return new SuccessDataResult<List<CarRentalListDto>>(response, BusinessMessages.GlobalMessages.DATA_LISTED_SUCCESSFULLY);
    }

    @Override
    public boolean checkIfCarIsRented(int id, LocalDate localDate){
        List<CarRental> result=this.carRentalDao.getAllByCar_CarId(id);
        for (CarRental carRental : result) {
            if(carRental.getRentalReturnDate()==null ||
                    localDate.isBefore(carRental.getRentalReturnDate()) &&
                    localDate.isAfter(carRental.getRentalDate()) ||
                    localDate.isEqual(carRental.getRentalReturnDate()) &&
                    localDate.isEqual(carRental.getRentalDate()))
                return true;
        }
        return false;
    }
    public boolean checkIfCarIsRented(int id, LocalDate startDate,LocalDate endDate){
        List<CarRental> result=this.carRentalDao.getAllByCar_CarId(id);
        for (CarRental carRental : result) {
            if(carRental.getRentalReturnDate()==null ||
                    startDate.now().isBefore(carRental.getRentalReturnDate()) &&
                            startDate.now().isAfter(carRental.getRentalDate()) ||
                    endDate.now().isBefore(carRental.getRentalReturnDate()) &&
                            endDate.now().isAfter(carRental.getRentalDate()) ||
                    startDate.now().isEqual(carRental.getRentalReturnDate()) &&
                            endDate.now().isEqual(carRental.getRentalDate()))
                return true;
        }
        return false;
    }

    @Override
    public CarRental getByRentalId(int id) {
        return this.carRentalDao.getById(id);
    }

    private void updateAndCheckMaintenanceStatusBeforeOperation(int carId,LocalDate startDate,LocalDate endDate) throws BusinessException {
        this.carService.updateCarMaintenanceStatus(carId,carMaintenanceService.checkIfCarIsInMaintenance(carId, startDate,endDate));
        if(this.carService.getCarByCarId(carId).isCarMaintenanceStatus())
            throw new BusinessException(BusinessMessages.CarRentalMessages.CAR_IS_AT_MAINTENANCE);
    }
    private void updateAndCheckRentalStatusBeforeOperation(int carId,LocalDate startDate,LocalDate endDate) throws BusinessException{
        this.carService.updateCarRentalStatus(carId,checkIfCarIsRented(carId,startDate,endDate));
        if(this.carService.getCarByCarId(carId).isCarRentalStatus())
            throw new BusinessException(BusinessMessages.CarRentalMessages.CAR_IS_ALREADY_RENTED);
    }

    private void modelMapperCorrection(CarRental carRental,CreateCarRentalRequest createCarRentalRequest) {
        carRental.setRentalId(0);
        carRental.setReturnCity(this.cityService.getCityByCityId(createCarRentalRequest.getCityId()));
        carRental.setCustomer(this.customerService.getCustomerByCustomerId(createCarRentalRequest.getCustomerId()));
    }

    private void checkIfDatesAreCorrect(LocalDate startDate,LocalDate endDate) throws BusinessException{
        if(startDate.isAfter(endDate))
            throw new BusinessException(BusinessMessages.CarRentalMessages.RENTAL_DATES_ARE_NOT_CORRECT);
    }

    private void checkIfCarRentalExistsById(int id) throws NotFoundException{
        if(!this.carRentalDao.existsById(id))
            throw new NotFoundException(BusinessMessages.CarRentalMessages.RENTAL_CAR_NOT_FOUND);
    }
    private void updateAndCheckOperationsForCarRental(CreateCarRentalRequest createCarRentalRequest) throws BusinessException{
        updateAndCheckMaintenanceStatusBeforeOperation(createCarRentalRequest.getCarId(),
                createCarRentalRequest.getRentalDate(), createCarRentalRequest.getRentalReturnDate());
        updateAndCheckRentalStatusBeforeOperation(createCarRentalRequest.getCarId(),
                createCarRentalRequest.getRentalDate(),createCarRentalRequest.getRentalReturnDate());
        checkIfDatesAreCorrect(createCarRentalRequest.getRentalDate(),createCarRentalRequest.getRentalReturnDate());
    }

    private void checkIfReturnedDayIsOutOfDateForIndividual(IndividualRentEndModel individualRentEndModel , CarRental carRental) throws BusinessException {
        if(individualRentEndModel.getEndCarRentalRequest().getReturnDate() != carRental.getRentalReturnDate()) {

            CreateCarRentalRequest createCarRentalRequest = manuelMappingForCreateRent(carRental);
            createCarRentalRequest.setRentalDate(carRental.getRentalReturnDate());
            createCarRentalRequest.setRentalReturnDate(individualRentEndModel.getEndCarRentalRequest().getReturnDate());
            createCarRentalRequest.setCustomerId(carRental.getCustomer().getCustomerId());

            List<CreateOrderedAdditionalServiceRequest> services = getAdditionalServices(carRental.getRentalId());

            RentalCarModel rentalCarModel = new RentalCarModel();
            rentalCarModel.setCreateCarRentalRequest(createCarRentalRequest);
            rentalCarModel.setCreateOrderedAdditionalServiceRequestList(services);

            IndividualCustomerPaymentModel individualCustomerPaymentModel = new IndividualCustomerPaymentModel();
            individualCustomerPaymentModel.setRentalCarModel(rentalCarModel);
            individualCustomerPaymentModel.setCreditCardRequest(individualRentEndModel.getCreditCardRequest());

            this.paymentService.makePaymentForIndividualCustomer(individualCustomerPaymentModel,EnumSaveCreditCard.NO);
        }
    }

    private CreateCarRentalRequest manuelMappingForCreateRent(CarRental carRental) throws BusinessException {
        CreateCarRentalRequest createCarRentalRequest = new CreateCarRentalRequest();
        createCarRentalRequest.setCarId(carRental.getCar().getCarId());
        createCarRentalRequest.setRentDescription(BusinessMessages.CarRentalMessages.ADDITIONAL_RENT_FOR_DELIVERING_LATE);
        createCarRentalRequest.setCityId(carRental.getReturnCity().getCityId());
        createCarRentalRequest.setCustomerId(carRental.getCustomer().getCustomerId());

        return createCarRentalRequest;
    }

    private void checkIfReturnedDayIsOutOfDateForCorporate(CorporateRentEndModel corporateRentEndModel , CarRental carRental) throws BusinessException {
        if(corporateRentEndModel.getEndCarRentalRequest().getReturnDate() != carRental.getRentalReturnDate()) {

            CreateCarRentalRequest createCarRentalRequest = manuelMappingForCreateRent(carRental);
            createCarRentalRequest.setRentalDate(carRental.getRentalReturnDate());
            createCarRentalRequest.setRentalReturnDate(corporateRentEndModel.getEndCarRentalRequest().getReturnDate());
            createCarRentalRequest.setCustomerId(carRental.getCustomer().getCustomerId());

            List<CreateOrderedAdditionalServiceRequest> services = getAdditionalServices(carRental.getRentalId());

            RentalCarModel rentalCarModel = new RentalCarModel();
            rentalCarModel.setCreateCarRentalRequest(createCarRentalRequest);
            rentalCarModel.setCreateOrderedAdditionalServiceRequestList(services);

            IndividualCustomerPaymentModel individualCustomerPaymentModel = new IndividualCustomerPaymentModel();
            individualCustomerPaymentModel.setRentalCarModel(rentalCarModel);
            individualCustomerPaymentModel.setCreditCardRequest(corporateRentEndModel.getCreditCardRequest());

            this.paymentService.makePaymentForIndividualCustomer(individualCustomerPaymentModel,EnumSaveCreditCard.NO);
        }
    }

    private List<CreateOrderedAdditionalServiceRequest> getAdditionalServices(int rentalId){
        List<OrderedAdditionalService> additionalServices = this.orderedAdditionalServiceService.getOrderedAdditionalServicesByRentalId(rentalId);
        List<CreateOrderedAdditionalServiceRequest> orderedAdditionalServiceRequests = new ArrayList<CreateOrderedAdditionalServiceRequest>();
        for(int i=0;i< additionalServices.size();i++){
            orderedAdditionalServiceRequests.get(i).setServiceId(additionalServices.get(i).getAdditionalService().getServiceId());
            orderedAdditionalServiceRequests.get(i).setQuantity(additionalServices.get(i).getQuantity());
        }
        return orderedAdditionalServiceRequests;
    }
    /*
    private void checkIfCarRentalTimeChanged(UpdateCarRentalRequest updateCarRentalRequest){
        if(updateCarRentalRequest.getRentalDate()!=this.carRentalDao.getById(updateCarRentalRequest.getCarId()).getRentalDate() ||
                updateCarRentalRequest.getRentalReturnDate()!=this.carRentalDao.getById(updateCarRentalRequest.getCarId()).getRentalDate()){
            int newRentDateValue = ((int) ChronoUnit.DAYS.between(updateCarRentalRequest.getRentalDate(),updateCarRentalRequest.getRentalReturnDate()));
            this.invoiceService.updateInvoiceIfCarRentalUpdates(updateCarRentalRequest.getRentalId(), newRentDateValue,
                    updateCarRentalRequest.getRentalDate(),updateCarRentalRequest.getRentalReturnDate());
        }
    }*/
}
