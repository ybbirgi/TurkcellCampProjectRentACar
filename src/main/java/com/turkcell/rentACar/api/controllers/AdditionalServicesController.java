package com.turkcell.rentACar.api.controllers;

import com.turkcell.rentACar.business.abstracts.AdditionalServiceService;
import com.turkcell.rentACar.business.dtos.additionalServiceDtos.AdditionalServiceDto;
import com.turkcell.rentACar.business.dtos.additionalServiceDtos.AdditionalServiceListDto;
import com.turkcell.rentACar.business.requests.creates.CreateAdditionalServiceRequest;
import com.turkcell.rentACar.business.requests.deletes.DeleteAdditionalServiceRequest;
import com.turkcell.rentACar.business.requests.updates.UpdateAdditionalServiceRequest;
import com.turkcell.rentACar.core.utilities.exceptions.BusinessException;
import com.turkcell.rentACar.core.utilities.results.DataResult;
import com.turkcell.rentACar.core.utilities.results.Result;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/additionalServices")
public class AdditionalServicesController {
    private AdditionalServiceService additionalServiceService;

    public AdditionalServicesController(AdditionalServiceService additionalServiceService) {this.additionalServiceService = additionalServiceService;}

    @GetMapping("/getAll")
    DataResult<List<AdditionalServiceListDto>> getAll(){return this.additionalServiceService.getAll();}
    @PostMapping("/add")
    Result add(@RequestBody CreateAdditionalServiceRequest createAdditionalServiceRequest) throws BusinessException {return this.additionalServiceService.add(createAdditionalServiceRequest);}
    @GetMapping("/getById")
    DataResult<AdditionalServiceDto> getById(@RequestParam int id) throws BusinessException{return this.additionalServiceService.getById(id);}
    @PutMapping("/update")
    Result update(@RequestBody UpdateAdditionalServiceRequest updateAdditionalServiceRequest) throws BusinessException{return this.additionalServiceService.update(updateAdditionalServiceRequest);}
    @DeleteMapping("/delete")
    Result delete(@RequestBody DeleteAdditionalServiceRequest deleteAdditionalServiceRequest) throws BusinessException{return this.additionalServiceService.delete(deleteAdditionalServiceRequest);}
}
