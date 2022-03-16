package com.turkcell.rentACar.entities.concretes;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "cars")
public class Car {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name= "car_id")
    private int carId;

    @Column(name= "car_dailyprice")
    private double carDailyPrice;

    @Column(name= "car_modelyear")
    private int carModelYear;

    @Column(name= "car_description")
    private String carDescription;

    @Column(name= "car_rental_status")
    private boolean carRentalStatus;

    @Column(name= "car_maintenance_status")
    private boolean carMaintenanceStatus;

    @Column(name = "car_kilometer")
    private int carKilometer;

    @ManyToOne
    @JoinColumn(name = "brand_id")
    private Brand brand;

    @ManyToOne
    @JoinColumn(name = "color_id")
    private Color color;

    @OneToMany(mappedBy = "car", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<CarMaintenance> carMaintenances;

    @OneToMany(mappedBy = "car", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<CarRental> carRentals;

    @ManyToOne
    @JoinColumn(name = "city_id")
    private City currentCity;

    @OneToMany(mappedBy = "car")
    private List<CarDamage> carDamages;


}
