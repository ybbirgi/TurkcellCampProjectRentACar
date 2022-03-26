package com.turkcell.rentACar.api.controllers;

import com.turkcell.rentACar.api.models.CorporateCustomerPaymentModel;
import com.turkcell.rentACar.api.models.EnumSaveCreditCard;
import com.turkcell.rentACar.api.models.IndividualCustomerPaymentModel;
import com.turkcell.rentACar.business.abstracts.PaymentService;
import com.turkcell.rentACar.business.dtos.invoiceDtos.InvoiceListDto;
import com.turkcell.rentACar.business.dtos.paymentDtos.PaymentDto;
import com.turkcell.rentACar.business.dtos.paymentDtos.PaymentListDto;
import com.turkcell.rentACar.business.requests.deletes.DeletePaymentRequest;
import com.turkcell.rentACar.core.utilities.exceptions.BusinessException;
import com.turkcell.rentACar.core.utilities.results.DataResult;
import com.turkcell.rentACar.core.utilities.results.Result;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api/paymentsController")
public class PaymentsController {

    private PaymentService paymentService;

    public PaymentsController(PaymentService paymentService) {
        this.paymentService = paymentService;
    }

    @GetMapping("/getAll")
    DataResult<List<PaymentListDto>> getAll(){return this.paymentService.getAll();}

    @GetMapping("/getPaymentsByCustomerId")
    DataResult<List<PaymentListDto>> getPaymentsByCustomerId(@RequestParam int customerId){return this.paymentService.getPaymentsByCustomerId(customerId);}

    @PostMapping("/makePaymentForIndividual{saveCreditCard}")
    Result makePaymentForIndividualCustomer(@RequestBody @Valid IndividualCustomerPaymentModel individualCustomerPaymentModel,
                                            @RequestParam @PathVariable(value = "savedCreditCard") EnumSaveCreditCard enumSaveCreditCard) throws BusinessException{
        return this.paymentService.makePaymentForIndividualCustomer(individualCustomerPaymentModel,enumSaveCreditCard);}

    @PostMapping("/makePaymentForCorporate{saveCreditCard}")
    Result makePaymentForCorporateCustomer(@RequestBody @Valid CorporateCustomerPaymentModel corporateCustomerPaymentModel ,
                                           @RequestParam @PathVariable(value = "savedCreditCard") EnumSaveCreditCard enumSaveCreditCard) throws BusinessException{
        return this.paymentService.makePaymentForCorporateCustomer(corporateCustomerPaymentModel,enumSaveCreditCard);}

    @GetMapping("/getById")
    DataResult<PaymentDto> getById(@RequestParam int id) throws BusinessException{return this.paymentService.getById(id);}

    @DeleteMapping("/delete")
    Result delete(@RequestBody DeletePaymentRequest deletePaymentRequest) throws BusinessException{return this.paymentService.delete(deletePaymentRequest);}
}
