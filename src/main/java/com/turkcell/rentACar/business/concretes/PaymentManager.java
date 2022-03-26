package com.turkcell.rentACar.business.concretes;

import com.turkcell.rentACar.api.models.CorporateCustomerPaymentModel;
import com.turkcell.rentACar.api.models.EnumSaveCreditCard;
import com.turkcell.rentACar.api.models.IndividualCustomerPaymentModel;
import com.turkcell.rentACar.business.abstracts.*;
import com.turkcell.rentACar.business.constants.messages.BusinessMessages;
import com.turkcell.rentACar.business.dtos.paymentDtos.PaymentDto;
import com.turkcell.rentACar.business.dtos.paymentDtos.PaymentListDto;
import com.turkcell.rentACar.business.requests.creates.CreateCreditCardRequest;
import com.turkcell.rentACar.business.requests.deletes.DeletePaymentRequest;
import com.turkcell.rentACar.core.bankServices.PosService;
import com.turkcell.rentACar.core.utilities.exceptions.BusinessException;
import com.turkcell.rentACar.core.utilities.mapping.ModelMapperService;
import com.turkcell.rentACar.core.utilities.results.DataResult;
import com.turkcell.rentACar.core.utilities.results.Result;
import com.turkcell.rentACar.core.utilities.results.SuccessDataResult;
import com.turkcell.rentACar.core.utilities.results.SuccessResult;
import com.turkcell.rentACar.dataAccess.abstracts.PaymentDao;
import com.turkcell.rentACar.entities.concretes.CarRental;
import com.turkcell.rentACar.entities.concretes.Invoice;
import com.turkcell.rentACar.entities.concretes.Payment;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class PaymentManager implements PaymentService {

    private CarRentalService carRentalService;
    private InvoiceService invoiceService;
    private PaymentDao paymentDao;
    private ModelMapperService modelMapperService;
    private PosService posService;
    private CustomerService customerService;
    private CreditCardService creditCardService;

    @Autowired
    public PaymentManager(CarRentalService carRentalService,
                          InvoiceService invoiceService,
                          PaymentDao paymentDao,
                          ModelMapperService modelMapperService,
                          PosService posService,
                          CustomerService customerService,
                          CreditCardService creditCardService) {
        this.carRentalService = carRentalService;
        this.invoiceService = invoiceService;
        this.paymentDao = paymentDao;
        this.modelMapperService = modelMapperService;
        this.posService = posService;
        this.customerService = customerService;
        this.creditCardService = creditCardService;
    }

    @Override
    public DataResult<List<PaymentListDto>> getAll() {
        List<Payment> result = this.paymentDao.findAll();

        List<PaymentListDto> response = result.stream().map(payment->
                this.modelMapperService.forDto().map(payment, PaymentListDto.class)).collect(Collectors.toList());

        return new SuccessDataResult<List<PaymentListDto>>(response, BusinessMessages.GlobalMessages.DATA_LISTED_SUCCESSFULLY);
    }

    @Override
    public DataResult<List<PaymentListDto>> getPaymentsByCustomerId(int customerId) {
        List<Payment> result = this.paymentDao.getAllByCustomer_CustomerId(customerId);
        List<PaymentListDto> response = result.stream().map(payment ->
                this.modelMapperService.forDto().map(payment, PaymentListDto.class)).collect(Collectors.toList());
        if(!result.isEmpty()) {
            for (PaymentListDto paymentListDto : response) {
                paymentListDto.setCustomerId(customerId);
            }
        }
        return new SuccessDataResult<List<PaymentListDto>>(response,BusinessMessages.GlobalMessages.DATA_LISTED_SUCCESSFULLY);
    }

    @Override
    public Result makePaymentForIndividualCustomer(IndividualCustomerPaymentModel individualCustomerPaymentModel, EnumSaveCreditCard enumSaveCreditCard) throws BusinessException {

        checkIfCardIsValid(individualCustomerPaymentModel.getCreditCardRequest());

        runPaymentSuccesorForIndividualCustomer(individualCustomerPaymentModel,enumSaveCreditCard);

        return new SuccessResult(BusinessMessages.GlobalMessages.DATA_ADDED_SUCCESSFULLY);
    }

    @Override
    public Result makePaymentForCorporateCustomer(CorporateCustomerPaymentModel corporateCustomerPaymentModel, EnumSaveCreditCard enumSaveCreditCard) throws BusinessException {
        checkIfCardIsValid(corporateCustomerPaymentModel.getCreditCardRequest());

        runPaymentSuccesorForCorporateCustomer(corporateCustomerPaymentModel,enumSaveCreditCard);

        return new SuccessResult(BusinessMessages.GlobalMessages.DATA_ADDED_SUCCESSFULLY);
    }

    @Override
    public DataResult<PaymentDto> getById(int id) throws BusinessException {
        checkIfPaymentExistsById(id);

        Payment payment = this.paymentDao.getById(id);

        PaymentDto paymentDto = this.modelMapperService.forDto().map(payment,PaymentDto.class);

        return new SuccessDataResult<PaymentDto>(paymentDto,BusinessMessages.GlobalMessages.DATA_LISTED_SUCCESSFULLY);

    }

    @Override
    public Result delete(DeletePaymentRequest deletePaymentRequest) throws BusinessException {
        checkIfPaymentExistsById(deletePaymentRequest.getPaymentId());

        Payment payment = this.paymentDao.getById(deletePaymentRequest.getPaymentId());

        this.paymentDao.delete(payment);

        return new SuccessResult(BusinessMessages.GlobalMessages.DATA_DELETED_SUCCESSFULLY);

    }
    @Transactional
    public void runPaymentSuccesorForIndividualCustomer(IndividualCustomerPaymentModel individualCustomerPaymentModel,EnumSaveCreditCard enumSaveCreditCard) throws BusinessException{
        CarRental carRental = this.carRentalService.rentCarToIndividualCustomer(individualCustomerPaymentModel.getRentalCarModel()).getData();

        Invoice invoice = this.invoiceService.createInvoice(carRental).getData();

        checkIfBalanceIsEnough(invoice.getTotalPayment());

        Payment payment = fillPaymentFields(carRental,invoice);

        if(enumSaveCreditCard == enumSaveCreditCard.YES)
            this.creditCardService.add(individualCustomerPaymentModel.getCreditCardRequest(),
                    individualCustomerPaymentModel.getRentalCarModel().getCreateCarRentalRequest().getCustomerId());

        this.paymentDao.save(payment);

    }
    @Transactional
    public void runPaymentSuccesorForCorporateCustomer(CorporateCustomerPaymentModel corporateCustomerPaymentModel,EnumSaveCreditCard enumSaveCreditCard) throws BusinessException{
        CarRental carRental = this.carRentalService.rentCarToIndividualCustomer(corporateCustomerPaymentModel.getRentalCarModel()).getData();

        Invoice invoice = this.invoiceService.createInvoice(carRental).getData();

        checkIfBalanceIsEnough(invoice.getTotalPayment());

        Payment payment = fillPaymentFields(carRental,invoice);

        if(enumSaveCreditCard == enumSaveCreditCard.YES)
            this.creditCardService.add(corporateCustomerPaymentModel.getCreditCardRequest(),
                    corporateCustomerPaymentModel.getRentalCarModel().getCreateCarRentalRequest().getCustomerId());

        this.paymentDao.save(payment);

    }

    public void checkIfPaymentExistsById(int id) throws BusinessException{
        if(!this.paymentDao.existsById(id))
            throw new BusinessException(BusinessMessages.PaymentMessages.PAYMENT_NOT_FOUND);
    }

    private void checkIfCardIsValid(CreateCreditCardRequest createCreditCardRequest) throws BusinessException {

        if (!this.posService.isCardValid(createCreditCardRequest)){
            throw new BusinessException(BusinessMessages.PaymentMessages.INVALID_PAYMENT);
        }
    }

    private Payment fillPaymentFields(CarRental carRental,Invoice invoice){
        Payment payment = new Payment();
        payment.setCustomer(this.customerService.getCustomerByCustomerId(carRental.getCustomer().getCustomerId()));
        payment.setInvoice(invoice);
        payment.setCarRental(carRental);
        payment.setTotalAmount(invoice.getTotalPayment());
        return payment;
    }
    private void checkIfBalanceIsEnough(double amount) throws BusinessException{
        if(!this.posService.makePayment(amount))
            throw new BusinessException(BusinessMessages.PaymentMessages.INSUFFICIENT_BALANCE);
    }
}
