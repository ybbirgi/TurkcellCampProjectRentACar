package com.turkcell.rentACar.api.models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@AllArgsConstructor
@NoArgsConstructor
public class InvoiceDateModel {
    String firstDateString;

    String secondDateString;
}
