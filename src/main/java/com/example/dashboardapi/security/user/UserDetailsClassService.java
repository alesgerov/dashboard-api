package com.example.dashboardapi.security.user;


import com.example.dashboardapi.controller.utils.ShortcutUtils;
import com.example.dashboardapi.entity.UserClass;
import com.example.dashboardapi.service.UserService;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class UserDetailsClassService implements UserDetailsService {

    private final UserService userService;

    public UserDetailsClassService(UserService userService) {
        this.userService = userService;
    }

    @Override
    public UserDetails loadUserByUsername(String s) throws UsernameNotFoundException {

        Optional<UserClass> userClass=userService.getUserByEmail(s);
        if (userClass.isPresent()){
            UserClass user =userClass.get();
            return new UserDetailsClass(user);
        }else {
            throw new UsernameNotFoundException("Email not found");
        }
    }


}
