package com.turkcell.rentACar.business.abstracts;

import com.turkcell.rentACar.business.dtos.carDtos.CarListDto;
import com.turkcell.rentACar.business.requests.creates.CreateCarRequest;
import com.turkcell.rentACar.business.requests.deletes.DeleteCarRequest;
import com.turkcell.rentACar.business.requests.updates.UpdateCarRequest;
import com.turkcell.rentACar.core.utilities.exceptions.BusinessException;
import com.turkcell.rentACar.core.utilities.exceptions.NotFoundException;
import com.turkcell.rentACar.core.utilities.results.DataResult;
import com.turkcell.rentACar.core.utilities.results.Result;
import com.turkcell.rentACar.entities.concretes.Car;
import org.springframework.data.domain.Sort;

import java.util.List;

public interface CarService {
    DataResult<List<CarListDto>> getAll();
    Result add(CreateCarRequest createCarRequest);
    Result update(UpdateCarRequest updateCarRequest) throws NotFoundException;
    Result delete(DeleteCarRequest deleteCarRequest) throws NotFoundException;
    DataResult<CarListDto> getById(int id) throws NotFoundException;
    DataResult<List<CarListDto>> getByCarDailyPriceLessThanOrEqual(Double carDailyPrice);
    DataResult<List<CarListDto>> getAllPaged(int pageNo,int pageSize);
    DataResult<List<CarListDto>> getAllSorted(Sort.Direction direction);
    void updateCarRentalStatus(int id,boolean status);
    void updateCarMaintenanceStatus(int id,boolean status);
    void updateCarKilometer(int id,int carKilometer);
    Car getCarByCarId(int id);
    }
