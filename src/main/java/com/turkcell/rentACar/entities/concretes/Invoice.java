package com.turkcell.rentACar.entities.concretes;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.List;

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

    @Column(name = "invoice_creation_date")
    private LocalDate invoiceDate;

    @Column(name = "rent_start_date")
    private LocalDate rentDate;

    @Column(name = "rent_end_date")
    private LocalDate rentEndDate;

    @Column(name = "rent_day_value")
    private Integer rentDayValue;

    @ManyToOne
    @JoinColumn(name = "rental_id")
    private CarRental carRental;

    @ManyToOne
    @JoinColumn(name = "customer_id")
    private Customer customer;

    @Column(name = "total_payment")
    private Double totalPayment;

    @OneToOne(mappedBy = "invoice")
    private Payment payment;
}
