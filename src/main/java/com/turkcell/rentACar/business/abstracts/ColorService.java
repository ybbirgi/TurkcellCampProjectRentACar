package com.turkcell.rentACar.business.abstracts;

import com.turkcell.rentACar.business.dtos.ColorListDto;
import com.turkcell.rentACar.business.requests.CreateColorRequest;
import com.turkcell.rentACar.business.requests.DeleteColorRequest;
import com.turkcell.rentACar.business.requests.UpdateColorRequest;

import java.util.List;

public interface ColorService {
    List<ColorListDto> getAll();
    void add(CreateColorRequest createColorRequest) throws Exception;
    ColorListDto getById(int id);
    void update(UpdateColorRequest updateColorRequest);
    void delete(DeleteColorRequest DeleteColorRequest);
}
