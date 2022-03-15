package com.turkcell.rentACar.business.concretes;

import com.turkcell.rentACar.api.models.RentalCarModel;
import com.turkcell.rentACar.business.abstracts.*;
import com.turkcell.rentACar.business.dtos.brandDtos.BrandDto;
import com.turkcell.rentACar.business.dtos.invoiceDtos.InvoiceDto;
import com.turkcell.rentACar.business.dtos.invoiceDtos.InvoiceListDto;
import com.turkcell.rentACar.business.requests.creates.CreateCarRentalRequest;
import com.turkcell.rentACar.business.requests.deletes.DeleteInvoiceRequest;
import com.turkcell.rentACar.core.utilities.exceptions.BusinessException;
import com.turkcell.rentACar.core.utilities.exceptions.NotFoundException;
import com.turkcell.rentACar.core.utilities.mapping.ModelMapperService;
import com.turkcell.rentACar.core.utilities.results.*;
import com.turkcell.rentACar.dataAccess.abstracts.InvoiceDao;
import com.turkcell.rentACar.entities.concretes.Invoice;
import com.turkcell.rentACar.entities.concretes.OrderedAdditionalService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class InvoiceManager implements InvoiceService {
    private InvoiceDao invoiceDao;
    private ModelMapperService modelMapperService;
    private CarRentalService carRentalService;
    private CarService carService;
    private OrderedAdditionalServiceService orderedAdditionalServiceService;
    private CityService cityService;
    private CustomerService customerService;
    @Autowired
    public InvoiceManager(InvoiceDao invoiceDao,
                          ModelMapperService modelMapperService,
                          CarRentalService carRentalService,
                          CarService carService,
                          OrderedAdditionalServiceService orderedAdditionalServiceService,
                          CityService cityService,
                          CustomerService customerService) {
        this.invoiceDao = invoiceDao;
        this.modelMapperService = modelMapperService;
        this.carRentalService = carRentalService;
        this.carService = carService;
        this.orderedAdditionalServiceService = orderedAdditionalServiceService;
        this.cityService = cityService;
        this.customerService = customerService;
    }

    @Override
    public DataResult<List<InvoiceListDto>> getAll() {
        List<Invoice> result=this.invoiceDao.findAll();
        List<InvoiceListDto> response = result.stream().map(invoice->this.modelMapperService.forDto().map(invoice,InvoiceListDto.class)).collect(Collectors.toList());
        return new SuccessDataResult<List<InvoiceListDto>>(response,"Invoices Listed Successfully");
    }

    @Override
    public DataResult<Integer> createInvoice(CreateCarRentalRequest createCarRentalRequest,int rentId) throws BusinessException {
        Invoice invoice = new Invoice();
        manuelMappingForInvoice(invoice,rentId);
        invoice.setTotalPayment(calculateCarRentPrice(invoice));
        this.invoiceDao.save(invoice);
        return new SuccessDataResult<Integer>(invoice.getInvoiceNo(),"Invoice Created Successfully");
    }

    @Override
    public DataResult<InvoiceDto> getById(int id) throws BusinessException {
        checkIfInvoiceExistsById(id);

        Invoice invoice = this.invoiceDao.getById(id);

        InvoiceDto invoiceDto = this.modelMapperService.forDto().map(invoice,InvoiceDto.class);

        return new SuccessDataResult<InvoiceDto>(invoiceDto,"Invoice Listed Successfully");
    }

    @Override
    public Result delete(DeleteInvoiceRequest deleteInvoiceRequest) throws BusinessException {
        checkIfInvoiceExistsById(deleteInvoiceRequest.getInvoiceNo());

        Invoice invoice = this.modelMapperService.forRequest().map(deleteInvoiceRequest,Invoice.class);

        this.invoiceDao.delete(invoice);

        return new SuccessResult("Invoice Deleted Successfully");
    }

    @Override
    public DataResult<List<InvoiceListDto>> getInvoicesByCustomerId(int customerId) {
        List<Invoice> invoices = this.invoiceDao.getAllByCustomer_CustomerId(customerId);
        if(invoices.isEmpty())
            return new ErrorDataResult<List<InvoiceListDto>>(null,"Current List is empty");
        List<InvoiceListDto> response = invoices.stream().map(invoice->this.modelMapperService.forDto().map(invoice,InvoiceListDto.class)).collect(Collectors.toList());
        for (InvoiceListDto invoiceListDto: response) {
            invoiceListDto.setCustomerId(customerId);
        }
        return new SuccessDataResult<List<InvoiceListDto>>(response,"Invoices Listed Successfully");

    }

    @Override
    public DataResult<List<InvoiceListDto>> getInvoicesBetweenDates(LocalDate firstDate,LocalDate secondDate) {
        List<Invoice> invoices = this.invoiceDao.findByInvoiceDateBetween(firstDate,secondDate);
        if(invoices.isEmpty())
            return new ErrorDataResult<List<InvoiceListDto>>(null,"Current List is empty");
        List<InvoiceListDto> response = invoices.stream().map(invoice->this.modelMapperService.forDto().map(invoice,InvoiceListDto.class)).collect(Collectors.toList());
        for (int i=0;i<invoices.size();i++){
            response.get(i).setCustomerId(invoices.get(i).getCustomer().getCustomerId());
        }
        return new SuccessDataResult<List<InvoiceListDto>>(response,"Invoices Between Dates Listed Successfully");
    }

    @Override
    public Result addAdditionalServicesToInvoice(int id) {
        if(this.orderedAdditionalServiceService.getOrderedAdditionalServicesByInvoiceNo(id)!=null) {
            double totalPayment = calculateAdditionalServicesPrice(this.orderedAdditionalServiceService.getOrderedAdditionalServicesByInvoiceNo(id), id);
            totalPayment += this.invoiceDao.getById(id).getTotalPayment();
            this.invoiceDao.getById(id).setTotalPayment(totalPayment);
            this.invoiceDao.save(this.invoiceDao.getById(id));
        }
        return new SuccessResult("Rent Operation Completed Successfully");
    }

    @Override
    public Invoice getInvoiceByInvoiceNo(int id) {
        return this.invoiceDao.getById(id);
    }

    @Override
    public Result updateInvoiceIfCarRentalUpdates(int rentalId,int newRentDayValue,LocalDate startDate,LocalDate endDate) {
        Invoice invoice = this.invoiceDao.findByCarRental_RentalId(rentalId);

        double totalPayment = invoice.getTotalPayment();
        double priceChangeForCarRental = this.carService.getCarByCarId(invoice.getCarRental().getCar().getCarId()).getCarDailyPrice()*(newRentDayValue- invoice.getRentDayValue());
        double priceChangeForAdditionalServices = calculateAdditionalServicesPriceIfRentalTimeChanges(invoice)*(newRentDayValue- invoice.getRentDayValue());

        invoice.setTotalPayment(totalPayment + priceChangeForCarRental + priceChangeForAdditionalServices );
        invoice.setRentDate(startDate);
        invoice.setRentEndDate(endDate);
        invoice.setRentDayValue(newRentDayValue);
        return new SuccessResult("Invoice Updated Successfully");
    }

    @Override
    public Result updateInvoiceIfOrderedAdditionalServiceDeletes(int invoiceNo, double price) {
        Invoice invoice = this.getInvoiceByInvoiceNo(invoiceNo);
        double totalPrice = invoice.getTotalPayment() - price * invoice.getRentDayValue();
        invoice.setTotalPayment(totalPrice);
        return new SuccessResult("Invoice Updated Successfully");
    }

    @Override
    public Result updateInvoiceIfOrderedAdditionalServiceUpdates(int invoiceNo, double price) {
        Invoice invoice = this.getInvoiceByInvoiceNo(invoiceNo);
        double totalPrice = invoice.getTotalPayment() + price * invoice.getRentDayValue();
        invoice.setTotalPayment(totalPrice);
        return new SuccessResult("Invoice Updated Successfully");
    }


    private void manuelMappingForInvoice(Invoice invoice,int rentalId){
        invoice.setCarRental(this.carRentalService.getByRentalId(rentalId));
        invoice.setInvoiceDate(LocalDate.now());
        invoice.setRentDate(invoice.getCarRental().getRentalDate());
        invoice.setRentEndDate(invoice.getCarRental().getRentalReturnDate());
        invoice.setRentDayValue((int) ChronoUnit.DAYS.between(invoice.getRentDate(),invoice.getRentEndDate()));
        invoice.setCustomer(this.customerService.getCustomerByCustomerId(invoice.getCarRental().getCustomer().getCustomerId()));
    }
    private double calculateCarRentPrice(Invoice invoice){
        double citySwapFee = 750.0;
        double rentPrice = this.carService.getCarByCarId(invoice.getCarRental().getCar().getCarId()).getCarDailyPrice()*invoice.getRentDayValue();
        if(this.cityService.getCityByCityId(this.carService.getCarByCarId(invoice.getCarRental().getCar().getCarId()).getCurrentCity().getCityId()).getCityId() != invoice.getCarRental().getReturnCity().getCityId()) {
            this.carService.getCarByCarId(invoice.getCarRental().getCar().getCarId()).setCurrentCity(this.cityService.getCityByCityId(invoice.getCarRental().getReturnCity().getCityId()));
            return rentPrice + citySwapFee;
        }
        return rentPrice;
    }
    private double calculateAdditionalServicesPrice(List<OrderedAdditionalService> orderedAdditionalServices,int id){
        double servicesPrice=0;
        for (OrderedAdditionalService orderedAdditionalService:orderedAdditionalServices) {
            servicesPrice += orderedAdditionalService.getAdditionalService().getDailyPrice()*orderedAdditionalService.getQuantity()*this.invoiceDao.getById(id).getRentDayValue();
        }
        return servicesPrice;
    }
    private double calculateAdditionalServicesPriceIfRentalTimeChanges(Invoice invoice){
        double priceChangeForAdditionalServices = 0;
        List<OrderedAdditionalService> orderedAdditionalServices = this.orderedAdditionalServiceService.getOrderedAdditionalServicesByInvoiceNo(invoice.getInvoiceNo());
        for (OrderedAdditionalService orderedAdditionalService:orderedAdditionalServices) {
            priceChangeForAdditionalServices += orderedAdditionalService.getAdditionalService().getDailyPrice()*orderedAdditionalService.getQuantity();
        }
        return priceChangeForAdditionalServices;
    }
    public void checkIfInvoiceExistsById(Integer invoiceNo) throws NotFoundException{
        if(!this.invoiceDao.existsById(invoiceNo))
            throw new NotFoundException("Invoice Not Found");
    }

}
