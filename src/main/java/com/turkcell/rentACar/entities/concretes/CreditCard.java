package com.turkcell.rentACar.entities.concretes;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "credit_cards")
public class CreditCard {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int creditCardId;

    @Column(name = "card_holder")
    private String cardHolder;

    @Column(name = "card_no")
    private String CardNo;

    @Column(name = "expiration_month")
    private int expirationMonth;

    @Column(name = "expiration_year")
    private int expirationYear;

    @Column(name = "cvv")
    private int CVV;

    @ManyToOne
    @JoinColumn(name = "customer_id")
    private Customer customer;
}
