package com.example.dashboardapi.form;

import lombok.Data;

import javax.validation.constraints.NotBlank;

@Data
public class CompanyForm {
    @NotBlank
    private String name;
    @NotBlank
    private String shortName;
    @NotBlank
    private String email;
    private String logo;
    private String noteText;
}

