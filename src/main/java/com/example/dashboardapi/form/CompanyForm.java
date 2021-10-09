package com.example.dashboardapi.form;

import com.example.dashboardapi.validator.UniqueCompanyName;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@Data
public class CompanyForm {
    @NotBlank(message = "Name cannot be null.")
    @UniqueCompanyName(message = "Company name must me unique")
    private String name;
    @NotBlank(message = "Short name cannot be null.")
    @Size(max = 3)
    private String shortName;
    @NotBlank(message = "Email cannot be null")
    private String email;
    private String logo;
    private String noteText;
}

