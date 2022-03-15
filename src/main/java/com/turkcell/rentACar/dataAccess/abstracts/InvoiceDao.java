package com.turkcell.rentACar.dataAccess.abstracts;

import com.turkcell.rentACar.entities.concretes.Invoice;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface InvoiceDao extends JpaRepository<Invoice,Integer> {
    List<Invoice> getAllByCustomer_CustomerId(int customerId);
    List<Invoice> findByInvoiceDateBetween(LocalDate firstDate,LocalDate secondDate);
    Invoice findByCarRental_RentalId(int rentalId);
}
