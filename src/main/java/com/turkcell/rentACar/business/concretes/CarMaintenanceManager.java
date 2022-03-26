package com.turkcell.rentACar.business.concretes;
import com.turkcell.rentACar.business.abstracts.CarMaintenanceService;
import com.turkcell.rentACar.business.abstracts.CarRentalService;
import com.turkcell.rentACar.business.abstracts.CarService;
import com.turkcell.rentACar.business.constants.messages.BusinessMessages;
import com.turkcell.rentACar.business.dtos.carMaintenanceDtos.CarMaintenanceListDto;
import com.turkcell.rentACar.business.requests.creates.CreateCarMaintenanceRequest;
import com.turkcell.rentACar.business.requests.creates.CreateCarRentalRequest;
import com.turkcell.rentACar.business.requests.deletes.DeleteCarMaintenanceRequest;
import com.turkcell.rentACar.business.requests.updates.UpdateCarMaintenanceRequest;
import com.turkcell.rentACar.core.utilities.exceptions.BusinessException;
import com.turkcell.rentACar.core.utilities.exceptions.CarAlreadyIsInMaintenance;
import com.turkcell.rentACar.core.utilities.exceptions.NotFoundException;
import com.turkcell.rentACar.core.utilities.mapping.ModelMapperService;
import com.turkcell.rentACar.core.utilities.results.*;
import com.turkcell.rentACar.dataAccess.abstracts.CarMaintenanceDao;
import com.turkcell.rentACar.entities.concretes.CarMaintenance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;
@Service
public class CarMaintenanceManager implements CarMaintenanceService {

    private ModelMapperService modelMapperService;
    private CarMaintenanceDao carMaintenanceDao;
    private CarService carService;
    private CarRentalService carRentalService;

    @Autowired
    public CarMaintenanceManager(ModelMapperService modelMapperService, CarMaintenanceDao carMaintenanceDao,@Lazy CarService carService,@Lazy CarRentalService carRentalService) {
        this.modelMapperService = modelMapperService;
        this.carMaintenanceDao = carMaintenanceDao;
        this.carService = carService;
        this.carRentalService = carRentalService;
    }

    @Override
    public DataResult<List<CarMaintenanceListDto>> getAll() {
        List<CarMaintenance> result=this.carMaintenanceDao.findAll();

        List<CarMaintenanceListDto> response = result.stream().map(carMaintenance->this.modelMapperService.forDto().
                map(carMaintenance,CarMaintenanceListDto.class)).collect(Collectors.toList());

        return new SuccessDataResult<List<CarMaintenanceListDto>>(response, BusinessMessages.GlobalMessages.DATA_LISTED_SUCCESSFULLY);
    }

    @Override
    public Result sendToMaintenance(CreateCarMaintenanceRequest createCarMaintenanceRequest) throws BusinessException {
        updateAndCheckRentalStatusBeforeOperation(createCarMaintenanceRequest.getCarId(),createCarMaintenanceRequest.getReturnDate());
        updateAndCheckMaintenanceStatusBeforeOperation(createCarMaintenanceRequest.getCarId());

        CarMaintenance carMaintenance =this.modelMapperService.forRequest().map(createCarMaintenanceRequest,CarMaintenance.class);
        carMaintenance.setMaintenanceId(0);

        this.carMaintenanceDao.save(carMaintenance);
        this.carService.updateCarMaintenanceStatus(createCarMaintenanceRequest.getCarId(),true);

        return new SuccessResult(BusinessMessages.GlobalMessages.DATA_ADDED_SUCCESSFULLY);
    }

    @Override
    public Result update(UpdateCarMaintenanceRequest updateCarMaintenanceRequest)  throws BusinessException{
        checkIfCarMaintenanceExistsById(updateCarMaintenanceRequest.getMaintenanceId());
        updateAndCheckRentalStatusBeforeOperation(updateCarMaintenanceRequest.getCarId(),updateCarMaintenanceRequest.getReturnDate());
        updateAndCheckMaintenanceStatusBeforeOperation(updateCarMaintenanceRequest.getCarId());

        CarMaintenance carMaintenance =this.modelMapperService.forRequest().map(updateCarMaintenanceRequest,CarMaintenance.class);

        this.carMaintenanceDao.save(carMaintenance);

        return new SuccessResult(BusinessMessages.GlobalMessages.DATA_UPDATED_SUCCESSFULLY);
    }

