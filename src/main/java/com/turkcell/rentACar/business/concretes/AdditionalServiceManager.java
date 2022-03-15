package com.turkcell.rentACar.business.concretes;

import com.turkcell.rentACar.business.abstracts.AdditionalServiceService;
import com.turkcell.rentACar.business.dtos.additionalServiceDtos.AdditionalServiceDto;
import com.turkcell.rentACar.business.dtos.additionalServiceDtos.AdditionalServiceListDto;
import com.turkcell.rentACar.business.requests.creates.CreateAdditionalServiceRequest;
import com.turkcell.rentACar.business.requests.deletes.DeleteAdditionalServiceRequest;
import com.turkcell.rentACar.business.requests.updates.UpdateAdditionalServiceRequest;
import com.turkcell.rentACar.core.utilities.exceptions.AlreadyExistsException;
import com.turkcell.rentACar.core.utilities.exceptions.NotFoundException;
import com.turkcell.rentACar.core.utilities.exceptions.UpdateHasNoChangesException;
import com.turkcell.rentACar.core.utilities.mapping.ModelMapperService;
import com.turkcell.rentACar.core.utilities.results.DataResult;
import com.turkcell.rentACar.core.utilities.results.Result;
import com.turkcell.rentACar.core.utilities.results.SuccessDataResult;
import com.turkcell.rentACar.core.utilities.results.SuccessResult;
import com.turkcell.rentACar.dataAccess.abstracts.AdditionalServiceDao;
import com.turkcell.rentACar.entities.concretes.AdditionalService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class AdditionalServiceManager implements AdditionalServiceService {
    private AdditionalServiceDao additionalServiceDao;
    private ModelMapperService modelMapperService;
    @Autowired
    public AdditionalServiceManager(AdditionalServiceDao additionalServiceDao, ModelMapperService modelMapperService) {
        this.additionalServiceDao = additionalServiceDao;
        this.modelMapperService = modelMapperService;
    }

    @Override
    public DataResult<List<AdditionalServiceListDto>> getAll() {
        List<AdditionalService> result = this.additionalServiceDao.findAll();

        List<AdditionalServiceListDto> response = result.stream().map(additionalService ->
                this.modelMapperService.forDto().map(additionalService, AdditionalServiceListDto.class)).collect(Collectors.toList());

        return new SuccessDataResult<List<AdditionalServiceListDto>>(response, "Additional Services Listed Successfully");
    }

    @Override
    public Result add(CreateAdditionalServiceRequest createAdditionalServiceRequest) throws AlreadyExistsException {

        checkIfAdditionalServiceExistsByName(createAdditionalServiceRequest.getServiceName());

        AdditionalService additionalService = this.modelMapperService.forRequest().map(createAdditionalServiceRequest, AdditionalService.class);

        this.additionalServiceDao.save(additionalService);

        return new SuccessResult("Additional Service Added Successfully");
    }

    @Override
    public DataResult<AdditionalServiceDto> getById(int id) throws NotFoundException {

        checkIfAdditionalServiceExistsById(id);

        AdditionalService additionalService = this.additionalServiceDao.getById(id);

        AdditionalServiceDto additionalServiceDto = this.modelMapperService.forDto().map(additionalService, AdditionalServiceDto.class);

        return new SuccessDataResult<AdditionalServiceDto>(additionalServiceDto, "Additional Service Listed Successfully");
    }

    @Override
    public Result update(UpdateAdditionalServiceRequest updateAdditionalServiceRequest) throws NotFoundException,UpdateHasNoChangesException {

        checkIfAdditionalServiceExistsById(updateAdditionalServiceRequest.getServiceId());
        checkIfAdditionalServiceUpdateHasNoChanges(updateAdditionalServiceRequest.getServiceName(), updateAdditionalServiceRequest.getDailyPrice());

        AdditionalService additionalService = this.modelMapperService.forRequest().map(updateAdditionalServiceRequest, AdditionalService.class);

        this.additionalServiceDao.save(additionalService);

        return new SuccessResult("Additional Service Updated Successfully");
    }

    @Override
    public Result delete(DeleteAdditionalServiceRequest deleteAdditionalServiceRequest) throws NotFoundException {

        checkIfAdditionalServiceExistsById(deleteAdditionalServiceRequest.getServiceId());

        AdditionalService additionalService = this.modelMapperService.forRequest().map(deleteAdditionalServiceRequest, AdditionalService.class);

        this.additionalServiceDao.delete(additionalService);

        return new SuccessResult("Additional Service Deleted Successfully");
    }

    @Override
    public AdditionalService getServiceByServiceId(int id) {
        return this.additionalServiceDao.getById(id);
    }

    public void checkIfAdditionalServiceExistsByName(String serviceName) throws AlreadyExistsException {
        if(this.additionalServiceDao.existsByServiceName(serviceName))
            throw new AlreadyExistsException("This Additional Service Already Exists");
    }

    public void checkIfAdditionalServiceExistsById(int serviceId) throws NotFoundException {
        if(!this.additionalServiceDao.existsByServiceId(serviceId))
            throw new NotFoundException("Additional Service Not Found");
    }

    public void checkIfAdditionalServiceUpdateHasNoChanges(String serviceName, double dailyPrice) throws UpdateHasNoChangesException{
        if(this.additionalServiceDao.existsByServiceNameAndDailyPrice(serviceName,dailyPrice))
            throw new UpdateHasNoChangesException("There is No Changes On Additional Service Update");
    }
}
