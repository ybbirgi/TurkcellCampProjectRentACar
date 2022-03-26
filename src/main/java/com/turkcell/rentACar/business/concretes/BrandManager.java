package com.turkcell.rentACar.business.concretes;

import com.turkcell.rentACar.business.abstracts.BrandService;
import com.turkcell.rentACar.business.constants.messages.BusinessMessages;
import com.turkcell.rentACar.business.dtos.brandDtos.BrandDto;
import com.turkcell.rentACar.business.dtos.brandDtos.BrandListDto;
import com.turkcell.rentACar.business.requests.creates.CreateBrandRequest;
import com.turkcell.rentACar.business.requests.deletes.DeleteBrandRequest;
import com.turkcell.rentACar.business.requests.updates.UpdateBrandRequest;
import com.turkcell.rentACar.core.utilities.exceptions.AlreadyExistsException;
import com.turkcell.rentACar.core.utilities.exceptions.BusinessException;
import com.turkcell.rentACar.core.utilities.exceptions.NotFoundException;
import com.turkcell.rentACar.core.utilities.exceptions.UpdateHasNoChangesException;
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

        return new SuccessDataResult<List<BrandListDto>>(response, BusinessMessages.GlobalMessages.DATA_LISTED_SUCCESSFULLY);
    }

    @Override
    public Result add(CreateBrandRequest createBrandRequest) throws AlreadyExistsException {
        checkIfBrandExistsByName(createBrandRequest.getBrandName());

        Brand brand = this.modelMapperService.forRequest().map(createBrandRequest,Brand.class);

        this.brandDao.save(brand);

        return new SuccessResult(BusinessMessages.GlobalMessages.DATA_ADDED_SUCCESSFULLY);
    }

    @Override
    public DataResult<BrandDto> getById(int id) throws NotFoundException {
        checkIfBrandExistsById(id);

        Brand brand = this.brandDao.getById(id);

        BrandDto brandDto = this.modelMapperService.forDto().map(brand,BrandDto.class);

        return new SuccessDataResult<BrandDto>(brandDto,BusinessMessages.GlobalMessages.DATA_LISTED_SUCCESSFULLY);
    }

    @Override
    public Result update(UpdateBrandRequest updateBrandRequest) throws NotFoundException, UpdateHasNoChangesException {

        checkIfBrandExistsById(updateBrandRequest.getBrandId());
        checkIfBrandUpdateHasNoChanges(updateBrandRequest.getBrandName());

        Brand brand = this.modelMapperService.forRequest().map(updateBrandRequest,Brand.class);

        this.brandDao.save(brand);

        return new SuccessResult(BusinessMessages.GlobalMessages.DATA_UPDATED_SUCCESSFULLY);
    }

    @Override
    public Result delete(DeleteBrandRequest deleteBrandRequest) throws NotFoundException{

        checkIfBrandExistsById(deleteBrandRequest.getBrandId());

        Brand brand = this.modelMapperService.forRequest().map(deleteBrandRequest,Brand.class);

        this.brandDao.delete(brand);

        return new SuccessResult(BusinessMessages.GlobalMessages.DATA_DELETED_SUCCESSFULLY);
    }

    void checkIfBrandExistsByName(String name) throws AlreadyExistsException {
        if(this.brandDao.existsByBrandName(name))
            throw new AlreadyExistsException(BusinessMessages.BrandMessages.BRAND_ALREADY_EXISTS);
    }

    void checkIfBrandExistsById(int id) throws NotFoundException {
        if(!this.brandDao.existsById(id))
            throw new NotFoundException(BusinessMessages.BrandMessages.BRAND_NOT_FOUND);
    }

    void checkIfBrandUpdateHasNoChanges(String name) throws UpdateHasNoChangesException{
        if(this.brandDao.existsByBrandName(name))
            throw new UpdateHasNoChangesException(BusinessMessages.BrandMessages.BRAND_HAS_NO_CHANGES);
    }



}
