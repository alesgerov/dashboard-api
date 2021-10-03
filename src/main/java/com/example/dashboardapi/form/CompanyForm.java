package com.example.dashboardapi.form;

import com.example.dashboardapi.validator.UniqueCompanyName;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@Data
public class CompanyForm {
    @NotBlank
    @UniqueCompanyName
    private String name;
    @NotBlank
    @Size(max = 3)
    private String shortName;
    @NotBlank
    private String email;
    private String logo;
    private String noteText;
}

