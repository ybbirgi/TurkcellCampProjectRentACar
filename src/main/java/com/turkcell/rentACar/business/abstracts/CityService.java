package com.turkcell.rentACar.business.abstracts;

import com.turkcell.rentACar.business.dtos.cityDtos.CityDto;
import com.turkcell.rentACar.business.dtos.cityDtos.CityListDto;
import com.turkcell.rentACar.core.utilities.exceptions.BusinessException;
import com.turkcell.rentACar.core.utilities.exceptions.NotFoundException;
import com.turkcell.rentACar.core.utilities.results.DataResult;
import com.turkcell.rentACar.entities.concretes.City;

import java.util.List;

public interface CityService {
    DataResult<List<CityListDto>> getAll();
    DataResult<CityDto> getById(int id) throws NotFoundException;
    City getCityByCityId(int id);
}
