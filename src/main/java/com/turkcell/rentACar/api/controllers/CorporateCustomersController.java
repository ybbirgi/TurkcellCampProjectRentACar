package com.turkcell.rentACar.api.controllers;

import com.turkcell.rentACar.business.abstracts.CorporateCustomerService;
import com.turkcell.rentACar.business.dtos.corporateCustomerDtos.CorporateCustomerDto;
import com.turkcell.rentACar.business.dtos.corporateCustomerDtos.CorporateCustomerListDto;
import com.turkcell.rentACar.business.requests.creates.CreateCorporateCustomerRequest;
import com.turkcell.rentACar.business.requests.deletes.DeleteCorporateCustomerRequest;
import com.turkcell.rentACar.business.requests.updates.UpdateCorporateCustomerRequest;
import com.turkcell.rentACar.core.utilities.exceptions.BusinessException;
import com.turkcell.rentACar.core.utilities.exceptions.EmailAlreadyUsedException;
import com.turkcell.rentACar.core.utilities.exceptions.NotFoundException;
import com.turkcell.rentACar.core.utilities.exceptions.TaxNumberAlreadyUsedException;
import com.turkcell.rentACar.core.utilities.results.DataResult;
import com.turkcell.rentACar.core.utilities.results.Result;
import org.springframework.web.bind.annotation.*;

import java.util.List;
@RestController
@RequestMapping("/api/corporateCustomers")
public class CorporateCustomersController {
    private CorporateCustomerService corporateCustomerService;

    public CorporateCustomersController(CorporateCustomerService corporateCustomerService) {
        this.corporateCustomerService = corporateCustomerService;
    }
    @GetMapping("/getAll")
    DataResult<List<CorporateCustomerListDto>> getAll(){return this.corporateCustomerService.getAll();}
    @PostMapping("/add")
    Result add(@RequestBody CreateCorporateCustomerRequest createCorporateCustomerRequest) throws EmailAlreadyUsedException, TaxNumberAlreadyUsedException
    {return this.corporateCustomerService.add(createCorporateCustomerRequest);}
    @GetMapping("/getById")
    DataResult<CorporateCustomerDto> getById(@RequestParam int id) throws NotFoundException {return this.corporateCustomerService.getById(id);}
    @PutMapping("/update")
    Result update(@RequestBody UpdateCorporateCustomerRequest updateCorporateCustomerRequest)
            throws NotFoundException,EmailAlreadyUsedException,TaxNumberAlreadyUsedException{
        return this.corporateCustomerService.update(updateCorporateCustomerRequest);}
    @DeleteMapping("/delete")
    Result delete(@RequestBody DeleteCorporateCustomerRequest deleteCorporateCustomerRequest) throws NotFoundException{return this.corporateCustomerService.delete(deleteCorporateCustomerRequest);}
}
