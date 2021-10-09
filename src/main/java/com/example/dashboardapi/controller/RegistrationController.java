package com.example.dashboardapi.controller;


import com.example.dashboardapi.controller.utils.ShortcutUtils;
import com.example.dashboardapi.entity.UserClass;
import com.example.dashboardapi.form.EmployeeForm;
import com.example.dashboardapi.form.RegistrationForm;
import com.example.dashboardapi.service.EmployeeService;
import com.example.dashboardapi.service.UserService;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;


@RestController
public class RegistrationController extends ApiControllerV1 {

    public RegistrationController(UserService userService, EmployeeService employeeService, ShortcutUtils utils) {
        this.userService = userService;
        this.employeeService = employeeService;
        this.utils = utils;
    }

    private final UserService userService;
    private final EmployeeService employeeService;
    private final ShortcutUtils utils;

    @PostMapping(value = {"/registration/","/registration"})
    public ResponseEntity<?> registerUser(@Valid @RequestBody RegistrationForm user,
                                                  BindingResult result){
        if (ShortcutUtils.isLogged()){
            return ResponseEntity.noContent().build();
        }
        if (result.hasErrors()){
            return ResponseEntity.status(409).body(utils.getErrorForm(result.getAllErrors().get(0).getDefaultMessage(),409,user));
        }
        return  ResponseEntity.status(201).body(utils.createdForm(userService.saveUser(user)));
    }



    @PostMapping(value = {"/add/user","/add/user/"})
    public ResponseEntity<?> addEmployee(@Valid @RequestBody RequestForm form,
                                         BindingResult result){
//
        if (result.hasErrors()){
            return  ResponseEntity.status(409).body(utils.getErrorForm(result.getAllErrors().get(0).getDefaultMessage(),409,form));
        }
        EmployeeForm savedEmp=employeeService.saveEmployee(form.getEmployeeForm());
        UserClass userClass=userService.saveUser(form.getRegistrationForm());
        if (savedEmp!=null && userClass!=null){
            savedEmp.setEmail(userClass.getEmail());
            return ResponseEntity.status(201).body(utils.createdForm(savedEmp));
        }
        return ResponseEntity.status(409).body(utils.getErrorForm("Bad inputs",409,form));
    }

}

@Data
@AllArgsConstructor
@NoArgsConstructor
class RequestForm{
    @Valid
    private RegistrationForm registrationForm;
    @Valid
    private EmployeeForm employeeForm;
}
