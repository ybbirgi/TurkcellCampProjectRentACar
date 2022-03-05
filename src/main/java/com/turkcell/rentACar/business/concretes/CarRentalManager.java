package com.turkcell.rentACar.business.concretes;

import com.turkcell.rentACar.business.abstracts.CarMaintenanceService;
import com.turkcell.rentACar.business.abstracts.CarRentalService;
import com.turkcell.rentACar.business.abstracts.CarService;
import com.turkcell.rentACar.business.dtos.CarRentalListDto;
import com.turkcell.rentACar.business.requests.creates.CreateCarRentalRequest;
import com.turkcell.rentACar.business.requests.deletes.DeleteCarRentalRequest;
import com.turkcell.rentACar.business.requests.updates.UpdateCarRentalRequest;
import com.turkcell.rentACar.core.utilities.mapping.ModelMapperService;
import com.turkcell.rentACar.core.utilities.results.*;
import com.turkcell.rentACar.dataAccess.abstracts.CarRentalDao;
import com.turkcell.rentACar.entities.concretes.CarRental;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class CarRentalManager implements CarRentalService {
    private CarRentalDao carRentalDao;
    private ModelMapperService modelMapperService;
    private CarService carService;
    private CarMaintenanceService carMaintenanceService;
    @Autowired
    public CarRentalManager(CarRentalDao carRentalDao, ModelMapperService modelMapperService, CarService carService, CarMaintenanceService carMaintenanceService) {
        this.carRentalDao = carRentalDao;
        this.modelMapperService = modelMapperService;
        this.carService = carService;
        this.carMaintenanceService = carMaintenanceService;
    }

    @Override
    public DataResult<List<CarRentalListDto>> getAll() {
        List<CarRental> carRentals = this.carRentalDao.findAll();
        if(carRentals.isEmpty())
                return new ErrorDataResult<List<CarRentalListDto>>(null,"Current List is Empty");
        List<CarRentalListDto> response = carRentals.stream().map(carRental->this.modelMapperService.forDto().map(carRental, CarRentalListDto.class)).collect(Collectors.toList());
        return new SuccessDataResult<List<CarRentalListDto>>(response,"CarRentals Listed Successfully");
    }

    @Override
    public Result add(CreateCarRentalRequest createCarRentalRequest){
        CarRental carRental = this.modelMapperService.forRequest().map(createCarRentalRequest,CarRental.class);
        carRental.setRentalId(0);
        this.carService.updateCarMaintenanceStatus(createCarRentalRequest.getCarId(),
                carMaintenanceService.checkIfCarIsInMaintenance(createCarRentalRequest.getCarId(),createCarRentalRequest.getRentalReturnDate()));
        System.out.println("Car Maintenance Status:"+ carService.getById(createCarRentalRequest.getCarId()).getData().isCarMaintenanceStatus());
        if(carService.getById(createCarRentalRequest.getCarId()).getData().isCarMaintenanceStatus())
            return new ErrorResult("Car Is In Maintenance , Can't Rent This Car");
        this.carRentalDao.save(carRental);
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
        int flag=0;
        for (CarRental carRental : result) {
            if(carRental.getRentalReturnDate()==null ||
                    localDate.isBefore(carRental.getRentalReturnDate()) &&
                    localDate.isAfter(carRental.getRentalDate()) ||
                    localDate.isEqual(carRental.getRentalReturnDate()) &&
                    localDate.isEqual(carRental.getRentalDate()))
                flag++;
        }
        if(flag!=0)
            return true;
        else
            return false;
    }
}
