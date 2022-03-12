package com.turkcell.rentACar.business.dtos.invoiceDtos;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@AllArgsConstructor
@NoArgsConstructor
public class InvoiceListDto {

    private int invoiceNo;

    private Integer rentDayValue;

    private Double totalPayment;

    private int rentalId;

    private int customerId;
}
