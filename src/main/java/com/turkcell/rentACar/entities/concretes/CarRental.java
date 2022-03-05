package com.turkcell.rentACar.entities.concretes;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.util.Pair;

import javax.persistence.*;
import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "car_rentals")
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

}
