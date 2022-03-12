package com.turkcell.rentACar.api.controllers;

import com.turkcell.rentACar.business.abstracts.IndividualCustomerService;
import com.turkcell.rentACar.business.dtos.individualCustomerDtos.IndividualCustomerDto;
import com.turkcell.rentACar.business.dtos.individualCustomerDtos.IndividualCustomerListDto;
import com.turkcell.rentACar.business.requests.creates.CreateIndividualCustomerRequest;
import com.turkcell.rentACar.business.requests.deletes.DeleteIndividualCustomerRequest;
import com.turkcell.rentACar.business.requests.updates.UpdateIndividualCustomerRequest;
import com.turkcell.rentACar.core.utilities.exceptions.BusinessException;
import com.turkcell.rentACar.core.utilities.results.DataResult;
import com.turkcell.rentACar.core.utilities.results.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/individualCustomers")
public class IndividualCustomersController {
    IndividualCustomerService individualCustomerService;
    @Autowired
    public IndividualCustomersController(IndividualCustomerService individualCustomerService) {
        this.individualCustomerService = individualCustomerService;
    }
    @GetMapping("/getAll")
    DataResult<List<IndividualCustomerListDto>> getAll(){return this.individualCustomerService.getAll();}
    @PostMapping("/add")
    Result add(@RequestBody CreateIndividualCustomerRequest createIndividualCustomerRequest) throws BusinessException{return this.individualCustomerService.add(createIndividualCustomerRequest);}
    @GetMapping("/getById")
    DataResult<IndividualCustomerDto> getById(@RequestParam int id) throws BusinessException{return this.individualCustomerService.getById(id);}
    @PutMapping("/update")
    Result update(@RequestBody UpdateIndividualCustomerRequest updateIndividualCustomerRequest) throws BusinessException{return this.individualCustomerService.update(updateIndividualCustomerRequest);}
    @DeleteMapping("/delete")
    Result delete(@RequestBody DeleteIndividualCustomerRequest deleteIndividualCustomerRequest) throws BusinessException{return this.individualCustomerService.delete(deleteIndividualCustomerRequest);}
}
