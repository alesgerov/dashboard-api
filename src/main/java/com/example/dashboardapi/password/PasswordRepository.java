package com.example.dashboardapi.password;

public interface PasswordRepository {
    String hashPassword(String rawPassword);

    boolean checkPassword(String hashedPassword, String rawPassword);
}
