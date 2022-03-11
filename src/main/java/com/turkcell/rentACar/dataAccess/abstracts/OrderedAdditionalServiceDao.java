package com.turkcell.rentACar.dataAccess.abstracts;

import com.turkcell.rentACar.entities.concretes.OrderedAdditionalService;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface OrderedAdditionalServiceDao extends JpaRepository<OrderedAdditionalService,Integer> {
    List<OrderedAdditionalService> getAllByCarRental_RentalId(Integer id);
}
