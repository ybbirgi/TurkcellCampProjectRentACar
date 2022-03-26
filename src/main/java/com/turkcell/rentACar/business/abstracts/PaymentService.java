package com.turkcell.rentACar.business.abstracts;

import com.turkcell.rentACar.api.models.CorporateCustomerPaymentModel;
import com.turkcell.rentACar.api.models.EnumSaveCreditCard;
import com.turkcell.rentACar.api.models.IndividualCustomerPaymentModel;
import com.turkcell.rentACar.business.dtos.paymentDtos.PaymentDto;
import com.turkcell.rentACar.business.dtos.paymentDtos.PaymentListDto;
import com.turkcell.rentACar.business.requests.deletes.DeletePaymentRequest;
import com.turkcell.rentACar.core.utilities.exceptions.BusinessException;
import com.turkcell.rentACar.core.utilities.results.DataResult;
import com.turkcell.rentACar.core.utilities.results.Result;

import java.util.List;

public interface PaymentService {
    DataResult<List<PaymentListDto>> getAll();

    DataResult<List<PaymentListDto>> getPaymentsByCustomerId(int customerId);

    Result makePaymentForIndividualCustomer(IndividualCustomerPaymentModel individualCustomerPaymentModel, EnumSaveCreditCard enumSaveCreditCard) throws BusinessException;

    Result makePaymentForCorporateCustomer(CorporateCustomerPaymentModel corporateCustomerPaymentModel, EnumSaveCreditCard enumSaveCreditCard) throws BusinessException;

    DataResult<PaymentDto> getById(int id) throws BusinessException;

    Result delete(DeletePaymentRequest deletePaymentRequest) throws BusinessException;

}
