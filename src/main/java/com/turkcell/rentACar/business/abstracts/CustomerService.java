package com.turkcell.rentACar.business.abstracts;

import com.turkcell.rentACar.core.utilities.exceptions.EmailAlreadyUsedException;
import com.turkcell.rentACar.core.utilities.exceptions.NotFoundException;
import com.turkcell.rentACar.entities.concretes.Customer;

public interface CustomerService {
    Customer getCustomerByCustomerId(int id);
    void checkIfCustomerExistsByEmail(String email) throws EmailAlreadyUsedException;
    void checkIfCustomerExistsById(int customerId) throws NotFoundException;
}
