package com.example.dashboardapi.controller;


import com.example.dashboardapi.controller.utils.ShortcutUtils;
import com.example.dashboardapi.entity.UserClass;
import com.example.dashboardapi.error.form.ErrorForm;
import com.example.dashboardapi.form.RegistrationForm;
import com.example.dashboardapi.service.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.net.URI;
//
@RestController
public class RegistrationController extends ApiControllerV1 {

    public RegistrationController(UserService userService) {
        this.userService = userService;
    }

    private final UserService userService;

    @PostMapping(value = {"/registration/","/registration"})
    public ResponseEntity registerUser(@Valid @RequestBody RegistrationForm user,
                                                  BindingResult result){
        URI uri= URI.create("/api/v1/hello");
        if (ShortcutUtils.isLogged()){
            return ResponseEntity.noContent().build();
        }
        if (result.hasErrors()){
            ErrorForm errorForm=new ErrorForm();
            errorForm.setMessage(result.getAllErrors().get(0).getDefaultMessage());
            errorForm.setStatus(409);
            return ResponseEntity.status(409).body(errorForm);
        }
        return  ResponseEntity.created(uri).body(userService.saveUser(user));
    }

}
