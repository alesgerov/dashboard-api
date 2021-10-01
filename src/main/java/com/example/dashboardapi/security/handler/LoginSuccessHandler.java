package com.example.dashboardapi.security.handler;

import com.example.dashboardapi.entity.UserClass;
import com.example.dashboardapi.security.user.UserDetailsClass;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Component
public class LoginSuccessHandler implements AuthenticationSuccessHandler {
    @Override
    public void onAuthenticationSuccess(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Authentication authentication) throws IOException, ServletException {
        UserDetailsClass userPrincipal=(UserDetailsClass) authentication.getPrincipal();
        UserClass user=userPrincipal.getUser();
        httpServletRequest.getSession().setAttribute("user",user);
        httpServletResponse.sendRedirect("/hello");
    }
}
