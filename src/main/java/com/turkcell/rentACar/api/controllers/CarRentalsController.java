package com.turkcell.rentACar.api.controllers;

import com.turkcell.rentACar.api.models.CorporateRentEndModel;
import com.turkcell.rentACar.api.models.IndividualRentEndModel;
import com.turkcell.rentACar.api.models.RentalCarModel;
import com.turkcell.rentACar.business.abstracts.CarRentalService;
import com.turkcell.rentACar.business.abstracts.InvoiceService;
import com.turkcell.rentACar.business.abstracts.OrderedAdditionalServiceService;
import com.turkcell.rentACar.business.dtos.carRentalDtos.CarRentalListDto;
import com.turkcell.rentACar.business.requests.creates.CreateCarRentalRequest;
import com.turkcell.rentACar.business.requests.deletes.DeleteCarRentalRequest;
import com.turkcell.rentACar.business.requests.ends.EndCarRentalRequest;
import com.turkcell.rentACar.business.requests.updates.UpdateCarRentalRequest;
import com.turkcell.rentACar.core.utilities.exceptions.BusinessException;
import com.turkcell.rentACar.core.utilities.results.DataResult;
import com.turkcell.rentACar.core.utilities.results.Result;
import com.turkcell.rentACar.entities.concretes.CarRental;
import org.aspectj.bridge.MessageWriter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.util.Pair;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api/carRentals")
public class CarRentalsController {

    private CarRentalService carRentalService;
    private OrderedAdditionalServiceService orderedAdditionalServiceService;
    private InvoiceService invoiceService;

    @Autowired
    public CarRentalsController(CarRentalService carRentalService,
                                OrderedAdditionalServiceService orderedAdditionalServiceService,
                                InvoiceService invoiceService) {
        this.carRentalService = carRentalService;
        this.orderedAdditionalServiceService = orderedAdditionalServiceService;
        this.invoiceService = invoiceService;
    }

    @GetMapping("/getAll")
    DataResult<List<CarRentalListDto>> getAll(){return carRentalService.getAll();}

    @PostMapping("/rentCarToIndividualCustomer")
    DataResult<CarRental> rentCarToIndividualCustomer(@RequestBody @Valid RentalCarModel rentalCarModel)throws BusinessException {
        return this.carRentalService.rentCarToIndividualCustomer(rentalCarModel);
    }

    @PostMapping("/rentCarToCorporateCustomer")
    DataResult<CarRental> rentCarToCorporateCustomer(@RequestBody @Valid RentalCarModel rentalCarModel)throws BusinessException {
        return this.carRentalService.rentCarToCorporateCustomer(rentalCarModel);
    }

    @PostMapping("/endRentForIndividual")
    DataResult<CarRental> endCarRentalForIndividual(@RequestBody @Valid IndividualRentEndModel individualRentEndModel) throws BusinessException{return this.carRentalService.endCarRentalForIndividual(individualRentEndModel);}

    @PostMapping("/endRentForCorporate")
    DataResult<CarRental> endCarRentalForCorporate(@RequestBody @Valid CorporateRentEndModel corporateRentEndModel) throws BusinessException{return this.carRentalService.endCarRentalForCorporate(corporateRentEndModel);}

    @PutMapping("/update")
    Result update(@RequestBody UpdateCarRentalRequest updateCarRentalRequest)throws BusinessException{return carRentalService.update(updateCarRentalRequest);}

    @DeleteMapping("/delete")
    Result delete(@RequestBody DeleteCarRentalRequest deleteCarRentalRequest)throws BusinessException{return carRentalService.delete(deleteCarRentalRequest);}

    @GetMapping("/getByCarId")
    DataResult<List<CarRentalListDto>> getByCarId(@RequestParam int id){return carRentalService.getByCarId(id);}
}

