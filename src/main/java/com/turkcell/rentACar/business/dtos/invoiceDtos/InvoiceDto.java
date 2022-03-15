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

    private LocalDate invoiceDate;

    private LocalDate rentDate;

    private LocalDate rentEndDate;

    private Integer rentDayValue;

    private int rentalId;

    private int customerId;

    private Double totalPayment;
}
