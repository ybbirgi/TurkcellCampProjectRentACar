package com.turkcell.rentACar.business.concretes;

import com.turkcell.rentACar.business.abstracts.*;
import com.turkcell.rentACar.business.dtos.carRentalDtos.CarRentalListDto;
import com.turkcell.rentACar.business.requests.creates.CreateCarRentalRequest;
import com.turkcell.rentACar.business.requests.deletes.DeleteCarRentalRequest;
import com.turkcell.rentACar.business.requests.updates.UpdateCarRentalRequest;
import com.turkcell.rentACar.core.utilities.exceptions.BusinessException;
import com.turkcell.rentACar.core.utilities.exceptions.NotFoundException;
import com.turkcell.rentACar.core.utilities.mapping.ModelMapperService;
import com.turkcell.rentACar.core.utilities.results.*;
import com.turkcell.rentACar.dataAccess.abstracts.CarRentalDao;
import com.turkcell.rentACar.entities.concretes.CarRental;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
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

    @Autowired
    public CarRentalManager(CarRentalDao carRentalDao,
                            ModelMapperService modelMapperService,
                            CarService carService,
                            CarMaintenanceService carMaintenanceService,
                            CityService cityService,
                            @Lazy OrderedAdditionalServiceService orderedAdditionalServiceService,
                            CustomerService customerService,
                            @Lazy InvoiceService invoiceService) {
        this.carRentalDao = carRentalDao;
        this.modelMapperService = modelMapperService;
        this.carService = carService;
        this.carMaintenanceService = carMaintenanceService;
        this.cityService = cityService;
        this.orderedAdditionalServiceService = orderedAdditionalServiceService;
        this.customerService = customerService;
        this.invoiceService = invoiceService;
    }

    @Override
    public DataResult<List<CarRentalListDto>> getAll() {
        List<CarRental> carRentals = this.carRentalDao.findAll();

        List<CarRentalListDto> response = carRentals.stream().map(carRental->
                this.modelMapperService.forDto().map(carRental, CarRentalListDto.class)).collect(Collectors.toList());

        return new SuccessDataResult<List<CarRentalListDto>>(response,"CarRentals Listed Successfully");
    }

    @Override
    public DataResult<Integer> rentCar(CreateCarRentalRequest createCarRentalRequest) throws BusinessException {
        updateAndCheckMaintenanceStatusBeforeOperation(createCarRentalRequest.getCarId(),
                createCarRentalRequest.getRentalDate(), createCarRentalRequest.getRentalReturnDate());
        updateAndCheckRentalStatusBeforeOperation(createCarRentalRequest.getCarId(),
                createCarRentalRequest.getRentalDate(),createCarRentalRequest.getRentalReturnDate());
        checkIfDatesAreCorrect(createCarRentalRequest.getRentalDate(),createCarRentalRequest.getRentalReturnDate());

        CarRental carRental = this.modelMapperService.forRequest().map(createCarRentalRequest,CarRental.class);
        modelMapperCorrection(carRental,createCarRentalRequest);

        this.carRentalDao.save(carRental);

        this.carService.updateCarRentalStatus(createCarRentalRequest.getCarId(),true);

        return new SuccessDataResult<Integer>(carRental.getRentalId(),"CarRental added Successfully");
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

        checkIfCarRentalTimeChanged(updateCarRentalRequest);

        this.carRentalDao.save(carRental);

        return new SuccessResult("CarRental updated Successfully");
    }

    @Override
    public Result delete(DeleteCarRentalRequest deleteCarRentalRequest) throws BusinessException{
        checkIfCarRentalExistsById(deleteCarRentalRequest.getRentalId());

        CarRental carRental = this.carRentalDao.getById(deleteCarRentalRequest.getRentalId());

        this.carRentalDao.delete(carRental);

        return new SuccessResult("CarRental deleted Successfully");
    }

    @Override
    public DataResult<List<CarRentalListDto>> getByCarId(int id) {
        List<CarRental> carRentals = this.carRentalDao.getAllByCar_CarId(id);
        if(carRentals.isEmpty())
            return new ErrorDataResult<List<CarRentalListDto>>(null,"Current List is Empty");
        List<CarRentalListDto> response = carRentals.stream().map(carMaintenance->this.modelMapperService.forDto().map(carRentals,CarRentalListDto.class)).collect(Collectors.toList());
        return new SuccessDataResult<List<CarRentalListDto>>(response,"Car Rentals Listed Successfully");
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
            throw new BusinessException("Car Is In Maintenance , Can't Rent This Car");
    }
    private void updateAndCheckRentalStatusBeforeOperation(int carId,LocalDate startDate,LocalDate endDate) throws BusinessException{
        this.carService.updateCarRentalStatus(carId,checkIfCarIsRented(carId,startDate,endDate));
        if(this.carService.getCarByCarId(carId).isCarRentalStatus())
            throw new BusinessException("Car is Rented , Can't Go Under Maintenance");
    }

    private void modelMapperCorrection(CarRental carRental,CreateCarRentalRequest createCarRentalRequest) {
        carRental.setRentalId(0);
        carRental.setReturnCity(this.cityService.getCityByCityId(createCarRentalRequest.getCityId()));
        carRental.setCustomer(this.customerService.getCustomerByCustomerId(createCarRentalRequest.getCustomerId()));
    }

    private void checkIfDatesAreCorrect(LocalDate startDate,LocalDate endDate) throws BusinessException{
        if(startDate.isAfter(endDate))
            throw new BusinessException("Rental Date Must Be Earlier Than Return Date");
    }

    private void checkIfCarRentalExistsById(int id) throws NotFoundException{
        if(!this.carRentalDao.existsById(id))
            throw new NotFoundException("There is not any CarRental with This Id");
    }
    private void checkIfCarRentalTimeChanged(UpdateCarRentalRequest updateCarRentalRequest){
        if(updateCarRentalRequest.getRentalDate()!=this.carRentalDao.getById(updateCarRentalRequest.getCarId()).getRentalDate() ||
                updateCarRentalRequest.getRentalReturnDate()!=this.carRentalDao.getById(updateCarRentalRequest.getCarId()).getRentalDate()){
            int newRentDateValue = ((int) ChronoUnit.DAYS.between(updateCarRentalRequest.getRentalDate(),updateCarRentalRequest.getRentalReturnDate()));
            this.invoiceService.updateInvoiceIfCarRentalUpdates(updateCarRentalRequest.getRentalId(), newRentDateValue,
                    updateCarRentalRequest.getRentalDate(),updateCarRentalRequest.getRentalReturnDate());
        }
    }
}
