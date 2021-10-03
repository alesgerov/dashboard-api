package com.example.dashboardapi.form;

import com.example.dashboardapi.entity.Company;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@Data
public class ProjectForm {
    @NotBlank
    private long company_id;
    @NotBlank
    private String name;
    @NotBlank
    @Size(max = 3)
    private String shortName;
}
