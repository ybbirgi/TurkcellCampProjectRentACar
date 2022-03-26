package com.turkcell.rentACar.business.concretes;

import com.turkcell.rentACar.business.abstracts.CarDamageService;
import com.turkcell.rentACar.business.constants.messages.BusinessMessages;
import com.turkcell.rentACar.business.dtos.carDamageDtos.CarDamageDto;
import com.turkcell.rentACar.business.dtos.carDamageDtos.CarDamageListDto;
import com.turkcell.rentACar.business.requests.creates.CreateCarDamageRequest;
import com.turkcell.rentACar.business.requests.deletes.DeleteCarDamageRequest;
import com.turkcell.rentACar.business.requests.updates.UpdateCarDamageRequest;
import com.turkcell.rentACar.core.utilities.exceptions.AlreadyExistsException;
import com.turkcell.rentACar.core.utilities.exceptions.NotFoundException;
import com.turkcell.rentACar.core.utilities.exceptions.UpdateHasNoChangesException;
import com.turkcell.rentACar.core.utilities.mapping.ModelMapperService;
import com.turkcell.rentACar.core.utilities.results.DataResult;
import com.turkcell.rentACar.core.utilities.results.Result;
import com.turkcell.rentACar.core.utilities.results.SuccessDataResult;
import com.turkcell.rentACar.core.utilities.results.SuccessResult;
import com.turkcell.rentACar.dataAccess.abstracts.CarDamageDao;
import com.turkcell.rentACar.entities.concretes.CarDamage;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class CarDamageManager implements CarDamageService{
    private CarDamageDao carDamageDao;
    private ModelMapperService modelMapperService;

    public CarDamageManager(CarDamageDao carDamageDao, ModelMapperService modelMapperService) {
        this.carDamageDao = carDamageDao;
        this.modelMapperService = modelMapperService;
    }

    @Override
    public DataResult<List<CarDamageListDto>> getAll() {
        List<CarDamage> result = this.carDamageDao.findAll();

        List<CarDamageListDto> response = result.stream().map(carDamage->this.modelMapperService.forDto().map(carDamage,CarDamageListDto.class)).collect(Collectors.toList());

        return new SuccessDataResult<List<CarDamageListDto>>(response, BusinessMessages.GlobalMessages.DATA_LISTED_SUCCESSFULLY);
    }

    @Override
    public Result add(CreateCarDamageRequest createCarDamageRequest) throws AlreadyExistsException {
        CarDamage carDamage = this.modelMapperService.forRequest().map(createCarDamageRequest,CarDamage.class);

        this.carDamageDao.save(carDamage);

        return new SuccessResult(BusinessMessages.GlobalMessages.DATA_ADDED_SUCCESSFULLY);
    }

    @Override
    public DataResult<CarDamageDto> getById(int id) throws NotFoundException {
        checkIfCarDamageExistsById(id);

        CarDamage carDamage = this.carDamageDao.getById(id);

        CarDamageDto carDamageDto = this.modelMapperService.forDto().map(carDamage,CarDamageDto.class);

        return new SuccessDataResult<CarDamageDto>(carDamageDto,BusinessMessages.GlobalMessages.DATA_LISTED_SUCCESSFULLY);

    }

    @Override
    public Result update(UpdateCarDamageRequest updateCarDamageRequest) throws NotFoundException, UpdateHasNoChangesException {
        checkIfCarDamageExistsById(updateCarDamageRequest.getCarDamageId());

        CarDamage carDamage = this.modelMapperService.forRequest().map(updateCarDamageRequest,CarDamage.class);

        this.carDamageDao.save(carDamage);

        return new SuccessResult(BusinessMessages.GlobalMessages.DATA_UPDATED_SUCCESSFULLY);
    }

    @Override
    public Result delete(DeleteCarDamageRequest deleteCarDamageRequest) throws NotFoundException {
        checkIfCarDamageExistsById(deleteCarDamageRequest.getCarDamageId());

        CarDamage carDamage = this.modelMapperService.forRequest().map(deleteCarDamageRequest,CarDamage.class);

        this.carDamageDao.delete(carDamage);

        return new SuccessResult(BusinessMessages.GlobalMessages.DATA_DELETED_SUCCESSFULLY);
    }

    private void checkIfCarDamageExistsById(int id)throws NotFoundException{
        if(!this.carDamageDao.existsById(id))
            throw new NotFoundException(BusinessMessages.CarDamageMessages.CAR_DAMAGE_NOT_FOUND);
    }
}
