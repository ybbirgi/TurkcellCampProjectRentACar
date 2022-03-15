package com.turkcell.rentACar.api.controllers;

import com.turkcell.rentACar.api.models.InvoiceDateModel;
import com.turkcell.rentACar.business.abstracts.InvoiceService;
import com.turkcell.rentACar.business.dtos.invoiceDtos.InvoiceListDto;
import com.turkcell.rentACar.core.utilities.results.DataResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/api/Invoice")
public class InvoicesController {
    private InvoiceService invoiceService;

    @Autowired
    public InvoicesController(InvoiceService invoiceService) {
        this.invoiceService = invoiceService;
    }

    @GetMapping("/getInvoicesByCustomerId")
    DataResult<List<InvoiceListDto>> getInvoicesByCustomerId(@RequestParam int customerId){return this.invoiceService.getInvoicesByCustomerId(customerId);}

    @GetMapping("/getInvoicesBetweenDates/{first_date}/{second_date}")
    DataResult<List<InvoiceListDto>> getInvoicesBetweenDates(@PathVariable(value = "first_date")
                                                             @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate firstDate,
                                                             @PathVariable(value = "second_date")
                                                             @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate secondDate){
        return this.invoiceService.getInvoicesBetweenDates(firstDate,secondDate);}
}
