package com.turkcell.rentACar.business.dtos.paymentDtos;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PaymentDto {
    private int paymentId;
    private double totalAmount;

    private int rentalId;
    private int invoiceId;
    private int customerId;
}
