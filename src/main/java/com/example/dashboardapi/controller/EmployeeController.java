package com.example.dashboardapi.controller;

import com.example.dashboardapi.controller.utils.ShortcutUtils;
import com.example.dashboardapi.entity.Employee;
import com.example.dashboardapi.form.EmployeeFormName;
import com.example.dashboardapi.service.EmployeeService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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
    public ResponseEntity<?> getEmployees() {
        List<Employee> employees = employeeService.getAllEmployees();
        List<EmployeeFormName> form = employeeService.mapperToEmployeeFormName(employees);
        return ResponseEntity.ok().body(shortcutUtils.successForm(form));
    }

    @DeleteMapping(value = {"/delete/employee/{id}", "/delete/employee/{id}/"})
    public ResponseEntity<?> deleteEmployee(@PathVariable("id") long id) {
        return employeeService.deleteEmployee(id);
    }


    @GetMapping(value = {"/employees"})
    public ResponseEntity<?> filterEmployee(@RequestParam(required = false, name = "name") String name) {
        List<Employee> employees = employeeService.getAllEmployeesByName(name);
        List<EmployeeFormName> form = employeeService.mapperToEmployeeFormName(employees);
        return ResponseEntity.ok().body(shortcutUtils.successForm(form));
    }

    @GetMapping(value = {"/employees/{id}", "/employees/{id}/"})
    public ResponseEntity<?> getEmployeeById(@PathVariable("id") long id) {
        Optional<Employee> optionalEmployee = employeeService.getEmployeeById(id);
        if (optionalEmployee.isPresent()) {
            return ResponseEntity.ok().body(shortcutUtils.successForm(employeeService.getEmployeeForm(optionalEmployee.get())));
        }
        return ResponseEntity.status(409).body(shortcutUtils.getErrorForm("This employee not found", 409, "This employee not found"));
    }


}
