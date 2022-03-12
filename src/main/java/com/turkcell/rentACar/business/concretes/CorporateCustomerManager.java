package com.turkcell.rentACar.business.concretes;

import com.turkcell.rentACar.business.abstracts.CorporateCustomerService;
import com.turkcell.rentACar.business.dtos.corporateCustomerDtos.CorporateCustomerDto;
import com.turkcell.rentACar.business.dtos.corporateCustomerDtos.CorporateCustomerListDto;
import com.turkcell.rentACar.business.requests.creates.CreateCorporateCustomerRequest;
import com.turkcell.rentACar.business.requests.deletes.DeleteCorporateCustomerRequest;
import com.turkcell.rentACar.business.requests.updates.UpdateCorporateCustomerRequest;
import com.turkcell.rentACar.core.utilities.exceptions.BusinessException;
import com.turkcell.rentACar.core.utilities.mapping.ModelMapperService;
import com.turkcell.rentACar.core.utilities.results.DataResult;
import com.turkcell.rentACar.core.utilities.results.Result;
import com.turkcell.rentACar.core.utilities.results.SuccessDataResult;
import com.turkcell.rentACar.core.utilities.results.SuccessResult;
import com.turkcell.rentACar.dataAccess.abstracts.CorporateCustomerDao;
import com.turkcell.rentACar.entities.concretes.CorporateCustomer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;
@Service
public class CorporateCustomerManager implements CorporateCustomerService {
    CorporateCustomerDao corporateCustomerDao;
    ModelMapperService modelMapperService;

    @Autowired
    public CorporateCustomerManager(CorporateCustomerDao corporateCustomerDao, ModelMapperService modelMapperService) {
        this.corporateCustomerDao = corporateCustomerDao;
        this.modelMapperService = modelMapperService;
    }

    @Override
    public DataResult<List<CorporateCustomerListDto>> getAll() {
        List<CorporateCustomer> result=this.corporateCustomerDao.findAll();
        List<CorporateCustomerListDto> response = result.stream().map(corporateCustomer->this.modelMapperService.forDto().map(corporateCustomer,CorporateCustomerListDto.class)).collect(Collectors.toList());
        return new SuccessDataResult<List<CorporateCustomerListDto>>(response,"Corporate Customers Listed Successfully");
    }

    @Override
    public Result add(CreateCorporateCustomerRequest createCorporateCustomerRequest) throws BusinessException {
        CorporateCustomer corporateCustomer = this.modelMapperService.forRequest().map(createCorporateCustomerRequest,CorporateCustomer.class);
        this.corporateCustomerDao.save(corporateCustomer);
        return new SuccessResult("Corporate Customer Added Successfully");
    }

    @Override
    public DataResult<CorporateCustomerDto> getById(int id) throws BusinessException {
        CorporateCustomer corporateCustomer = corporateCustomerDao.getById(id);
        CorporateCustomerDto corporateCustomerDto = this.modelMapperService.forDto().map(corporateCustomer,CorporateCustomerDto.class);
        return new SuccessDataResult<CorporateCustomerDto>(corporateCustomerDto,"Corporate Customer Listed Successfully");
    }

    @Override
    public Result update(UpdateCorporateCustomerRequest updateCorporateCustomerRequest) throws BusinessException {
        CorporateCustomer corporateCustomer = corporateCustomerDao.getById(updateCorporateCustomerRequest.getUserId());
        corporateCustomer = this.modelMapperService.forRequest().map(updateCorporateCustomerRequest,CorporateCustomer.class);
        this.corporateCustomerDao.save(corporateCustomer);
        return new SuccessResult("Corporate Customer Updated Successfully");
    }

    @Override
    public Result delete(DeleteCorporateCustomerRequest deleteCorporateCustomerRequest) throws BusinessException {
        CorporateCustomer corporateCustomer = corporateCustomerDao.getById(deleteCorporateCustomerRequest.getUserId());
        corporateCustomer = this.modelMapperService.forRequest().map(deleteCorporateCustomerRequest,CorporateCustomer.class);
        this.corporateCustomerDao.delete(corporateCustomer);
        return new SuccessResult("Corporate Customer Deleted Successfully");
    }
}
