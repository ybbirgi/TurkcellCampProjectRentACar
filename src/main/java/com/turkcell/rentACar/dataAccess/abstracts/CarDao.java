package com.turkcell.rentACar.dataAccess.abstracts;

import com.turkcell.rentACar.entities.concretes.Car;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CarDao extends JpaRepository <Car,Integer>  {
    List<Car> getByCarDailyPriceLessThanEqual(Double carDailyPrice, Sort sort);
}
