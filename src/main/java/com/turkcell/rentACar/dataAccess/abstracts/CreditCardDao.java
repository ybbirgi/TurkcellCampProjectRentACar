package com.turkcell.rentACar.dataAccess.abstracts;

import com.turkcell.rentACar.entities.concretes.CreditCard;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CreditCardDao extends JpaRepository<CreditCard,Integer> {
    List<CreditCard> getAllByCustomer_CustomerId(int id);
}
