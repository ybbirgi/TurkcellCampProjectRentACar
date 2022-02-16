package com.turkcell.rentACar.business.concretes;

import com.turkcell.rentACar.business.abstracts.CarService;
import com.turkcell.rentACar.business.dtos.CarListDto;
import com.turkcell.rentACar.business.requests.CreateCarRequest;
import com.turkcell.rentACar.business.requests.DeleteCarRequest;
import com.turkcell.rentACar.business.requests.UpdateCarRequest;
import com.turkcell.rentACar.core.utilities.mapping.ModelMapperManager;
import com.turkcell.rentACar.dataAccess.abstracts.CarDao;
import com.turkcell.rentACar.entities.concretes.Car;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class CarManager implements CarService {
    private CarDao carDao;
    private ModelMapperManager modelMapperService;

    @Autowired
    public CarManager(CarDao carDao, ModelMapperManager modelMapperService) {
        this.carDao = carDao;
        this.modelMapperService = modelMapperService;
    }


    @Override
    public List<CarListDto> getAll() {
        List<Car> result = this.carDao.findAll();
        List<CarListDto> response = result.stream().map(car->this.modelMapperService.forDto().map(car,CarListDto.class)).collect(Collectors.toList());
        return response;
    }

    @Override
    public void add(CreateCarRequest createCarRequest) throws Exception {
        Car car = this.modelMapperService.forRequest().map(createCarRequest,Car.class);
        this.carDao.save(car);
    }

    @Override
    public void update(UpdateCarRequest updateCarRequest) {
        Car car = carDao.getById(updateCarRequest.getCarId());
        car = this.modelMapperService.forRequest().map(updateCarRequest,Car.class);
        this.carDao.save(car);
    }

    @Override
    public void delete(DeleteCarRequest DeleteCarRequest) {
        Car car = this.modelMapperService.forRequest().map(DeleteCarRequest,Car.class);
        this.carDao.delete(car);
    }

    @Override
    public CarListDto getById(int id) {
        Car car = carDao.getById(id);
        CarListDto carListDto = this.modelMapperService.forDto().map(car,CarListDto.class);
        return carListDto;
    }
}
