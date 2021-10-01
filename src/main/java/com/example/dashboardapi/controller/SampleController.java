package com.example.dashboardapi.controller;

import com.example.dashboardapi.controller.utils.ShortcutUtils;
import org.springframework.http.ResponseEntity;
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
