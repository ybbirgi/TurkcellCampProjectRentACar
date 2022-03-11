package com.turkcell.rentACar.business.abstracts;

import com.turkcell.rentACar.business.dtos.carDtos.CarListDto;
import com.turkcell.rentACar.business.requests.creates.CreateCarRequest;
import com.turkcell.rentACar.business.requests.deletes.DeleteCarRequest;
import com.turkcell.rentACar.business.requests.updates.UpdateCarRequest;
import com.turkcell.rentACar.core.utilities.exceptions.BusinessException;
import com.turkcell.rentACar.core.utilities.results.DataResult;
import com.turkcell.rentACar.core.utilities.results.Result;
import com.turkcell.rentACar.entities.concretes.Car;
import org.springframework.data.domain.Sort;

import java.util.List;

public interface CarService {
    DataResult<List<CarListDto>> getAll();
    Result add(CreateCarRequest createCarRequest) throws BusinessException;
    Result update(UpdateCarRequest updateCarRequest) throws BusinessException;
    Result delete(DeleteCarRequest deleteCarRequest) throws BusinessException;
    DataResult<CarListDto> getById(int id) throws BusinessException;
    DataResult<List<CarListDto>> getByCarDailyPriceLessThanOrEqual(Double carDailyPrice);
    DataResult<List<CarListDto>> getAllPaged(int pageNo,int pageSize);
    DataResult<List<CarListDto>> getAllSorted(Sort.Direction direction);
    void updateCarRentalStatus(int id,boolean status);
    void updateCarMaintenanceStatus(int id,boolean status);
    Car getCarByCarId(int id);
    }
