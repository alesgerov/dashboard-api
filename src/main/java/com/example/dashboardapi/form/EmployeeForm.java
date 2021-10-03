package com.example.dashboardapi.form;

import com.example.dashboardapi.entity.UserClass;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Data
public class EmployeeForm {
    @NotNull
    private long company_id;
    @NotNull
    private long project_id;
    @JsonIgnore
    private UserClass userClass;
    @NotBlank
    private String name;
    @NotBlank
    private String surname;
    @NotBlank
    private String fatherName;
    @NotBlank
    private String phone;
    private String noteText;
}
