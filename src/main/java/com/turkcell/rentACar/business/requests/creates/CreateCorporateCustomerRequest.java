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
public class CreateCorporateCustomerRequest {
    @Email
    @NotNull
    private String email;

    @NotNull
    private String password;

    @NotNull
    @Size(min=2,max=50)
    private String companyName;

    @Pattern(regexp="^[0-9]{10}",message="length must be 11 and all digits have to be an integer")
    private String taxNumber;
}
