package com.turkcell.rentACar.business.abstracts;

import com.turkcell.rentACar.business.dtos.additionalServiceDtos.AdditionalServiceDto;
import com.turkcell.rentACar.business.dtos.additionalServiceDtos.AdditionalServiceListDto;
import com.turkcell.rentACar.business.requests.creates.CreateAdditionalServiceRequest;
import com.turkcell.rentACar.business.requests.deletes.DeleteAdditionalServiceRequest;
import com.turkcell.rentACar.business.requests.updates.UpdateAdditionalServiceRequest;
import com.turkcell.rentACar.core.utilities.exceptions.BusinessException;
import com.turkcell.rentACar.core.utilities.results.DataResult;
import com.turkcell.rentACar.core.utilities.results.Result;

import java.util.List;

public interface AdditionalServiceService {
    DataResult<List<AdditionalServiceListDto>> getAll();
    Result add(CreateAdditionalServiceRequest createAdditionalServiceRequest) throws BusinessException;
    DataResult<AdditionalServiceDto> getById(int id) throws BusinessException;
    Result update(UpdateAdditionalServiceRequest updateAdditionalServiceRequest) throws BusinessException;
    Result delete(DeleteAdditionalServiceRequest deleteAdditionalServiceRequest) throws BusinessException;
}
