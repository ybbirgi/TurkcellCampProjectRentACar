package com.turkcell.rentACar.api.controllers;

import com.turkcell.rentACar.business.abstracts.CarRentalService;
import com.turkcell.rentACar.business.dtos.CarRentalListDto;
import com.turkcell.rentACar.business.requests.creates.CreateCarRentalRequest;
import com.turkcell.rentACar.business.requests.deletes.DeleteCarRentalRequest;
import com.turkcell.rentACar.business.requests.updates.UpdateCarRentalRequest;
import com.turkcell.rentACar.core.utilities.results.DataResult;
import com.turkcell.rentACar.core.utilities.results.Result;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/carRentals")
public class CarRentalsController {

    private CarRentalService carRentalService;

    public CarRentalsController(CarRentalService carRentalService) {
        this.carRentalService = carRentalService;
    }

    @GetMapping("/getAll")
    DataResult<List<CarRentalListDto>> getAll(){return carRentalService.getAll();}
    @PostMapping("/add")
    Result add(@RequestBody CreateCarRentalRequest createCarRentalRequest){return carRentalService.add(createCarRentalRequest);}
    @PutMapping("/update")
    Result update(@RequestBody UpdateCarRentalRequest updateCarRentalRequest){return carRentalService.update(updateCarRentalRequest);}
    @DeleteMapping("/delete")
    Result delete(@RequestBody DeleteCarRentalRequest deleteCarRentalRequest){return carRentalService.delete(deleteCarRentalRequest);}
    @GetMapping("/getByCarId")
    DataResult<List<CarRentalListDto>> getByCarId(@RequestParam int id){return carRentalService.getByCarId(id);}
}
