package com.example.dashboardapi.form;

import com.example.dashboardapi.entity.Company;
import com.example.dashboardapi.entity.Project;
import lombok.Data;

@Data
public class UtilForm {
    private Company company;
    private Project project;
}
