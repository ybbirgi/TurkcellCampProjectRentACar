package com.turkcell.rentACar.business.concretes;

import com.turkcell.rentACar.business.abstracts.ColorService;
import com.turkcell.rentACar.business.constants.messages.BusinessMessages;
import com.turkcell.rentACar.business.dtos.colorDtos.ColorDto;
import com.turkcell.rentACar.business.dtos.colorDtos.ColorListDto;
import com.turkcell.rentACar.business.requests.creates.CreateColorRequest;
import com.turkcell.rentACar.business.requests.deletes.DeleteColorRequest;
import com.turkcell.rentACar.business.requests.updates.UpdateColorRequest;
import com.turkcell.rentACar.core.utilities.exceptions.AlreadyExistsException;
import com.turkcell.rentACar.core.utilities.exceptions.BusinessException;
import com.turkcell.rentACar.core.utilities.exceptions.NotFoundException;
import com.turkcell.rentACar.core.utilities.exceptions.UpdateHasNoChangesException;
import com.turkcell.rentACar.core.utilities.mapping.ModelMapperManager;
import com.turkcell.rentACar.core.utilities.results.*;
import com.turkcell.rentACar.dataAccess.abstracts.ColorDao;
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
    public DataResult<List<ColorListDto>> getAll() {
        List<Color> result = this.colorDao.findAll();
        List<ColorListDto> response = result.stream().map(color->this.modelMapperService.forDto().map(color,ColorListDto.class)).collect(Collectors.toList());
        return new SuccessDataResult<List<ColorListDto>>(response, BusinessMessages.GlobalMessages.DATA_LISTED_SUCCESSFULLY);
    }

    @Override
    public Result add(CreateColorRequest createColorRequest) throws AlreadyExistsException {
        checkIfColorExistsByName(createColorRequest.getColorName());

        Color color = this.modelMapperService.forRequest().map(createColorRequest,Color.class);

        this.colorDao.save(color);

        return new SuccessResult(BusinessMessages.GlobalMessages.DATA_ADDED_SUCCESSFULLY);
    }

    @Override
    public DataResult<ColorDto> getById(int id) throws NotFoundException {

        checkIfColorExistsById(id);

        Color color = colorDao.getById(id);

        ColorDto colorDto = this.modelMapperService.forDto().map(color,ColorDto.class);

        return new SuccessDataResult<ColorDto>(colorDto, BusinessMessages.GlobalMessages.DATA_LISTED_SUCCESSFULLY);
    }

    @Override
    public Result update(UpdateColorRequest updateColorRequest) throws NotFoundException, UpdateHasNoChangesException {

        checkIfColorExistsById(updateColorRequest.getColorId());
        checkIfUpdateHasNoChanges(updateColorRequest.getColorName());

        Color color = this.modelMapperService.forRequest().map(updateColorRequest,Color.class);

        this.colorDao.save(color);

        return new SuccessResult(BusinessMessages.GlobalMessages.DATA_UPDATED_SUCCESSFULLY);
    }

    @Override
    public Result delete(DeleteColorRequest deleteColorRequest) throws NotFoundException{
        checkIfColorExistsById(deleteColorRequest.getColorId());

        Color color = this.modelMapperService.forRequest().map(deleteColorRequest,Color.class);

        this.colorDao.delete(color);

        return new SuccessResult(BusinessMessages.GlobalMessages.DATA_DELETED_SUCCESSFULLY);
    }

    void checkIfColorExistsByName(String name) throws AlreadyExistsException {
        if(this.colorDao.existsByColorName(name))
            throw new AlreadyExistsException(BusinessMessages.ColorMessages.COLOR_ALREADY_EXISTS);
    }

    void checkIfColorExistsById(int id) throws NotFoundException {
        if(!this.colorDao.existsById(id))
            throw new NotFoundException(BusinessMessages.ColorMessages.COLOR_NOT_FOUND);
    }

    void checkIfUpdateHasNoChanges(String name) throws  UpdateHasNoChangesException{
        if(this.colorDao.existsByColorName(name))
            throw new UpdateHasNoChangesException(BusinessMessages.ColorMessages.COLOR_HAS_NO_CHANGES);
    }

}
