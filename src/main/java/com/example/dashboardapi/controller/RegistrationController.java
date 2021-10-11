package com.example.dashboardapi.controller;


import com.example.dashboardapi.controller.utils.ShortcutUtils;
import com.example.dashboardapi.entity.Employee;
import com.example.dashboardapi.entity.UserClass;
import com.example.dashboardapi.form.EmployeeFormName;
import com.example.dashboardapi.form.RegistrationForm;
import com.example.dashboardapi.service.EmployeeService;
import com.example.dashboardapi.service.UserService;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.Optional;


@RestController
public class RegistrationController extends ApiControllerV1 {

    private final UserService userService;
    private final EmployeeService employeeService;
    private final ShortcutUtils utils;

    public RegistrationController(UserService userService, EmployeeService employeeService, ShortcutUtils utils) {
        this.userService = userService;
        this.employeeService = employeeService;
        this.utils = utils;
    }

    @PostMapping(value = {"/registration/", "/registration"})
    public ResponseEntity<?> registerUser(@Valid @RequestBody RegistrationForm user,
                                          BindingResult result) {
        if (ShortcutUtils.isLogged()) {
            return ResponseEntity.noContent().build();
        }
        if (result.hasErrors()) {
            return ResponseEntity.status(409).body(utils.getErrorForm(result.getAllErrors().get(0).getDefaultMessage(), 409, user));
        }
        return ResponseEntity.status(201).body(utils.createdForm(userService.saveUser(user)));
    }


    @PostMapping(value = {"/add/user", "/add/user/"})
    public ResponseEntity<?> addEmployee(@Valid @RequestBody RequestForm form,
                                         BindingResult result) {
//
        if (result.hasErrors()) {
            return ResponseEntity.status(409).body(utils.getErrorForm(result.getAllErrors().get(0).getDefaultMessage(), 409, form));
        }
        UserClass userClass = userService.saveUser(form.getRegistrationForm());
        if (userClass != null) {
            form.getEmployeeForm().setEmail(userClass.getEmail());
            EmployeeFormName savedEmp = employeeService.saveEmployee(form.getEmployeeForm());
            if (savedEmp != null) {
                return ResponseEntity.status(201).body(utils.createdForm(savedEmp));
            }
        }
        return ResponseEntity.status(409).body(utils.getErrorForm("Bad inputs", 409, form));
    }

    @PutMapping(value = {"/update/user/{id}", "/update/user/{id}/"})
    public ResponseEntity<?> updateEmployee(@PathVariable("id") long id,
                                            @Valid @RequestBody RequestForm form,
                                            BindingResult result) {
        Optional<Employee> optionalEmployee = employeeService.getEmployeeById(id);

        if (optionalEmployee.isEmpty()) {
            return ResponseEntity.status(409).body(utils.getErrorForm("This project does not exists.", 409, form));
        }
        UserClass user = optionalEmployee.get().getUserId();
        System.out.println(user.getEmail());
        if (result.hasErrors()) {
            if (!user.getEmail().equals(form.getRegistrationForm().getEmail())) {
                return ResponseEntity.status(409).body(utils.getErrorForm(result.getAllErrors().get(0).getDefaultMessage(), 409, form));
            } else if (user.getEmail().equals(form.getRegistrationForm().getEmail()) && result.getErrorCount() > 1) {
                List<ObjectError> errors = utils.reNewErrors(result, "registrationForm.email");
                return ResponseEntity.status(409).body(utils.getErrorForm(errors.get(0).getDefaultMessage(), 409, form));
            }
        }
        UserClass userClass = userService.updateUser(user, form.getRegistrationForm());
        System.out.println(userClass);
        if (userClass != null) {
            EmployeeFormName updated = employeeService.updateEmployee(form.getEmployeeForm(), optionalEmployee.get());
            System.out.println(updated);
            if (updated != null) {
                return ResponseEntity.status(200).body(utils.successForm(updated));
            }
        }
        return ResponseEntity.status(409).body(utils.getErrorForm("Bad inputs", 409, form));
    }


}

@Data
@AllArgsConstructor
@NoArgsConstructor
class RequestForm {
    @Valid
    private RegistrationForm registrationForm;
    @Valid
    private EmployeeFormName employeeForm;
}
