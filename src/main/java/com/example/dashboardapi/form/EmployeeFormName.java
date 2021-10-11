package com.example.dashboardapi.form;

import lombok.Data;
import lombok.ToString;

import javax.validation.constraints.NotBlank;

@Data
@ToString
public class EmployeeFormName {
    @NotBlank
    private String company;
    @NotBlank
    private String project;
    @NotBlank(message = "Name cannot be null")
    private String name;
    @NotBlank(message = "Surname cannot be null")
    private String surname;
    @NotBlank(message = "Father name cannot be null")
    private String fatherName;
    @NotBlank(message = "Phone field cannot be null")
    private String phone;
    private String noteText;
    private String email;
}
