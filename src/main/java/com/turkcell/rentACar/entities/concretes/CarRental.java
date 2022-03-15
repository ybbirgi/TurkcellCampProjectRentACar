package com.turkcell.rentACar.entities.concretes;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.List;
import java.util.Set;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "car_rentals")
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler", "Lazy"})
public class CarRental {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "rental_id")
    private int rentalId;

    @Column(name = "rent_description")
    private String rentDescription;

    @Column(name = "car_rental_date")
    private LocalDate rentalDate;

    @Column(name = "car_return_date")
    private LocalDate rentalReturnDate;

    @ManyToOne
    @JoinColumn(name="car_id")
    private Car car;

    @ManyToOne
    @JoinColumn(name="city_id")
    private City returnCity;

    @OneToOne(mappedBy = "carRental", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private Invoice invoice;

    @ManyToOne
    @JoinColumn(name = "customer_id")
    private Customer customer;
}
