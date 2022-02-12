package com.turkcell.rentACar.api.controllers;

import com.turkcell.rentACar.business.abstracts.BrandService;
import com.turkcell.rentACar.business.dtos.BrandListDto;
import com.turkcell.rentACar.business.requests.CreateBrandRequest;
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
    public List<BrandListDto> getAll(){
        return this.brandService.getAll();
    }
    @GetMapping("/getById")
    public BrandListDto getById(@RequestParam int id){return this.brandService.getById(id);}

    @PostMapping("/add")
    public void add(@RequestBody CreateBrandRequest createBrandRequest) throws Exception {
        this.brandService.add(createBrandRequest);
    }
}
