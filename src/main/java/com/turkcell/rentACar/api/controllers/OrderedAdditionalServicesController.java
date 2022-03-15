package com.turkcell.rentACar.api.controllers;

import com.turkcell.rentACar.business.abstracts.OrderedAdditionalServiceService;
import com.turkcell.rentACar.business.dtos.orderedAdditionalServiceDtos.OrderedAdditionalServiceDto;
import com.turkcell.rentACar.core.utilities.exceptions.NotFoundException;
import com.turkcell.rentACar.core.utilities.results.DataResult;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/orderedAdditionalServices")
public class OrderedAdditionalServicesController {
    private OrderedAdditionalServiceService orderedAdditionalServiceService;

    public OrderedAdditionalServicesController(OrderedAdditionalServiceService orderedAdditionalServiceService) {
        this.orderedAdditionalServiceService = orderedAdditionalServiceService;
    }
    @GetMapping("/getById")
    DataResult<OrderedAdditionalServiceDto> getById(@RequestParam int id) throws NotFoundException {return this.orderedAdditionalServiceService.getById(id);}
}
