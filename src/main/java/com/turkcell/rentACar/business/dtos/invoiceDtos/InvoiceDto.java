package com.turkcell.rentACar.business.dtos.invoiceDtos;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class InvoiceDto {
    private int invoiceNo;

    private Integer rentDayValue;

    private Double totalPayment;

    private int rentalId;

    private int customerId;

    private LocalDate rentalDate;

    private LocalDate rentalReturnDate;
}
