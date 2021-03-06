package com.turkcell.rentACar.api.controllers;

import com.turkcell.rentACar.business.abstracts.ColorService;
import com.turkcell.rentACar.business.dtos.colorDtos.ColorDto;
import com.turkcell.rentACar.business.dtos.colorDtos.ColorListDto;
import com.turkcell.rentACar.business.requests.creates.CreateColorRequest;
import com.turkcell.rentACar.business.requests.deletes.DeleteColorRequest;
import com.turkcell.rentACar.business.requests.updates.UpdateColorRequest;
import com.turkcell.rentACar.core.utilities.exceptions.AlreadyExistsException;
import com.turkcell.rentACar.core.utilities.exceptions.BusinessException;
import com.turkcell.rentACar.core.utilities.exceptions.NotFoundException;
import com.turkcell.rentACar.core.utilities.exceptions.UpdateHasNoChangesException;
import com.turkcell.rentACar.core.utilities.results.DataResult;
import com.turkcell.rentACar.core.utilities.results.Result;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/colors")
public class ColorsController {
    private ColorService colorService;

    public ColorsController(ColorService colorService) {
        this.colorService = colorService;
    }

    @GetMapping("/getAll")
    DataResult<List<ColorListDto>> getAll(){
        return this.colorService.getAll();
    }
    @GetMapping("/getById")
    public DataResult<ColorDto> getById(@RequestParam int id) throws BusinessException{return this.colorService.getById(id);}
    @PostMapping("/add")
    public Result add(@RequestBody CreateColorRequest createColorRequest) throws AlreadyExistsException {
        return this.colorService.add(createColorRequest);
    }
    @PutMapping("/update")
    public Result update(@RequestBody UpdateColorRequest updateColorRequest) throws NotFoundException , UpdateHasNoChangesException {
       return this.colorService.update(updateColorRequest);
    }

    @DeleteMapping("/delete")
    public Result delete(@RequestBody DeleteColorRequest deleteColorRequest) throws NotFoundException{
        return this.colorService.delete(deleteColorRequest);
    }

}

