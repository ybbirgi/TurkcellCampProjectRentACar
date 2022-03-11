package com.turkcell.rentACar.business.concretes;

import com.turkcell.rentACar.business.abstracts.BrandService;
import com.turkcell.rentACar.business.dtos.brandDtos.BrandDto;
import com.turkcell.rentACar.business.dtos.brandDtos.BrandListDto;
import com.turkcell.rentACar.business.requests.creates.CreateBrandRequest;
import com.turkcell.rentACar.business.requests.deletes.DeleteBrandRequest;
import com.turkcell.rentACar.business.requests.updates.UpdateBrandRequest;
import com.turkcell.rentACar.core.utilities.exceptions.BusinessException;
import com.turkcell.rentACar.core.utilities.mapping.ModelMapperService;
import com.turkcell.rentACar.core.utilities.results.*;
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
    public DataResult<List<BrandListDto>> getAll() {
        List<Brand> result=this.brandDao.findAll();
        List<BrandListDto> response = result.stream().map(brand->this.modelMapperService.forDto().map(brand,BrandListDto.class)).collect(Collectors.toList());
        return new SuccessDataResult<List<BrandListDto>>(response,"Brands Listed Successfully");
    }

    @Override
    public Result add(CreateBrandRequest createBrandRequest) throws BusinessException {
        Brand brand = this.modelMapperService.forRequest().map(createBrandRequest,Brand.class);
        checkIfSameBrand(brand.getBrandName());
        this.brandDao.save(brand);
        return new SuccessResult("Brand Added Successfully");
    }

    @Override
    public DataResult<BrandDto> getById(int id) throws BusinessException{
        checkIfBrandExists(id);

        Brand brand = this.brandDao.getById(id);
        BrandDto brandDto = this.modelMapperService.forDto().map(brand,BrandDto.class);
        return new SuccessDataResult<BrandDto>(brandDto,"Brand Listed Successfully");
    }

    @Override
    public Result update(UpdateBrandRequest updateBrandRequest) throws BusinessException {
        checkIfBrandExists(updateBrandRequest.getBrandId());

        Brand brand = this.modelMapperService.forRequest().map(updateBrandRequest,Brand.class);
        checkIfSameBrand(brand.getBrandName());
        this.brandDao.save(brand);
        return new SuccessResult("Brand Updated Successfully");
    }

    @Override
    public Result delete(DeleteBrandRequest deleteBrandRequest) throws BusinessException{
        checkIfBrandExists(deleteBrandRequest.getBrandId());
        Brand brand = this.modelMapperService.forRequest().map(deleteBrandRequest,Brand.class);
        this.brandDao.delete(brand);
        return new SuccessResult("Brand Deleted Successfully");
    }
    void checkIfBrandExists(int id) throws BusinessException {
        if(!this.brandDao.existsById(id))
            throw new BusinessException("There is not any Brand with This Id");
    }

    void checkIfSameBrand(String name) throws BusinessException {
        if(this.brandDao.existsByBrandName(name))
            throw new BusinessException("This brand already exists");
    }

}
