package com.example.dashboardapi.form;

import com.example.dashboardapi.validator.EqualFields;
import com.example.dashboardapi.validator.UniqueEmail;
import lombok.Data;

import javax.validation.constraints.NotBlank;

@Data
@EqualFields(field1 = "password", field2 = "confirmPassword")
public class RegistrationForm {
    @NotBlank
    @UniqueEmail
    private String email;
    @NotBlank
    private String password;
    @NotBlank
    private String confirmPassword;
}
