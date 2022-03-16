package com.turkcell.rentACar.api.controllers;

import com.turkcell.rentACar.business.abstracts.CarDamageService;
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
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/carDamages")
public class CarDamagesController {

    private CarDamageService carDamageService;

    public CarDamagesController(CarDamageService carDamageService) {
        this.carDamageService = carDamageService;
    }

    @GetMapping("/getAll")
    DataResult<List<CarDamageListDto>> getAll(){return this.carDamageService.getAll();}
    @PostMapping("/add")
    Result add(CreateCarDamageRequest createCarDamageRequest) throws AlreadyExistsException{return this.carDamageService.add(createCarDamageRequest);}
    @GetMapping("/getById")
    DataResult<CarDamageDto> getById(int id) throws NotFoundException{return this.carDamageService.getById(id);}
    @PutMapping("/update")
    Result update(UpdateCarDamageRequest updateCarDamageRequest) throws NotFoundException, UpdateHasNoChangesException{return this.carDamageService.update(updateCarDamageRequest);}
    @DeleteMapping("/delete")
    Result delete(DeleteCarDamageRequest deleteCarDamageRequest) throws NotFoundException{return this.carDamageService.delete(deleteCarDamageRequest);}

}
