package com.turkcell.rentACar.business.abstracts;

import com.turkcell.rentACar.business.dtos.ColorListDto;
import com.turkcell.rentACar.business.requests.CreateColorRequest;
import com.turkcell.rentACar.entities.concretes.Color;

import java.util.List;

public interface ColorService {
    List<ColorListDto> getAll();
    void add(CreateColorRequest createColorRequest) throws Exception;
    ColorListDto getById(int id);
}
