package com.example.dashboardapi.security.jwt;

import lombok.Data;

@Data
public class LoginViewModel {
    private String email;
    private String password;
}
