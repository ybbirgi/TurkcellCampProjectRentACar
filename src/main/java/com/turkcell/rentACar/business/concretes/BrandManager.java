package com.turkcell.rentACar.business.concretes;

import com.turkcell.rentACar.business.abstracts.BrandService;
import com.turkcell.rentACar.business.dtos.BrandListDto;
import com.turkcell.rentACar.business.requests.CreateBrandRequest;
import com.turkcell.rentACar.business.requests.DeleteBrandRequest;
import com.turkcell.rentACar.business.requests.UpdateBrandRequest;
import com.turkcell.rentACar.core.utilities.mapping.ModelMapperService;
import com.turkcell.rentACar.dataAccess.abstracts.BrandDao;
import com.turkcell.rentACar.entities.concretes.Brand;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class BrandManager implements BrandService {
    private BrandDao brandDao;
    private ModelMapperService modelMapperService;
    @Autowired
    public BrandManager(BrandDao brandDao,ModelMapperService modelMapperService) {
        this.brandDao = brandDao;
        this.modelMapperService = modelMapperService;
    }

    @Override
    public List<BrandListDto> getAll() {
        List<Brand> result=this.brandDao.findAll();
        List<BrandListDto> response = result.stream().map(brand->this.modelMapperService.forDto().map(brand,BrandListDto.class)).collect(Collectors.toList());
        return response;
    }

    @Override
    public void add(CreateBrandRequest createBrandRequest) throws Exception {
        Brand brand = this.modelMapperService.forRequest().map(createBrandRequest,Brand.class);
        checkIfSameBrand(brand);
        this.brandDao.save(brand);
    }

    @Override
    public BrandListDto getById(int id) {
        Brand brand = brandDao.getById(id);
        BrandListDto brandListDto = this.modelMapperService.forDto().map(brand,BrandListDto.class);
        return brandListDto;
    }

    @Override
    public void update(UpdateBrandRequest updateBrandRequest) {
        Brand brand = brandDao.getById(updateBrandRequest.getBrandId());
        brand = this.modelMapperService.forRequest().map(updateBrandRequest,Brand.class);
        this.brandDao.save(brand);
    }

    @Override
    public void delete(DeleteBrandRequest DeleteBrandRequest) {
        Brand brand = this.modelMapperService.forRequest().map(DeleteBrandRequest,Brand.class);
        this.brandDao.delete(brand);
    }


    void checkIfSameBrand(Brand brand) throws Exception {
        if(this.brandDao.getAllByBrandName(brand.getBrandName()).stream().count()!=0)
            throw new Exception("This brand already exists");

    }
}
