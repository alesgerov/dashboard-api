package com.example.dashboardapi.controller;


import com.example.dashboardapi.service.UserService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping
public class SampleController {


    private final UserService userService;

    public SampleController(UserService userService) {
        this.userService = userService;
    }


    @GetMapping(value = {"/hello", "/hello/"})
    public String getHello() {
        return "Hello";
    }


}
