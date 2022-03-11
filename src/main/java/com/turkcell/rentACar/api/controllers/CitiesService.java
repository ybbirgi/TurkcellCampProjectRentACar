package com.turkcell.rentACar.api.controllers;

import com.turkcell.rentACar.business.abstracts.CityService;
import com.turkcell.rentACar.business.dtos.cityDtos.CityDto;
import com.turkcell.rentACar.business.dtos.cityDtos.CityListDto;
import com.turkcell.rentACar.core.utilities.exceptions.BusinessException;
import com.turkcell.rentACar.core.utilities.results.DataResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/cities")
public class CitiesService {
    private CityService cityService;

    public CitiesService(CityService cityService) {
        this.cityService = cityService;
    }
    @GetMapping("/getAll")
    DataResult<List<CityListDto>> getAll(){return this.cityService.getAll();}
    @GetMapping("/getById")
    DataResult<CityDto> getById(@RequestParam int id) throws BusinessException{return this.cityService.getById(id);}
}
