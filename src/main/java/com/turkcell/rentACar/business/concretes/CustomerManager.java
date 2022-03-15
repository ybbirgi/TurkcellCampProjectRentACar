package com.turkcell.rentACar.business.concretes;

import com.turkcell.rentACar.business.abstracts.CustomerService;
import com.turkcell.rentACar.core.utilities.exceptions.EmailAlreadyUsedException;
import com.turkcell.rentACar.core.utilities.exceptions.NotFoundException;
import com.turkcell.rentACar.dataAccess.abstracts.CustomerDao;
import com.turkcell.rentACar.entities.concretes.Customer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CustomerManager implements CustomerService {

    private CustomerDao customerDao;
    @Autowired
    public CustomerManager(CustomerDao customerDao) {
        this.customerDao = customerDao;
    }

    @Override
    public Customer getCustomerByCustomerId(int id) {
        return this.customerDao.getById(id);
    }

    @Override
    public void checkIfCustomerExistsByEmail(String email) throws EmailAlreadyUsedException {
        if(this.customerDao.existsByEmail(email))
            throw new EmailAlreadyUsedException("This Email Is Already In Use");
    }

    @Override
    public void checkIfCustomerExistsById(int customerId) throws NotFoundException {
        if(!this.customerDao.existsByCustomerId(customerId))
            throw new NotFoundException("Customer Not Found");
    }


}
