package com.turkcell.rentACar.business.abstracts;

import com.turkcell.rentACar.business.dtos.invoiceDtos.InvoiceDto;
import com.turkcell.rentACar.business.dtos.invoiceDtos.InvoiceListDto;
import com.turkcell.rentACar.business.requests.creates.CreateCarRentalRequest;
import com.turkcell.rentACar.business.requests.deletes.DeleteInvoiceRequest;
import com.turkcell.rentACar.core.utilities.exceptions.BusinessException;
import com.turkcell.rentACar.core.utilities.results.DataResult;
import com.turkcell.rentACar.core.utilities.results.Result;
import com.turkcell.rentACar.entities.concretes.CarRental;
import com.turkcell.rentACar.entities.concretes.Invoice;

import java.time.LocalDate;
import java.util.List;

public interface InvoiceService {
    DataResult<List<InvoiceListDto>> getAll();
    DataResult<Invoice> createInvoice(CarRental carRental) throws BusinessException;
    DataResult<InvoiceDto> getById(int id) throws BusinessException;
    Result delete(DeleteInvoiceRequest deleteInvoiceRequest) throws BusinessException;
    DataResult<List<InvoiceListDto>> getInvoicesByCustomerId(int customerId);
    DataResult<List<InvoiceListDto>> getInvoicesBetweenDates(LocalDate firstDate,LocalDate secondDate);
    Invoice getInvoiceByInvoiceNo(int id);
    //Result updateInvoiceIfCarRentalUpdates(int rentalId,int newRentDayValue,LocalDate startDate,LocalDate endDate);
    //Result updateInvoiceIfOrderedAdditionalServiceDeletes(int invoiceNo,double price);
    //Result updateInvoiceIfOrderedAdditionalServiceUpdates(int invoiceNo,double price);
}
