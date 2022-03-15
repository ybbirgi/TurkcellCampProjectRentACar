package com.turkcell.rentACar.business.requests.updates;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import javax.validation.constraints.Size;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UpdateIndividualCustomerRequest {
    @NotNull
    @Positive
    private int customerId;

    @Email
    @NotNull
    private String email;

    @NotNull
    private String password;

    @NotNull
    @Size(min=2,max=20)
    private String firstName;

    @NotNull
    @Size(min=2,max=20)
    private String lastName;

    @NotNull
    @Size(min=2,max=20)
    private String nationalIdentity;
}
