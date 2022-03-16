package com.turkcell.rentACar.entities.concretes;

import com.turkcell.rentACar.core.entities.User;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "customers")
//todo inheritance configuration
@PrimaryKeyJoinColumn(name = "customer_id",referencedColumnName = "user_id")
public class Customer extends User {

    @Column(name = "customer_id",insertable = false,updatable = false)
    private int customerId;

    //todo addfield = dateRegistered;

    @OneToMany(mappedBy = "customer",cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    List<Invoice> invoiceList;

    //todo addfield = List<Carrental>
}
