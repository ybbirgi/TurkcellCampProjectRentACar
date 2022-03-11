package com.turkcell.rentACar.business.concretes;

import com.turkcell.rentACar.business.abstracts.CarMaintenanceService;
import com.turkcell.rentACar.business.abstracts.CarRentalService;
import com.turkcell.rentACar.business.abstracts.CarService;
import com.turkcell.rentACar.business.abstracts.CityService;
import com.turkcell.rentACar.business.dtos.carDtos.CarListDto;
import com.turkcell.rentACar.business.requests.creates.CreateCarRequest;
import com.turkcell.rentACar.business.requests.deletes.DeleteCarRequest;
import com.turkcell.rentACar.business.requests.updates.UpdateCarRequest;
import com.turkcell.rentACar.core.utilities.exceptions.BusinessException;
import com.turkcell.rentACar.core.utilities.mapping.ModelMapperManager;
import com.turkcell.rentACar.core.utilities.results.*;
import com.turkcell.rentACar.dataAccess.abstracts.CarDao;
import com.turkcell.rentACar.entities.concretes.Car;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class CarManager implements CarService {
    private CarDao carDao;
    private ModelMapperManager modelMapperService;
    private CarRentalService carRentalService;
    private CarMaintenanceService carMaintenanceService;
    private CityService cityService;

    @Autowired
    public CarManager(CarDao carDao, ModelMapperManager modelMapperService, @Lazy CarRentalService carRentalService, @Lazy CarMaintenanceService carMaintenanceService,CityService cityService) {
        this.carDao = carDao;
        this.modelMapperService = modelMapperService;
        this.carRentalService = carRentalService;
        this.carMaintenanceService = carMaintenanceService;
        this.cityService = cityService;
    }

    @Override
    public DataResult<List<CarListDto>> getAll() {
        List<Car> result = this.carDao.findAll();
        if(result.isEmpty())
            return new ErrorDataResult<List<CarListDto>>(null,"Current List is Empty");
        for (Car car:result) {
            updateCarMaintenanceStatus(car.getCarId(),this.carMaintenanceService.checkIfCarIsInMaintenance(car.getCarId(), LocalDate.now()));
            updateCarRentalStatus(car.getCarId(),this.carRentalService.checkIfCarIsRented(car.getCarId(),LocalDate.now()));
        }
        List<CarListDto> response = result.stream().map(car->this.modelMapperService.forDto().map(car,CarListDto.class)).collect(Collectors.toList());
        return new SuccessDataResult<List<CarListDto>>(response,"Cars Listed succesfully");
    }

    @Override
    public Result add(CreateCarRequest createCarRequest) throws BusinessException {
        Car car = this.modelMapperService.forRequest().map(createCarRequest,Car.class);
        car.setCurrentCity(this.cityService.getCityByCityId(createCarRequest.getCityId()));
        this.carDao.save(car);
        return new SuccessResult("Car Added Successfully");
    }

    @Override
    public Result update(UpdateCarRequest updateCarRequest) throws BusinessException{
        checkIfCarExists(updateCarRequest.getCarId());
        Car car = this.modelMapperService.forRequest().map(updateCarRequest,Car.class);
        checkIfSameCar(car.getBrand().getBrandName(),car.getColor().getColorName());
        this.carDao.save(car);
        return new SuccessResult("Car Updated Successfully");
    }

    @Override
    public Result delete(DeleteCarRequest deleteCarRequest) throws BusinessException{
        checkIfCarExists(deleteCarRequest.getCarId());
        Car car = this.modelMapperService.forRequest().map(deleteCarRequest,Car.class);
        this.carDao.delete(car);
        return new SuccessResult("Car Deleted Successfully");
    }

    @Override
    public DataResult<CarListDto> getById(int id) throws BusinessException{
        Car car = this.carDao.getById(id);
        checkIfCarExists(id);
        updateCarMaintenanceStatus(car.getCarId(),this.carMaintenanceService.checkIfCarIsInMaintenance(car.getCarId(), LocalDate.now()));
        updateCarRentalStatus(car.getCarId(),this.carRentalService.checkIfCarIsRented(car.getCarId(),LocalDate.now()));
        CarListDto carListDto = this.modelMapperService.forDto().map(car,CarListDto.class);
        return new SuccessDataResult<CarListDto>(carListDto,"Car Getted Succesfully");
    }

    @Override
    public DataResult<List<CarListDto>> getByCarDailyPriceLessThanOrEqual(Double carDailyPrice) {
        Sort sort = Sort.by(Sort.Direction.DESC,"carDailyPrice");
        List<Car> result = this.carDao.getByCarDailyPriceLessThanEqual(carDailyPrice,sort);
        List<CarListDto> response = result.stream().map(car->this.modelMapperService.forDto().map(car,CarListDto.class)).collect(Collectors.toList());
        return new SuccessDataResult<List<CarListDto>>(response,"Cars Listed succesfully");
    }

    @Override
    public DataResult<List<CarListDto>> getAllPaged(int pageNo, int pageSize) {
        Pageable pageable = PageRequest.of(pageNo,pageSize);
        List<Car> result = this.carDao.findAll();
        for(Car car : result){
            updateCarMaintenanceStatus(car.getCarId(),this.carMaintenanceService.checkIfCarIsInMaintenance(car.getCarId(), LocalDate.now()));
            updateCarRentalStatus(car.getCarId(),this.carRentalService.checkIfCarIsRented(car.getCarId(),LocalDate.now()));
        }
        List<CarListDto> response = result.stream().map(car->this.modelMapperService.forDto().map(car,CarListDto.class)).collect(Collectors.toList());
        return new SuccessDataResult<List<CarListDto>>(response,"Cars Listed succesfully");
    }

    @Override
    public DataResult<List<CarListDto>> getAllSorted(Sort.Direction direction) {
        Sort sort = Sort.by(direction,"carDailyPrice");
        List<Car> result =this.carDao.findAll(sort);
        for(Car car : result){
            updateCarMaintenanceStatus(car.getCarId(),this.carMaintenanceService.checkIfCarIsInMaintenance(car.getCarId(), LocalDate.now()));
            updateCarRentalStatus(car.getCarId(),this.carRentalService.checkIfCarIsRented(car.getCarId(),LocalDate.now()));
        }
        List<CarListDto> response = result.stream().map(car->this.modelMapperService.forDto().map(car,CarListDto.class)).collect(Collectors.toList());
        return new SuccessDataResult<List<CarListDto>>(response,"Cars Listed succesfully");
    }

    @Override
    public void updateCarRentalStatus(int id,boolean status) {
        Car car = this.carDao.getById(id);
        car.setCarRentalStatus(status);
        carDao.save(car);
    }

    @Override
    public void updateCarMaintenanceStatus(int id,boolean status) {
        Car car = this.carDao.getById(id);
        car.setCarMaintenanceStatus(status);
        carDao.save(car);
    }

    @Override
    public Car getCarByCarId(int id) {
        return this.carDao.getById(id);
    }

    void checkIfCarExists(int id) throws BusinessException{
        if(!this.carDao.existsById(id))
            throw new BusinessException("There is not any Car with This Id");
    }
    void checkIfSameCar(String brandName,String colorName) throws BusinessException{
        if(!this.carDao.existsByBrand_BrandName(brandName) && !this.carDao.existsByColor_ColorName(colorName))
            throw new BusinessException("There is not any Car with This Id");
    }
}
