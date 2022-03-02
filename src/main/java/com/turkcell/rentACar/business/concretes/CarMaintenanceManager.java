package com.turkcell.rentACar.business.concretes;

import com.turkcell.rentACar.business.abstracts.CarMaintenanceService;
import com.turkcell.rentACar.business.dtos.CarMaintenanceListDto;
import com.turkcell.rentACar.business.requests.creates.CreateCarMaintenanceRequest;
import com.turkcell.rentACar.business.requests.deletes.DeleteCarMaintenanceRequest;
import com.turkcell.rentACar.business.requests.updates.UpdateCarMaintenanceRequest;
import com.turkcell.rentACar.core.utilities.mapping.ModelMapperService;
import com.turkcell.rentACar.core.utilities.results.*;
import com.turkcell.rentACar.dataAccess.abstracts.CarDao;
import com.turkcell.rentACar.dataAccess.abstracts.CarMaintenanceDao;
import com.turkcell.rentACar.entities.concretes.CarMaintenance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class CarMaintenanceManager implements CarMaintenanceService {

    private ModelMapperService modelMapperService;
    private CarDao carDao;
    private CarMaintenanceDao carMaintenanceDao;

    @Autowired
    public CarMaintenanceManager(ModelMapperService modelMapperService, CarDao carDao, CarMaintenanceDao carMaintenanceDao) {
        this.modelMapperService = modelMapperService;
        this.carDao = carDao;
        this.carMaintenanceDao = carMaintenanceDao;
    }

    @Override
    public DataResult<List<CarMaintenanceListDto>> getAll() {
        List<CarMaintenance> result=this.carMaintenanceDao.findAll();
        if(result.isEmpty())
            return new ErrorDataResult<List<CarMaintenanceListDto>>(null,"Current List is Empty");
        List<CarMaintenanceListDto> response = result.stream().map(carMaintenance->this.modelMapperService.forDto().map(carMaintenance,CarMaintenanceListDto.class)).collect(Collectors.toList());
        return new SuccessDataResult<List<CarMaintenanceListDto>>(response,"Car Maintenances Listed Successfully");
    }

    @Override
    public Result add(CreateCarMaintenanceRequest createCarMaintenanceRequest) {
        CarMaintenance carMaintenance =this.modelMapperService.forRequest().map(createCarMaintenanceRequest,CarMaintenance.class);
        this.carMaintenanceDao.save(carMaintenance);
        return new SuccessResult("Car Maintenance Added Successfully");
    }

    @Override
    public Result update(int id,UpdateCarMaintenanceRequest updateCarMaintenanceRequest) {
        CarMaintenance carMaintenance =this.modelMapperService.forRequest().map(updateCarMaintenanceRequest,CarMaintenance.class);
        this.carMaintenanceDao.save(carMaintenance);
        return new SuccessResult("Car Maintenance Updated Successfully");
    }

    @Override
    public Result delete(DeleteCarMaintenanceRequest deleteCarMaintenanceRequest) {
        CarMaintenance carMaintenance =this.modelMapperService.forRequest().map(deleteCarMaintenanceRequest,CarMaintenance.class);
        this.carMaintenanceDao.delete(carMaintenance);
        return new SuccessResult("Car Maintenance Deleted Successfully");
    }

    @Override
    public DataResult<List<CarMaintenanceListDto>> getByCarId(int id) {
        List<CarMaintenance> carMaintenanceList = carMaintenanceDao.getAllByCar_CarId(id);
        if(carMaintenanceList.isEmpty())
            return new ErrorDataResult<List<CarMaintenanceListDto>>(null,"Current List is Empty");
        List<CarMaintenanceListDto> response = carMaintenanceList.stream().map(carMaintenance->this.modelMapperService.forDto().map(carMaintenance,CarMaintenanceListDto.class)).collect(Collectors.toList());
        return new SuccessDataResult<List<CarMaintenanceListDto>>(response,"Car Maintenances Listed Successfully");
    }
}
