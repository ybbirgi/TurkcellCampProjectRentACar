package com.turkcell.rentACar.business.concretes;

import com.turkcell.rentACar.business.abstracts.*;
import com.turkcell.rentACar.business.dtos.carRentalDtos.CarRentalListDto;
import com.turkcell.rentACar.business.requests.creates.CreateCarRentalRequest;
import com.turkcell.rentACar.business.requests.deletes.DeleteCarRentalRequest;
import com.turkcell.rentACar.business.requests.updates.UpdateCarRentalRequest;
import com.turkcell.rentACar.core.utilities.exceptions.BusinessException;
import com.turkcell.rentACar.core.utilities.mapping.ModelMapperService;
import com.turkcell.rentACar.core.utilities.results.*;
import com.turkcell.rentACar.dataAccess.abstracts.CarRentalDao;
import com.turkcell.rentACar.entities.concretes.CarRental;
import org.springframework.beans.factory.annotation.Autowired;
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
    private CustomerService customerService;
    private CityService cityService;

    @Autowired
    public CarRentalManager(CarRentalDao carRentalDao,
                            ModelMapperService modelMapperService,
                            CarService carService,
                            CarMaintenanceService carMaintenanceService,
                            CustomerService customerService,
                            CityService cityService) {
        this.carRentalDao = carRentalDao;
        this.modelMapperService = modelMapperService;
        this.carService = carService;
        this.carMaintenanceService = carMaintenanceService;
        this.customerService = customerService;
        this.cityService = cityService;
    }

    @Override
    public DataResult<List<CarRentalListDto>> getAll() {
        List<CarRental> carRentals = this.carRentalDao.findAll();
        List<CarRentalListDto> response = carRentals.stream().map(carRental->this.modelMapperService.forDto().map(carRental, CarRentalListDto.class)).collect(Collectors.toList());
        return new SuccessDataResult<List<CarRentalListDto>>(response,"CarRentals Listed Successfully");
    }

    @Override
    public Result rentCar(CreateCarRentalRequest createCarRentalRequest) throws BusinessException {
        CarRental carRental = this.modelMapperService.forRequest().map(createCarRentalRequest,CarRental.class);
        modelMapperCorrection(carRental,createCarRentalRequest);

        this.carService.updateCarMaintenanceStatus(createCarRentalRequest.getCarId(),
                carMaintenanceService.checkIfCarIsInMaintenance(createCarRentalRequest.getCarId(),
                        createCarRentalRequest.getRentalDate(),createCarRentalRequest.getRentalReturnDate()));

        if(carService.getById(createCarRentalRequest.getCarId()).getData().isCarMaintenanceStatus())
            return new ErrorResult("Car Is In Maintenance , Can't Rent This Car");

        checkIfDatesAreCorrect(createCarRentalRequest);

        carRental.setTotalPayment(calculateCarRentPrice(createCarRentalRequest));

        this.carRentalDao.save(carRental);

        this.carService.getCarByCarId(createCarRentalRequest.getCarId()).setCurrentCity(this.cityService.getCityByCityId(createCarRentalRequest.getCityId()));

        this.carService.updateCarRentalStatus(createCarRentalRequest.getCarId(),true);

        return new SuccessResult("CarRental added Successfully");
    }

    @Override
    public Result update(UpdateCarRentalRequest updateCarRentalRequest) {
        CarRental carRental = this.carRentalDao.getById(updateCarRentalRequest.getRentalId());
        carRental = this.modelMapperService.forRequest().map(updateCarRentalRequest,CarRental.class);
        this.carRentalDao.save(carRental);
        return new SuccessResult("CarRental updated Successfully");
    }

    @Override
    public Result delete(DeleteCarRentalRequest deleteCarRentalRequest) {
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

    private void modelMapperCorrection(CarRental carRental,CreateCarRentalRequest createCarRentalRequest) {
        carRental.setCustomer(this.customerService.getCustomerByCustomerId(createCarRentalRequest.getCustomerId()));
        carRental.setReturnCity(this.cityService.getCityByCityId(createCarRentalRequest.getCityId()));
    }

    private double calculateCarRentPrice(CreateCarRentalRequest createCarRentalRequest) {
        int rentedDayValue = (int) ChronoUnit.DAYS.between(createCarRentalRequest.getRentalDate(),createCarRentalRequest.getRentalReturnDate()) + 1;
        double citySwapFee = 750.0;
        if(this.carService.getCarByCarId(createCarRentalRequest.getCarId()).getCurrentCity() ==
                this.cityService.getCityByCityId(createCarRentalRequest.getCityId()))
            return this.carService.getCarByCarId(createCarRentalRequest.getCarId()).getCarDailyPrice()*rentedDayValue;
        return this.carService.getCarByCarId(createCarRentalRequest.getCarId()).getCarDailyPrice()*rentedDayValue+citySwapFee;
    }

    private void checkIfDatesAreCorrect(CreateCarRentalRequest createCarRentalRequest) throws BusinessException{
        if(createCarRentalRequest.getRentalDate().isAfter(createCarRentalRequest.getRentalReturnDate()))
            throw new BusinessException("Rental Date Must Be Earlier Than Return Date");

    }

}
