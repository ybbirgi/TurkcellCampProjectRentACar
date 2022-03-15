package com.turkcell.rentACar.business.concretes;
import com.turkcell.rentACar.business.abstracts.CorporateCustomerService;
import com.turkcell.rentACar.business.abstracts.CustomerService;
import com.turkcell.rentACar.business.dtos.corporateCustomerDtos.CorporateCustomerDto;
import com.turkcell.rentACar.business.dtos.corporateCustomerDtos.CorporateCustomerListDto;
import com.turkcell.rentACar.business.requests.creates.CreateCorporateCustomerRequest;
import com.turkcell.rentACar.business.requests.deletes.DeleteCorporateCustomerRequest;
import com.turkcell.rentACar.business.requests.updates.UpdateCorporateCustomerRequest;
import com.turkcell.rentACar.core.utilities.exceptions.BusinessException;
import com.turkcell.rentACar.core.utilities.exceptions.EmailAlreadyUsedException;
import com.turkcell.rentACar.core.utilities.exceptions.NotFoundException;
import com.turkcell.rentACar.core.utilities.exceptions.TaxNumberAlreadyUsedException;
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
    CustomerService customerService;

    @Autowired
    public CorporateCustomerManager(CorporateCustomerDao corporateCustomerDao,
                                    ModelMapperService modelMapperService,
                                    CustomerService customerService) {
        this.corporateCustomerDao = corporateCustomerDao;
        this.modelMapperService = modelMapperService;
        this.customerService = customerService;
    }

    @Override
    public DataResult<List<CorporateCustomerListDto>> getAll() {
        List<CorporateCustomer> result=this.corporateCustomerDao.findAll();

        List<CorporateCustomerListDto> response = result.stream().map(corporateCustomer->
                this.modelMapperService.forDto().map(corporateCustomer,CorporateCustomerListDto.class)).collect(Collectors.toList());

        return new SuccessDataResult<List<CorporateCustomerListDto>>(response,"Corporate Customers Listed Successfully");
    }

    @Override
    public Result add(CreateCorporateCustomerRequest createCorporateCustomerRequest) throws EmailAlreadyUsedException,TaxNumberAlreadyUsedException {
        checkUniqueFieldsBeforeOperation(createCorporateCustomerRequest.getEmail(), createCorporateCustomerRequest.getTaxNumber());

        CorporateCustomer corporateCustomer = this.modelMapperService.forRequest().map(createCorporateCustomerRequest,CorporateCustomer.class);

        this.corporateCustomerDao.save(corporateCustomer);

        return new SuccessResult("Corporate Customer Added Successfully");
    }

    @Override
    public DataResult<CorporateCustomerDto> getById(int id) throws NotFoundException {
        this.customerService.checkIfCustomerExistsById(id);

        CorporateCustomer corporateCustomer = corporateCustomerDao.getById(id);

        CorporateCustomerDto corporateCustomerDto = this.modelMapperService.forDto().map(corporateCustomer,CorporateCustomerDto.class);

        return new SuccessDataResult<CorporateCustomerDto>(corporateCustomerDto,"Corporate Customer Listed Successfully");
    }

    @Override
    public Result update(UpdateCorporateCustomerRequest updateCorporateCustomerRequest) throws NotFoundException , EmailAlreadyUsedException,TaxNumberAlreadyUsedException{
        this.customerService.checkIfCustomerExistsById(updateCorporateCustomerRequest.getCustomerId());
        if(checkIfEmailChanged(updateCorporateCustomerRequest) || checkIfTaxNumberChanged(updateCorporateCustomerRequest))
            checkUniqueFieldsBeforeOperation(updateCorporateCustomerRequest.getEmail(), updateCorporateCustomerRequest.getTaxNumber());

        CorporateCustomer corporateCustomer =  this.modelMapperService.forRequest().map(updateCorporateCustomerRequest,CorporateCustomer.class);

        this.corporateCustomerDao.save(corporateCustomer);

        return new SuccessResult("Corporate Customer Updated Successfully");
    }

    @Override
    public Result delete(DeleteCorporateCustomerRequest deleteCorporateCustomerRequest) throws NotFoundException {
        this.customerService.checkIfCustomerExistsById(deleteCorporateCustomerRequest.getCustomerId());

        CorporateCustomer corporateCustomer =  this.modelMapperService.forRequest().map(deleteCorporateCustomerRequest,CorporateCustomer.class);

        this.corporateCustomerDao.delete(corporateCustomer);

        return new SuccessResult("Corporate Customer Deleted Successfully");
    }

    public void checkUniqueFieldsBeforeOperation(String email,String taxNumber) throws EmailAlreadyUsedException,TaxNumberAlreadyUsedException {
        this.customerService.checkIfCustomerExistsByEmail(email);
        if(this.corporateCustomerDao.existsByTaxNumber(taxNumber))
            throw new TaxNumberAlreadyUsedException("Tax Number Is Already In Use");
    }
    public boolean checkIfEmailChanged(UpdateCorporateCustomerRequest updateCorporateCustomerRequest){
        if(updateCorporateCustomerRequest.getEmail().equals(this.corporateCustomerDao.getById(updateCorporateCustomerRequest.getCustomerId()).getEmail()))
            return false;
        return true;
    }
    public boolean checkIfTaxNumberChanged(UpdateCorporateCustomerRequest updateCorporateCustomerRequest){
        if(updateCorporateCustomerRequest.getTaxNumber().equals(this.corporateCustomerDao.getById(updateCorporateCustomerRequest.getCustomerId()).getTaxNumber()))
            return false;
        return true;
    }
}
