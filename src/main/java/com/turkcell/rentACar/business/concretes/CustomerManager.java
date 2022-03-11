package com.turkcell.rentACar.business.concretes;

import com.turkcell.rentACar.business.abstracts.CustomerService;
import com.turkcell.rentACar.business.dtos.customerDtos.CustomerDto;
import com.turkcell.rentACar.business.dtos.customerDtos.CustomerListDto;
import com.turkcell.rentACar.business.requests.creates.CreateCustomerRequest;
import com.turkcell.rentACar.business.requests.deletes.DeleteCustomerRequest;
import com.turkcell.rentACar.business.requests.updates.UpdateCustomerRequest;
import com.turkcell.rentACar.core.utilities.exceptions.BusinessException;
import com.turkcell.rentACar.core.utilities.mapping.ModelMapperService;
import com.turkcell.rentACar.core.utilities.results.DataResult;
import com.turkcell.rentACar.core.utilities.results.Result;
import com.turkcell.rentACar.core.utilities.results.SuccessDataResult;
import com.turkcell.rentACar.core.utilities.results.SuccessResult;
import com.turkcell.rentACar.dataAccess.abstracts.CustomerDao;
import com.turkcell.rentACar.entities.concretes.Customer;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class CustomerManager implements CustomerService {
    private CustomerDao customerDao;
    private ModelMapperService modelMapperService;

    public CustomerManager(CustomerDao customerDao, ModelMapperService modelMapperService) {
        this.customerDao = customerDao;
        this.modelMapperService = modelMapperService;
    }

    @Override
    public DataResult<List<CustomerListDto>> getAll() {
        List<Customer> result=this.customerDao.findAll();
        List<CustomerListDto> response = result.stream().map(customer ->this.modelMapperService.forDto().map(customer,CustomerListDto.class)).collect(Collectors.toList());
        return new SuccessDataResult<List<CustomerListDto>>(response,"Customers Listed Successfully");
    }

    @Override
    public Result add(CreateCustomerRequest createCustomerRequest) throws BusinessException {
        Customer customer = this.modelMapperService.forRequest().map(createCustomerRequest,Customer.class);
        this.customerDao.save(customer);
        return new SuccessResult("Customer Added Successfully");
    }

    @Override
    public DataResult<CustomerDto> getById(int id) throws BusinessException {
        Customer customer = this.customerDao.getById(id);
        CustomerDto customerDto = this.modelMapperService.forDto().map(customer,CustomerDto.class);
        return new SuccessDataResult<CustomerDto>(customerDto,"Customer Listed Successfully");
    }

    @Override
    public Result update(UpdateCustomerRequest updateCustomerRequest) throws BusinessException {
        Customer customer = this.modelMapperService.forRequest().map(updateCustomerRequest,Customer.class);
        this.customerDao.save(customer);
        return new SuccessResult("Customer Updated Successfully");
    }

    @Override
    public Result delete(DeleteCustomerRequest deleteCustomerRequest) throws BusinessException {
        Customer customer = this.modelMapperService.forRequest().map(deleteCustomerRequest,Customer.class);
        this.customerDao.delete(customer);
        return new SuccessResult("Customer Deleted Successfully");
    }

    @Override
    public Customer getCustomerByCustomerId(int id) {
        return this.customerDao.getById(id);
    }
}
