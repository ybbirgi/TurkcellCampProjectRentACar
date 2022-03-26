package com.turkcell.rentACar.business.abstracts;

import com.turkcell.rentACar.business.dtos.creditCardDto.CreditCardDto;
import com.turkcell.rentACar.business.dtos.creditCardDto.CreditCardListDto;
import com.turkcell.rentACar.business.requests.creates.CreateCreditCardRequest;
import com.turkcell.rentACar.business.requests.deletes.DeleteCreditCardRequest;
import com.turkcell.rentACar.business.requests.updates.UpdateCreditCardRequest;
import com.turkcell.rentACar.core.utilities.exceptions.BusinessException;
import com.turkcell.rentACar.core.utilities.results.DataResult;
import com.turkcell.rentACar.core.utilities.results.Result;

import java.util.List;

public interface CreditCardService {

    DataResult<List<CreditCardListDto>> getAll();

    Result add(CreateCreditCardRequest createCreditCardRequest, int customerId) throws BusinessException;

    DataResult<CreditCardDto> getById(int id) throws BusinessException;

    DataResult<List<CreditCardListDto>> getByCustomerId(int id) throws BusinessException;

    Result update(UpdateCreditCardRequest updateCreditCardRequest) throws BusinessException;

    Result delete(DeleteCreditCardRequest deleteCreditCardRequest) throws BusinessException;
}
