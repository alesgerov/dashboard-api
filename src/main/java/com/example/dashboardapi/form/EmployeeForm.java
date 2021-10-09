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
    @NotBlank(message = "Name cannot be null")
    private String name;
    @NotBlank(message = "Surname cannot be null")
    private String surname;
    @NotBlank(message = "Father name cannot be null")
    private String fatherName;
    @NotBlank(message = "Phone field cannot be null")
    private String phone;
    private String noteText;
}
