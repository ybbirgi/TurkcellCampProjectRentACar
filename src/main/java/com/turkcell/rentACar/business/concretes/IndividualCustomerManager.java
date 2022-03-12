package com.turkcell.rentACar.business.concretes;

import com.turkcell.rentACar.business.abstracts.IndividualCustomerService;
import com.turkcell.rentACar.business.dtos.corporateCustomerDtos.CorporateCustomerDto;
import com.turkcell.rentACar.business.dtos.corporateCustomerDtos.CorporateCustomerListDto;
import com.turkcell.rentACar.business.dtos.individualCustomerDtos.IndividualCustomerDto;
import com.turkcell.rentACar.business.dtos.individualCustomerDtos.IndividualCustomerListDto;
import com.turkcell.rentACar.business.requests.creates.CreateIndividualCustomerRequest;
import com.turkcell.rentACar.business.requests.deletes.DeleteIndividualCustomerRequest;
import com.turkcell.rentACar.business.requests.updates.UpdateIndividualCustomerRequest;
import com.turkcell.rentACar.core.utilities.exceptions.BusinessException;
import com.turkcell.rentACar.core.utilities.mapping.ModelMapperService;
import com.turkcell.rentACar.core.utilities.results.DataResult;
import com.turkcell.rentACar.core.utilities.results.Result;
import com.turkcell.rentACar.core.utilities.results.SuccessDataResult;
import com.turkcell.rentACar.core.utilities.results.SuccessResult;
import com.turkcell.rentACar.dataAccess.abstracts.IndividualCustomerDao;
import com.turkcell.rentACar.entities.concretes.CorporateCustomer;
import com.turkcell.rentACar.entities.concretes.IndividualCustomer;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class IndividualCustomerManager implements IndividualCustomerService {
    IndividualCustomerDao individualCustomerDao;
    ModelMapperService modelMapperService;

    public IndividualCustomerManager(IndividualCustomerDao individualCustomerDao, ModelMapperService modelMapperService) {
        this.individualCustomerDao = individualCustomerDao;
        this.modelMapperService = modelMapperService;
    }

    @Override
    public DataResult<List<IndividualCustomerListDto>> getAll() {
        List<IndividualCustomer> result=this.individualCustomerDao.findAll();
        List<IndividualCustomerListDto> response = result.stream().map(individualCustomer->this.modelMapperService.forDto().map(individualCustomer,IndividualCustomerListDto.class)).collect(Collectors.toList());
        return new SuccessDataResult<List<IndividualCustomerListDto>>(response,"Individual Customers Listed Successfully");
    }

    @Override
    public Result add(CreateIndividualCustomerRequest createIndividualCustomerRequest) throws BusinessException {
        IndividualCustomer individualCustomer = this.modelMapperService.forRequest().map(createIndividualCustomerRequest,IndividualCustomer.class);
        this.individualCustomerDao.save(individualCustomer);
        return new SuccessResult("Individual Customer Added Successfully");
    }

    @Override
    public DataResult<IndividualCustomerDto> getById(int id) throws BusinessException {
        IndividualCustomer individualCustomer = individualCustomerDao.getById(id);
        IndividualCustomerDto individualCustomerDto = this.modelMapperService.forDto().map(individualCustomer,IndividualCustomerDto.class);
        return new SuccessDataResult<IndividualCustomerDto>(individualCustomerDto,"Individual Customer Listed Successfully");
    }

    @Override
    public Result update(UpdateIndividualCustomerRequest updateIndividualCustomerRequest) throws BusinessException {
        IndividualCustomer individualCustomer = individualCustomerDao.getById(updateIndividualCustomerRequest.getUserId());
        individualCustomer = this.modelMapperService.forRequest().map(updateIndividualCustomerRequest,IndividualCustomer.class);
        this.individualCustomerDao.save(individualCustomer);
        return new SuccessResult("Individual Customer Updated Successfully");
    }

    @Override
    public Result delete(DeleteIndividualCustomerRequest deleteIndividualCustomerRequest) throws BusinessException {
        IndividualCustomer individualCustomer = individualCustomerDao.getById(deleteIndividualCustomerRequest.getUserId());
        individualCustomer = this.modelMapperService.forRequest().map(deleteIndividualCustomerRequest,IndividualCustomer.class);
        this.individualCustomerDao.delete(individualCustomer);
        return new SuccessResult("Individual Customer Updated Successfully");
    }
}