    @Override
    public Result delete(DeleteCarMaintenanceRequest deleteCarMaintenanceRequest) throws BusinessException{
        checkIfCarMaintenanceExistsById(deleteCarMaintenanceRequest.getMaintenanceId());
        CarMaintenance carMaintenance =this.modelMapperService.forRequest().map(deleteCarMaintenanceRequest,CarMaintenance.class);
        this.carMaintenanceDao.delete(carMaintenance);
        return new SuccessResult(BusinessMessages.GlobalMessages.DATA_DELETED_SUCCESSFULLY);
    }

    @Override
    public DataResult<List<CarMaintenanceListDto>> getByCarId(int id) {
        List<CarMaintenance> carMaintenanceList = carMaintenanceDao.getAllByCar_CarId(id);

        List<CarMaintenanceListDto> response = carMaintenanceList.stream().map(carMaintenance->this.modelMapperService.forDto().map(carMaintenance,CarMaintenanceListDto.class)).collect(Collectors.toList());

        return new SuccessDataResult<List<CarMaintenanceListDto>>(response,BusinessMessages.GlobalMessages.DATA_LISTED_SUCCESSFULLY);
    }

    @Override
    public boolean checkIfCarIsInMaintenance(int id,LocalDate localDate){
        List<CarMaintenance> result=this.carMaintenanceDao.getAllByCar_CarId(id);
        for (CarMaintenance carMaintenance : result) {
            if((carMaintenance.getReturnDate()==null)||
                    localDate.now().isBefore(carMaintenance.getReturnDate()) ||
                        localDate.now().isEqual(carMaintenance.getReturnDate())) {
                return true;
            }
        }
        return false;
    }

    @Override
    public boolean checkIfCarIsInMaintenance(int id, LocalDate startDate, LocalDate endDate) {
        List<CarMaintenance> result=this.carMaintenanceDao.getAllByCar_CarId(id);
        for (CarMaintenance carMaintenance : result) {
            if((carMaintenance.getReturnDate()==null)||
                    startDate.now().isAfter(carMaintenance.getReturnDate()) &&
                            endDate.now().isBefore(carMaintenance.getReturnDate())||
                    startDate.now().isEqual(carMaintenance.getReturnDate()) &&
                            endDate.now().isEqual(carMaintenance.getReturnDate())) {
                return true;
            }
        }
        return false;
    }
    private void updateAndCheckRentalStatusBeforeOperation(int carId,LocalDate returnDate) throws BusinessException{
        this.carService.updateCarRentalStatus(carId,this.carRentalService.checkIfCarIsRented(carId,returnDate));
        if(this.carService.getCarByCarId(carId).isCarRentalStatus())
            throw new BusinessException(BusinessMessages.CarMaintenanceMessages.CAR_IS_RENTED);
    }

    private void updateAndCheckMaintenanceStatusBeforeOperation(int carId) throws CarAlreadyIsInMaintenance {
        this.carService.updateCarMaintenanceStatus(carId,checkIfCarIsInMaintenance(carId,LocalDate.now()));
        if(this.carService.getCarByCarId(carId).isCarMaintenanceStatus())
            throw new CarAlreadyIsInMaintenance(BusinessMessages.CarMaintenanceMessages.CAR_IS_ALREADY_AT_MAINTENANCE);
    }
    private void checkIfCarMaintenanceExistsById(int id) throws NotFoundException{
        if(!this.carMaintenanceDao.existsByMaintenanceId(id))
            throw new NotFoundException(BusinessMessages.CarMaintenanceMessages.CAR_MAINTENANCE_NOT_FOUND);
    }
}
