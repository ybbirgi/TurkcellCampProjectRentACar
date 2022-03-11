package com.turkcell.rentACar.business.abstracts;

import com.turkcell.rentACar.business.dtos.customerDtos.CustomerDto;
import com.turkcell.rentACar.business.dtos.customerDtos.CustomerListDto;
import com.turkcell.rentACar.business.requests.creates.CreateCustomerRequest;
import com.turkcell.rentACar.business.requests.deletes.DeleteCustomerRequest;
import com.turkcell.rentACar.business.requests.updates.UpdateCustomerRequest;
import com.turkcell.rentACar.core.utilities.exceptions.BusinessException;
import com.turkcell.rentACar.core.utilities.results.DataResult;
import com.turkcell.rentACar.core.utilities.results.Result;
import com.turkcell.rentACar.entities.concretes.Customer;

import java.util.List;

public interface CustomerService {
    DataResult<List<CustomerListDto>> getAll();
    Result add(CreateCustomerRequest createCustomerRequest) throws BusinessException;
    DataResult<CustomerDto> getById(int id) throws BusinessException;
    Result update(UpdateCustomerRequest updateCustomerRequest) throws BusinessException;
    Result delete(DeleteCustomerRequest deleteCustomerRequest) throws BusinessException;
    Customer getCustomerByCustomerId(int id);
}
