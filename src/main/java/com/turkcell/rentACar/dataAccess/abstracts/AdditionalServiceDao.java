package com.turkcell.rentACar.dataAccess.abstracts;

import com.turkcell.rentACar.entities.concretes.AdditionalService;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AdditionalServiceDao extends JpaRepository<AdditionalService,Integer> {
    boolean existsByServiceName(String name);
    boolean existsByServiceId(int id);
    boolean existsByServiceNameAndDailyPrice(String name,double dailyPrice);
}
