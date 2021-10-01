package com.example.dashboardapi.error.form;

import lombok.Data;

@Data
public class ErrorForm {
    private String message;
    private int status;
}
