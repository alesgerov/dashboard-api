package com.example.dashboardapi.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping
public class SampleController {
    @GetMapping(value = {"/hello","/hello/"})
    public String getHello(){
        return "Hello";
    }
}
