package com.turkcell.rentACar.api.controllers;

import com.turkcell.rentACar.business.abstracts.BrandService;
import com.turkcell.rentACar.business.dtos.brandDtos.BrandDto;
import com.turkcell.rentACar.business.dtos.brandDtos.BrandListDto;
import com.turkcell.rentACar.business.requests.creates.CreateBrandRequest;
import com.turkcell.rentACar.business.requests.deletes.DeleteBrandRequest;
import com.turkcell.rentACar.business.requests.updates.UpdateBrandRequest;
import com.turkcell.rentACar.core.utilities.exceptions.AlreadyExistsException;
import com.turkcell.rentACar.core.utilities.exceptions.NotFoundException;
import com.turkcell.rentACar.core.utilities.exceptions.UpdateHasNoChangesException;
import com.turkcell.rentACar.core.utilities.results.DataResult;
import com.turkcell.rentACar.core.utilities.results.Result;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/brands")
public class BrandsController {
    private BrandService brandService;

    public BrandsController(BrandService brandService) {
        this.brandService = brandService;
    }

    @GetMapping("/getAll")
    public DataResult<List<BrandListDto>> getAll(){
        return this.brandService.getAll();
    }

    @GetMapping("/getById")
    public DataResult<BrandDto> getById(@RequestParam int id) throws NotFoundException {return this.brandService.getById(id);}

    @PostMapping("/add")
    public Result add(@RequestBody CreateBrandRequest createBrandRequest) throws AlreadyExistsException {
        return this.brandService.add(createBrandRequest);
    }
    @PutMapping("/update")
    public Result update(@RequestBody UpdateBrandRequest updateBrandRequest) throws NotFoundException, UpdateHasNoChangesException {
        return this.brandService.update(updateBrandRequest);
    }

    @DeleteMapping("/delete")
    public Result delete(@RequestBody DeleteBrandRequest deleteBrandRequest) throws NotFoundException{
        return this.brandService.delete(deleteBrandRequest);
    }

}
