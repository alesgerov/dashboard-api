package com.example.dashboardapi.form;

import com.example.dashboardapi.validator.UniqueProjectName;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@Data
public class ProjectFormName {
    @NotBlank
    private String company;
    @NotBlank
    @UniqueProjectName
    private String name;
    @NotBlank
    @Size(max = 3)
    private String shortName;
}
