package com.example.dashboardapi.controller;

import com.example.dashboardapi.controller.utils.ShortcutUtils;
import com.example.dashboardapi.entity.Employee;
import com.example.dashboardapi.form.EmployeeForm;
import com.example.dashboardapi.service.EmployeeService;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.Optional;

@RestController
public class EmployeeController extends ApiControllerV1 {

    private final EmployeeService employeeService;
    private final ShortcutUtils shortcutUtils;

    public EmployeeController(EmployeeService employeeService, ShortcutUtils shortcutUtils) {
        this.employeeService = employeeService;
        this.shortcutUtils = shortcutUtils;
    }

    @GetMapping(value = {"/employees/"})
    public ResponseEntity<List<Employee>> getEmployees(){
        return ResponseEntity.ok().body(employeeService.getAllEmployees());
    }

    @DeleteMapping(value = {"/delete/employee/{id}","/delete/employee/{id}/"})
    public ResponseEntity<?> deleteEmployee(@PathVariable("id") long id){
        return employeeService.deleteEmployee(id);
    }


    @GetMapping(value = { "/employees"})
    public ResponseEntity<?> filterEmployee(@RequestParam(required = false, name = "name") String name) {
        return ResponseEntity.ok().body(employeeService.getAllEmployeesByName(name));
    }

    @GetMapping(value = {"/employees/{id}","/employees/{id}/"})
    public ResponseEntity<?> getEmployeeById(@PathVariable("id") long id){
        Optional<Employee> optionalEmployee=employeeService.getEmployeeById(id);
        if(optionalEmployee.isPresent()){
            return ResponseEntity.ok().body(optionalEmployee.get());
        }
        return ResponseEntity.status(409).body(shortcutUtils.getErrorForm("This id not found",409));
    }


    @PostMapping(value = {"/add/employee","/add/employee/"})
    public ResponseEntity<?> addEmployee(@Valid @RequestBody EmployeeForm form, BindingResult result){
        if (result.hasErrors()){
            return ResponseEntity.status(409).body(shortcutUtils.getErrorForm(result.getAllErrors().get(0).getDefaultMessage(), 409));
        }
        EmployeeForm employeeForm=employeeService.saveEmployee(form);
        if (employeeForm==null){
            return ResponseEntity.status(409).body(shortcutUtils.getErrorForm("Bad inputs", 409));
        }
        return ResponseEntity.status(201).body(employeeForm);

    }



    @PutMapping(value = {"/update/employee/{id}","/update/employee/{id}/"})
    public ResponseEntity<?> updateEmployee(@PathVariable("id") long id,
                                        @Valid @RequestBody EmployeeForm form, BindingResult result){
        Optional<Employee> optionalEmployee=employeeService.getEmployeeById(id);
        if (result.hasErrors()) {
            return ResponseEntity.status(409).body(shortcutUtils.getErrorForm(result.getAllErrors().get(0).getDefaultMessage(), 409));
        }else  if (optionalEmployee.isEmpty()){
            return ResponseEntity.status(409).body(shortcutUtils.getErrorForm("This employee does not exists.",409));
        }
        EmployeeForm updated=employeeService.updateEmployee(form,optionalEmployee.get());
        if (updated==null){
            return ResponseEntity.status(409).body(shortcutUtils.getErrorForm("Bad inputs", 409));
        }
        return ResponseEntity.status(200).body(updated);
    }







}
