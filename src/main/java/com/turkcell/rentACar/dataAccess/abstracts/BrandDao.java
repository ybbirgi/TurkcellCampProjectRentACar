package com.turkcell.rentACar.dataAccess.abstracts;

import com.turkcell.rentACar.entities.concretes.Brand;

import com.turkcell.rentACar.entities.concretes.Color;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BrandDao extends JpaRepository<Brand,Integer> {
    boolean existsByName(String name);
    List<Brand> getAllByBrandId(Integer id);
    List<Brand> getAllByBrandName(String name);
}
