package com.turkcell.rentACar.business.abstracts;

import com.turkcell.rentACar.business.dtos.individualCustomerDtos.IndividualCustomerDto;
import com.turkcell.rentACar.business.dtos.individualCustomerDtos.IndividualCustomerListDto;
import com.turkcell.rentACar.business.requests.creates.CreateIndividualCustomerRequest;
import com.turkcell.rentACar.business.requests.deletes.DeleteIndividualCustomerRequest;
import com.turkcell.rentACar.business.requests.updates.UpdateIndividualCustomerRequest;
import com.turkcell.rentACar.core.utilities.exceptions.BusinessException;
import com.turkcell.rentACar.core.utilities.exceptions.EmailAlreadyUsedException;
import com.turkcell.rentACar.core.utilities.exceptions.NationalIdentityAlreadyUsedException;
import com.turkcell.rentACar.core.utilities.exceptions.NotFoundException;
import com.turkcell.rentACar.core.utilities.results.DataResult;
import com.turkcell.rentACar.core.utilities.results.Result;

import java.util.List;

public interface IndividualCustomerService {
    DataResult<List<IndividualCustomerListDto>> getAll();
    Result add(CreateIndividualCustomerRequest createIndividualCustomerRequest) throws BusinessException;
    DataResult<IndividualCustomerDto> getById(int id) throws NotFoundException;
    Result update(UpdateIndividualCustomerRequest updateIndividualCustomerRequest) throws BusinessException;
    Result delete(DeleteIndividualCustomerRequest deleteIndividualCustomerRequest) throws NotFoundException;
}
