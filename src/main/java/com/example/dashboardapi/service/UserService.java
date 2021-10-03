package com.example.dashboardapi.service;

import com.example.dashboardapi.entity.UserClass;
import com.example.dashboardapi.form.RegistrationForm;
import com.example.dashboardapi.password.PasswordService;
import com.example.dashboardapi.repository.UserRepository;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@Transactional
public class UserService {
    private final UserRepository userRepository;
    private final PasswordService passwordService;

    public UserService(UserRepository userRepository, PasswordService passwordService) {
        this.userRepository = userRepository;
        this.passwordService = passwordService;
    }

    public Optional<UserClass> getUserByEmail(String email) {
        return userRepository.findByEmail(email);
    }


    public UserClass saveUser(RegistrationForm form) {
        UserClass user=new UserClass();
        String  hashedPw=passwordService.hashPassword(form.getPassword());
        user.setEmail(form.getEmail());
        user.setPassword(hashedPw);
        return userRepository.save(user);
    }

    public boolean isRegistered(String email) {
        return getUserByEmail(email).isPresent();
    }



    public UserClass getCurrentUser(){
        Authentication authentication= SecurityContextHolder.getContext().getAuthentication();
        String username=authentication.getName();
        return getUserByEmail(username).get();
    }
}
