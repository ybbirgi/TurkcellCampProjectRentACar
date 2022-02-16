package com.turkcell.rentACar.business.abstracts;


import com.turkcell.rentACar.business.dtos.BrandListDto;
import com.turkcell.rentACar.business.requests.*;

import java.util.List;

public interface BrandService {
    List<BrandListDto> getAll();
    void add(CreateBrandRequest createBrandRequest) throws Exception;
    BrandListDto getById(int id);
    void update(UpdateBrandRequest updateBrandRequest);
    void delete(DeleteBrandRequest DeleteBrandRequest);
}
