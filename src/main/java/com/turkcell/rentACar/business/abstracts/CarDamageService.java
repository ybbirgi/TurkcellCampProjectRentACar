package com.turkcell.rentACar.business.abstracts;

import com.turkcell.rentACar.business.dtos.carDamageDtos.CarDamageDto;
import com.turkcell.rentACar.business.dtos.carDamageDtos.CarDamageListDto;
import com.turkcell.rentACar.business.requests.creates.CreateCarDamageRequest;
import com.turkcell.rentACar.business.requests.deletes.DeleteCarDamageRequest;
import com.turkcell.rentACar.business.requests.updates.UpdateCarDamageRequest;
import com.turkcell.rentACar.core.utilities.exceptions.AlreadyExistsException;
import com.turkcell.rentACar.core.utilities.exceptions.NotFoundException;
import com.turkcell.rentACar.core.utilities.exceptions.UpdateHasNoChangesException;
import com.turkcell.rentACar.core.utilities.results.DataResult;
import com.turkcell.rentACar.core.utilities.results.Result;

import java.util.List;

public interface CarDamageService {
    DataResult<List<CarDamageListDto>> getAll();
    Result add(CreateCarDamageRequest createCarDamageRequest) throws AlreadyExistsException;
    DataResult<CarDamageDto> getById(int id) throws NotFoundException;
    Result update(UpdateCarDamageRequest updateCarDamageRequest) throws NotFoundException, UpdateHasNoChangesException;
    Result delete(DeleteCarDamageRequest deleteCarDamageRequest) throws NotFoundException;
}
