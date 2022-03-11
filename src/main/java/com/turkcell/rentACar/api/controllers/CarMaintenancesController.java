package com.turkcell.rentACar.api.controllers;

import com.turkcell.rentACar.business.abstracts.CarMaintenanceService;
import com.turkcell.rentACar.business.dtos.carMaintenanceDtos.CarMaintenanceListDto;
import com.turkcell.rentACar.business.requests.creates.CreateCarMaintenanceRequest;
import com.turkcell.rentACar.business.requests.deletes.DeleteCarMaintenanceRequest;
import com.turkcell.rentACar.business.requests.updates.UpdateCarMaintenanceRequest;
import com.turkcell.rentACar.core.utilities.exceptions.BusinessException;
import com.turkcell.rentACar.core.utilities.results.DataResult;
import com.turkcell.rentACar.core.utilities.results.Result;
import org.springframework.web.bind.annotation.*;

import java.util.List;
@RestController
@RequestMapping("/api/carMaintenances")
public class CarMaintenancesController {
    private CarMaintenanceService carMaintenanceService;

    public CarMaintenancesController(CarMaintenanceService carMaintenanceService) {
        this.carMaintenanceService = carMaintenanceService;
    }
    @GetMapping("/getAll")
    DataResult<List<CarMaintenanceListDto>> getAll(){return carMaintenanceService.getAll();}
    @PostMapping("/add")
    Result sendToMaintenance(@RequestBody CreateCarMaintenanceRequest createCarMaintenanceRequest)throws BusinessException {return carMaintenanceService.sendToMaintenance(createCarMaintenanceRequest);}
    @PutMapping("/update")
    Result update(@RequestBody UpdateCarMaintenanceRequest updateCarMaintenanceRequest){return carMaintenanceService.update(updateCarMaintenanceRequest);}
    @DeleteMapping("/delete")
    Result delete(@RequestBody DeleteCarMaintenanceRequest deleteCarMaintenanceRequest){return carMaintenanceService.delete(deleteCarMaintenanceRequest);}
    @GetMapping("/getByCarId")
    DataResult<List<CarMaintenanceListDto>> getByCarId(@RequestParam int id){return carMaintenanceService.getByCarId(id);}
}
