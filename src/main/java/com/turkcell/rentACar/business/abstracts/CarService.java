package com.turkcell.rentACar.business.abstracts;

import com.turkcell.rentACar.business.dtos.CarListDto;
import com.turkcell.rentACar.business.requests.CreateCarRequest;
import com.turkcell.rentACar.business.requests.DeleteCarRequest;
import com.turkcell.rentACar.business.requests.UpdateCarRequest;

import java.util.List;

public interface CarService {
    List<CarListDto> getAll();
    void add(CreateCarRequest createCarRequest) throws Exception;
    void update(UpdateCarRequest updateCarRequest);
    void delete(DeleteCarRequest DeleteCarRequest);
    CarListDto getById(int id);
}
