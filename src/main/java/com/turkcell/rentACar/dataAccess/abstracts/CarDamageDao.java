package com.turkcell.rentACar.dataAccess.abstracts;

import com.turkcell.rentACar.entities.concretes.CarDamage;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CarDamageDao extends JpaRepository<CarDamage,Integer> {
}
