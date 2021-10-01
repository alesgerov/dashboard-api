package com.example.dashboardapi.password;


import org.mindrot.jbcrypt.BCrypt;
import org.springframework.stereotype.Component;

@Component
public class PasswordService implements PasswordRepository{

    @Override
    public String hashPassword(String rawPassword) {
        return BCrypt.hashpw(rawPassword,BCrypt.gensalt());
    }

    @Override
    public boolean checkPassword(String hashedPassword, String rawPassword) {
        return BCrypt.checkpw(rawPassword,hashedPassword);
    }
}
