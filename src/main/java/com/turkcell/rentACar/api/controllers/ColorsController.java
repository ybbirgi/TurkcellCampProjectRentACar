package com.turkcell.rentACar.api.controllers;

import com.turkcell.rentACar.business.abstracts.ColorService;
import com.turkcell.rentACar.business.dtos.ColorListDto;
import com.turkcell.rentACar.business.requests.*;
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
    List<ColorListDto> getAll(){
        return this.colorService.getAll();
    }
    @GetMapping("/getById")
    public ColorListDto getById(@RequestParam int id){return this.colorService.getById(id);}
    @PostMapping("/add")
    public void add(@RequestBody CreateColorRequest createColorRequest) throws Exception {
        this.colorService.add(createColorRequest);
    }
    @PostMapping("/update")
    public void update(@RequestBody UpdateColorRequest updateColorRequest){
        this.colorService.update(updateColorRequest);
    }

    @PostMapping("/delete")
    public void delete(@RequestBody DeleteColorRequest deleteColorRequest){
        this.colorService.delete(deleteColorRequest);
    }

}
