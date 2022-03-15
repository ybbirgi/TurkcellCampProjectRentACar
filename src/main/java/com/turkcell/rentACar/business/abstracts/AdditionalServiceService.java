package com.turkcell.rentACar.business.abstracts;

import com.turkcell.rentACar.business.dtos.additionalServiceDtos.AdditionalServiceDto;
import com.turkcell.rentACar.business.dtos.additionalServiceDtos.AdditionalServiceListDto;
import com.turkcell.rentACar.business.requests.creates.CreateAdditionalServiceRequest;
import com.turkcell.rentACar.business.requests.deletes.DeleteAdditionalServiceRequest;
import com.turkcell.rentACar.business.requests.updates.UpdateAdditionalServiceRequest;
import com.turkcell.rentACar.core.utilities.exceptions.AlreadyExistsException;
import com.turkcell.rentACar.core.utilities.exceptions.NotFoundException;
import com.turkcell.rentACar.core.utilities.exceptions.BusinessException;
import com.turkcell.rentACar.core.utilities.exceptions.UpdateHasNoChangesException;
import com.turkcell.rentACar.core.utilities.results.DataResult;
import com.turkcell.rentACar.core.utilities.results.Result;
import com.turkcell.rentACar.entities.concretes.AdditionalService;

import java.util.List;

public interface AdditionalServiceService {
    DataResult<List<AdditionalServiceListDto>> getAll();
    Result add(CreateAdditionalServiceRequest createAdditionalServiceRequest) throws AlreadyExistsException;
    DataResult<AdditionalServiceDto> getById(int id) throws NotFoundException;
    Result update(UpdateAdditionalServiceRequest updateAdditionalServiceRequest) throws NotFoundException, UpdateHasNoChangesException;
    Result delete(DeleteAdditionalServiceRequest deleteAdditionalServiceRequest) throws NotFoundException;
    AdditionalService getServiceByServiceId(int id);
}
