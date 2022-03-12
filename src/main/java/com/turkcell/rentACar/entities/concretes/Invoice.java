package com.turkcell.rentACar.entities.concretes;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "invoices")
public class Invoice {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "invoice_no")
    private int invoiceNo;

    @Column(name = "rent_day_value")
    private Integer rentDayValue;

    @OneToOne
    @JoinColumn(name = "rental_id")
    private CarRental carRental;

    @ManyToOne
    @JoinColumn(name = "customer_id")
    private Customer customer;
}
