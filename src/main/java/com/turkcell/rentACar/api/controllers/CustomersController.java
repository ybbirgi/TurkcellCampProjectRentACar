package com.turkcell.rentACar.api.controllers;

import com.turkcell.rentACar.business.abstracts.CustomerService;
import com.turkcell.rentACar.business.dtos.customerDtos.CustomerDto;
import com.turkcell.rentACar.business.dtos.customerDtos.CustomerListDto;
import com.turkcell.rentACar.business.requests.creates.CreateCustomerRequest;
import com.turkcell.rentACar.business.requests.deletes.DeleteCustomerRequest;
import com.turkcell.rentACar.business.requests.updates.UpdateCustomerRequest;
import com.turkcell.rentACar.core.utilities.exceptions.BusinessException;
import com.turkcell.rentACar.core.utilities.results.DataResult;
import com.turkcell.rentACar.core.utilities.results.Result;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/customers")
public class CustomersController {
    private CustomerService customerService;

    public CustomersController(CustomerService customerService) {
        this.customerService = customerService;
    }

    @GetMapping("/getAll")
    DataResult<List<CustomerListDto>> getAll(){return this.customerService.getAll();}
    @PostMapping("/add")
    Result add(@RequestBody CreateCustomerRequest createCustomerRequest) throws BusinessException{return this.customerService.add(createCustomerRequest);}
    @GetMapping("/getById")
    DataResult<CustomerDto> getById(@RequestParam int id) throws BusinessException{return this.customerService.getById(id);}
    @PutMapping("/update")
    Result update(@RequestBody UpdateCustomerRequest updateCustomerRequest) throws BusinessException{return this.customerService.update(updateCustomerRequest);}
    @DeleteMapping("/delete")
    Result delete(@RequestBody DeleteCustomerRequest deleteCustomerRequest) throws BusinessException{return this.customerService.delete(deleteCustomerRequest);}
}
