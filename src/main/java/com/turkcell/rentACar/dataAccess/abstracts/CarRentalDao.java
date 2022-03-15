package com.turkcell.rentACar.dataAccess.abstracts;

import com.turkcell.rentACar.entities.concretes.CarRental;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CarRentalDao extends JpaRepository<CarRental,Integer> {
    List<CarRental> getAllByCar_CarId(Integer id);
    boolean existsByRentalId(int id);
}
