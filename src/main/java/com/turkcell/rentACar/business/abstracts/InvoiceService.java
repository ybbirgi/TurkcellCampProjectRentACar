package com.turkcell.rentACar.business.abstracts;

import com.turkcell.rentACar.api.models.InvoiceDateModel;
import com.turkcell.rentACar.api.models.RentalCarModel;
import com.turkcell.rentACar.business.dtos.brandDtos.BrandDto;
import com.turkcell.rentACar.business.dtos.invoiceDtos.InvoiceListDto;
import com.turkcell.rentACar.business.requests.creates.CreateCarRentalRequest;
import com.turkcell.rentACar.core.utilities.exceptions.BusinessException;
import com.turkcell.rentACar.core.utilities.results.DataResult;
import com.turkcell.rentACar.core.utilities.results.Result;
import com.turkcell.rentACar.entities.concretes.Invoice;

import java.time.LocalDate;
import java.util.List;

public interface InvoiceService {
    DataResult<List<InvoiceListDto>> getAll();
    DataResult<Integer> createInvoice(CreateCarRentalRequest createCarRentalRequest,int rentalId) throws BusinessException;
    DataResult<BrandDto> getById(int id) throws BusinessException;
    Result update(RentalCarModel rentalCarModel) throws BusinessException;
    Result delete(RentalCarModel rentalCarModel) throws BusinessException;
    DataResult<List<InvoiceListDto>> getInvoicesByCustomerId(int customerId);
    DataResult<List<InvoiceListDto>> getInvoicesBetweenDates(LocalDate firstDate,LocalDate secondDate);
    Result addAdditionalServicesToInvoice(int id);
    Invoice getInvoiceByInvoiceNo(int id);
    Result updateInvoiceIfCarRentalUpdates(int rentalId,int newRentDayValue,LocalDate startDate,LocalDate endDate);
}
