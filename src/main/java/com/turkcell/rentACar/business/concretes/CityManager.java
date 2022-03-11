package com.turkcell.rentACar.business.concretes;

import com.turkcell.rentACar.business.abstracts.CityService;
import com.turkcell.rentACar.business.dtos.cityDtos.CityDto;
import com.turkcell.rentACar.business.dtos.cityDtos.CityListDto;
import com.turkcell.rentACar.core.utilities.exceptions.BusinessException;
import com.turkcell.rentACar.core.utilities.mapping.ModelMapperService;
import com.turkcell.rentACar.core.utilities.results.DataResult;
import com.turkcell.rentACar.core.utilities.results.SuccessDataResult;
import com.turkcell.rentACar.dataAccess.abstracts.CityDao;
import com.turkcell.rentACar.entities.concretes.City;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;
@Service
public class CityManager implements CityService {
    private CityDao cityDao;
    private ModelMapperService modelMapperService;

    public CityManager(CityDao cityDao, ModelMapperService modelMapperService) {
        this.cityDao = cityDao;
        this.modelMapperService = modelMapperService;
    }

    @Override
    public DataResult<List<CityListDto>> getAll() {
        List<City> result = this.cityDao.findAll();
        List<CityListDto> response = result.stream().map(city -> this.modelMapperService.forDto().map(city,CityListDto.class)).collect(Collectors.toList());
        return new SuccessDataResult<List<CityListDto>>(response,"Cities Listed Successfully");
    }

    @Override
    public DataResult<CityDto> getById(int id) throws BusinessException {
        City city = this.cityDao.getById(id);
        CityDto cityDto = this.modelMapperService.forDto().map(city,CityDto.class);
        return new SuccessDataResult<CityDto>(cityDto,"City Listed Successfully");
    }

    @Override
    public City getCityByCityId(int id) {
        return this.cityDao.getById(id);
    }

}
