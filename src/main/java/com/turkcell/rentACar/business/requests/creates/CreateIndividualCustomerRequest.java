package com.turkcell.rentACar.business.requests.creates;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
@Data
@AllArgsConstructor
@NoArgsConstructor
public class CreateIndividualCustomerRequest {
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

    @Pattern(regexp="^[0-9]{11}",message="length must be 11 and all digits have to be an integer")
    private String nationalIdentity;
}
