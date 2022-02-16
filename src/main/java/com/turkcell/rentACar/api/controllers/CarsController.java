package com.turkcell.rentACar.api.controllers;

import com.turkcell.rentACar.business.abstracts.BrandService;
import com.turkcell.rentACar.business.abstracts.CarService;
import com.turkcell.rentACar.business.dtos.BrandListDto;
import com.turkcell.rentACar.business.dtos.CarListDto;
import com.turkcell.rentACar.business.requests.CreateBrandRequest;
import com.turkcell.rentACar.business.requests.CreateCarRequest;
import com.turkcell.rentACar.business.requests.DeleteCarRequest;
import com.turkcell.rentACar.business.requests.UpdateCarRequest;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/cars")
public class CarsController {
    private CarService carService;

    public CarsController(CarService carService) {
        this.carService = carService;
    }

    @GetMapping("/getAll")
    public List<CarListDto> getAll(){
        return this.carService.getAll();
    }
    @GetMapping("/getById")
    public CarListDto getById(@RequestParam int id){return this.carService.getById(id);}

    @PostMapping("/add")
    public void add(@RequestBody CreateCarRequest createCarRequest) throws Exception {
        this.carService.add(createCarRequest);
    }

    @PostMapping("/update")
    public void update(@RequestBody UpdateCarRequest updateCarRequest){
        this.carService.update(updateCarRequest);
    }

    @PostMapping("/delete")
    public void delete(@RequestBody DeleteCarRequest deleteCarRequest){
        this.carService.delete(deleteCarRequest);
    }
}
