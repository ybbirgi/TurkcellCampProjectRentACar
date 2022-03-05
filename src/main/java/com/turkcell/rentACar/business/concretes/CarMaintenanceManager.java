package com.turkcell.rentACar.business.concretes;

import com.turkcell.rentACar.business.abstracts.CarMaintenanceService;
import com.turkcell.rentACar.business.abstracts.CarRentalService;
import com.turkcell.rentACar.business.abstracts.CarService;
import com.turkcell.rentACar.business.dtos.CarMaintenanceListDto;
import com.turkcell.rentACar.business.requests.creates.CreateCarMaintenanceRequest;
import com.turkcell.rentACar.business.requests.deletes.DeleteCarMaintenanceRequest;
import com.turkcell.rentACar.business.requests.updates.UpdateCarMaintenanceRequest;
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
        if(result.isEmpty())
            return new ErrorDataResult<List<CarMaintenanceListDto>>(null,"Current List is Empty");
        List<CarMaintenanceListDto> response = result.stream().map(carMaintenance->this.modelMapperService.forDto().
                map(carMaintenance,CarMaintenanceListDto.class)).collect(Collectors.toList());
        return new SuccessDataResult<List<CarMaintenanceListDto>>(response,"Car Maintenances Listed Successfully");
    }

    @Override
    public Result add(CreateCarMaintenanceRequest createCarMaintenanceRequest){
        CarMaintenance carMaintenance =this.modelMapperService.forRequest().map(createCarMaintenanceRequest,CarMaintenance.class);
        carMaintenance.setMaintenanceId(0);
        this.carService.updateCarRentalStatus(createCarMaintenanceRequest.getCarId(),
                this.carRentalService.checkIfCarIsRented(createCarMaintenanceRequest.getCarId(),createCarMaintenanceRequest.getReturnDate()));
        System.out.println("Car Rental Status:"+ carService.getById(createCarMaintenanceRequest.getCarId()).getData().isCarRentalStatus());
        if(this.carService.getById(createCarMaintenanceRequest.getCarId()).getData().isCarRentalStatus())
            return new ErrorResult("Car is Rented , Can't Go Under Maintenance");
        this.carMaintenanceDao.save(carMaintenance);
        this.carService.updateCarMaintenanceStatus(createCarMaintenanceRequest.getCarId(),true);
        return new SuccessResult("Car Maintenance Added Successfully");
    }

    @Override
    public Result update(UpdateCarMaintenanceRequest updateCarMaintenanceRequest) {
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
    //local date = rental date
    @Override
    public boolean checkIfCarIsInMaintenance(int id,LocalDate localDate){
        List<CarMaintenance> result=this.carMaintenanceDao.getAllByCar_CarId(id);
        int flag=0;
        for (CarMaintenance carMaintenance : result) {
            if((carMaintenance.getReturnDate()==null)||
                    localDate.now().isBefore(carMaintenance.getReturnDate()) ||
                        localDate.now().isEqual(carMaintenance.getReturnDate())) {
                flag++;
            }
        }
        if(flag!=0)
            return true;
        else
            return false;
    }

    @Override
    public boolean checkIfCarIsInMaintenance(int id, LocalDate startDate, LocalDate endDate) {
        List<CarMaintenance> result=this.carMaintenanceDao.getAllByCar_CarId(id);
        int flag=0;
        for (CarMaintenance carMaintenance : result) {
            if((carMaintenance.getReturnDate()==null)||
                    startDate.now().isAfter(carMaintenance.getReturnDate()) &&
                            endDate.now().isBefore(carMaintenance.getReturnDate())||
                    startDate.now().isEqual(carMaintenance.getReturnDate()) &&
                            endDate.now().isEqual(carMaintenance.getReturnDate())) {
                flag++;
            }
        }
        if(flag!=0)
            return true;
        else
            return false;
    }
}
