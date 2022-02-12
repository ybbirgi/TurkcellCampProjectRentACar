package com.turkcell.rentACar.business.concretes;

import com.turkcell.rentACar.business.abstracts.ColorService;
import com.turkcell.rentACar.business.dtos.BrandListDto;
import com.turkcell.rentACar.business.dtos.ColorListDto;
import com.turkcell.rentACar.business.requests.CreateColorRequest;
import com.turkcell.rentACar.core.utilities.mapping.ModelMapperManager;
import com.turkcell.rentACar.dataAccess.abstracts.ColorDao;
import com.turkcell.rentACar.entities.concretes.Brand;
import com.turkcell.rentACar.entities.concretes.Color;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ColorManager implements ColorService {

    private ColorDao colorDao;
    private ModelMapperManager modelMapperService;

    @Autowired
    public ColorManager(ColorDao colorDao,ModelMapperManager modelMapperService) {

        this.colorDao = colorDao;
        this.modelMapperService = modelMapperService;
    }

    @Override
    public List<ColorListDto> getAll() {
        
        List<Color> result = this.colorDao.findAll();
        List<ColorListDto> response = result.stream().map(color->this.modelMapperService.forDto().map(color,ColorListDto.class)).collect(Collectors.toList());
        return response;
    }

    @Override
    public void add(CreateColorRequest createColorRequest) throws Exception {
        Color color = this.modelMapperService.forRequest().map(createColorRequest,Color.class);
        checkIfSameColor(color);
        this.colorDao.save(color);
    }

    @Override
    public ColorListDto getById(int id) {
        Color color = colorDao.getById(id);
        ColorListDto colorListDto = this.modelMapperService.forDto().map(color,ColorListDto.class);
        return colorListDto;
    }

    void checkIfSameColor(Color color) throws Exception {
        if(this.colorDao.getAllByName(color.getName()).stream().count()!=0)
            throw new Exception("This brand already exists");
        /*List<Color> colors = colorDao.findAll();
        for (Color c:colors) {
            if(c.getName().equals(color.getName()))
                throw new Exception("This color already exists");
        }*/
    }
}
