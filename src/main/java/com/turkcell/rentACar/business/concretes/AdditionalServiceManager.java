package com.turkcell.rentACar.business.concretes;

import com.turkcell.rentACar.business.abstracts.AdditionalServiceService;
import com.turkcell.rentACar.business.dtos.additionalServiceDtos.AdditionalServiceDto;
import com.turkcell.rentACar.business.dtos.additionalServiceDtos.AdditionalServiceListDto;
import com.turkcell.rentACar.business.requests.creates.CreateAdditionalServiceRequest;
import com.turkcell.rentACar.business.requests.deletes.DeleteAdditionalServiceRequest;
import com.turkcell.rentACar.business.requests.updates.UpdateAdditionalServiceRequest;
import com.turkcell.rentACar.core.utilities.exceptions.BusinessException;
import com.turkcell.rentACar.core.utilities.mapping.ModelMapperService;
import com.turkcell.rentACar.core.utilities.results.DataResult;
import com.turkcell.rentACar.core.utilities.results.Result;
import com.turkcell.rentACar.core.utilities.results.SuccessDataResult;
import com.turkcell.rentACar.core.utilities.results.SuccessResult;
import com.turkcell.rentACar.dataAccess.abstracts.AdditionalServiceDao;
import com.turkcell.rentACar.entities.concretes.AdditionalService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class AdditionalServiceManager implements AdditionalServiceService {
    private AdditionalServiceDao additionalServiceDao;
    private ModelMapperService modelMapperService;

    public AdditionalServiceManager(AdditionalServiceDao additionalServiceDao, ModelMapperService modelMapperService) {
        this.additionalServiceDao = additionalServiceDao;
        this.modelMapperService = modelMapperService;
    }

    @Override
    public DataResult<List<AdditionalServiceListDto>> getAll() {
        List<AdditionalService> result = this.additionalServiceDao.findAll();
        List<AdditionalServiceListDto> response = result.stream().map(additionalService -> this.modelMapperService.forDto().map(additionalService, AdditionalServiceListDto.class)).collect(Collectors.toList());
        return new SuccessDataResult<List<AdditionalServiceListDto>>(response, "Additional Services Listed Successfully");
    }

    @Override
    public Result add(CreateAdditionalServiceRequest createAdditionalServiceRequest) throws BusinessException {
        AdditionalService additionalService = this.modelMapperService.forRequest().map(createAdditionalServiceRequest, AdditionalService.class);
        this.additionalServiceDao.save(additionalService);
        return new SuccessResult("Additional Service Added Successfully");
    }

    @Override
    public DataResult<AdditionalServiceDto> getById(int id) throws BusinessException {
        AdditionalService additionalService = this.additionalServiceDao.getById(id);
        AdditionalServiceDto additionalServiceDto = this.modelMapperService.forDto().map(additionalService, AdditionalServiceDto.class);
        return new SuccessDataResult<AdditionalServiceDto>(additionalServiceDto, "Additional Service Listed Successfully");
    }

    @Override
    public Result update(UpdateAdditionalServiceRequest updateAdditionalServiceRequest) throws BusinessException {
        AdditionalService additionalService = this.modelMapperService.forRequest().map(updateAdditionalServiceRequest, AdditionalService.class);
        this.additionalServiceDao.save(additionalService);
        return new SuccessResult("Additional Service Updated Successfully");
    }

    @Override
    public Result delete(DeleteAdditionalServiceRequest deleteAdditionalServiceRequest) throws BusinessException {
        AdditionalService additionalService = this.modelMapperService.forRequest().map(deleteAdditionalServiceRequest, AdditionalService.class);
        this.additionalServiceDao.delete(additionalService);
        return new SuccessResult("Additional Service Deleted Successfully");
    }
}
