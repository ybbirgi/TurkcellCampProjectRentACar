package com.turkcell.rentACar.business.abstracts;

import com.turkcell.rentACar.business.dtos.corporateCustomerDtos.CorporateCustomerDto;
import com.turkcell.rentACar.business.dtos.corporateCustomerDtos.CorporateCustomerListDto;
import com.turkcell.rentACar.business.requests.creates.CreateCorporateCustomerRequest;
import com.turkcell.rentACar.business.requests.deletes.DeleteCorporateCustomerRequest;
import com.turkcell.rentACar.business.requests.updates.UpdateCorporateCustomerRequest;
import com.turkcell.rentACar.core.utilities.exceptions.BusinessException;
import com.turkcell.rentACar.core.utilities.exceptions.EmailAlreadyUsedException;
import com.turkcell.rentACar.core.utilities.exceptions.NotFoundException;
import com.turkcell.rentACar.core.utilities.exceptions.TaxNumberAlreadyUsedException;
import com.turkcell.rentACar.core.utilities.results.DataResult;
import com.turkcell.rentACar.core.utilities.results.Result;

import java.util.List;

public interface CorporateCustomerService {
    DataResult<List<CorporateCustomerListDto>> getAll();
    Result add(CreateCorporateCustomerRequest createCorporateCustomerRequest) throws BusinessException;
    DataResult<CorporateCustomerDto> getById(int id) throws NotFoundException;
    Result update(UpdateCorporateCustomerRequest updateCorporateCustomerRequest) throws BusinessException;
    Result delete(DeleteCorporateCustomerRequest deleteCorporateCustomerRequest) throws NotFoundException;
}
