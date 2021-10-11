package com.example.dashboardapi.service;


import com.example.dashboardapi.controller.utils.ShortcutUtils;
import com.example.dashboardapi.entity.Employee;
import com.example.dashboardapi.form.EmployeeFormName;
import com.example.dashboardapi.form.ResponseForm;
import com.example.dashboardapi.form.UtilForm;
import com.example.dashboardapi.repository.EmployeeRepository;
import org.apache.commons.lang3.StringUtils;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class EmployeeService {
    private final EmployeeRepository employeeRepository;
    private final ShortcutUtils utils;
    private final UserService userService;

    public EmployeeService(EmployeeRepository employeeRepository, ShortcutUtils utils, UserService userService) {
        this.employeeRepository = employeeRepository;
        this.utils = utils;
        this.userService = userService;
    }


    public EmployeeFormName saveUtils(EmployeeFormName form, Employee employee) {
        if (form == null) {
            return null;
        }
        UtilForm utilForm = utils.getOptionals(form.getCompany(), form.getProject());
        if (utilForm == null) {
            return null;
        }

//        Optional<UserClass> userClassOptional = userService.getUserByEmail(form.getEmail());
//        if (userClassOptional.isEmpty()) {
//            return null;
//        }
        employee.setUserId(employee.getUserId());
        employee.setCompany(utilForm.getCompany());
        employee.setProject(utilForm.getProject());
        employee.setName(form.getName());
        employee.setSurname(form.getSurname());
        employee.setFatherName(form.getFatherName());
        employee.setPhone(form.getPhone());
        employee.setNoteText(form.getNoteText());
        employeeRepository.save(employee);
        return form;
    }

    public EmployeeFormName saveEmployee(EmployeeFormName form) {
        Employee employee = new Employee();
        return saveUtils(form, employee);
    }

    public EmployeeFormName updateEmployee(EmployeeFormName form, Employee employee) {
        return saveUtils(form, employee);
    }

    public List<Employee> getAllEmployees() {
        return employeeRepository.findAll();
    }

    public Optional<Employee> getEmployeeById(long id) {
        return employeeRepository.findById(id);
    }


    public List<Employee> getAllEmployeesByName(String name) {
        return employeeRepository.getEmployeesByName(StringUtils.capitalize(name));
    }

    public ResponseEntity<ResponseForm> deleteEmployee(long id) {
        ResponseForm form = new ResponseForm();
        if (getEmployeeById(id).isPresent()) {
            employeeRepository.deleteById(id);
            form.setMessage("Deleted");
            form.setStatus(200);
            form.setContent("Deleted");
            return ResponseEntity.ok(form);
        }
        form.setMessage("This Employee does not exists");
        form.setStatus(409);
        form.setContent("This Employee does not exists");
        return ResponseEntity.status(409).body(form);
    }

    public EmployeeFormName getEmployeeForm(Employee employee) {
        EmployeeFormName form = new EmployeeFormName();

        form.setEmail(employee.getUserId().getEmail());
        form.setCompany(employee.getCompany().getName());
        form.setProject(employee.getProject().getName());
        form.setName(employee.getName());
        form.setSurname(employee.getSurname());
        form.setFatherName(employee.getFatherName());
        form.setPhone(employee.getPhone());
        form.setNoteText(employee.getNoteText());

        return form;
    }

    public List<EmployeeFormName> mapperToEmployeeFormName(List<Employee> employees) {
        List<EmployeeFormName> result = new ArrayList<>();
        for (int i = 0; i < employees.size(); i++) {
            result.add(getEmployeeForm(employees.get(i)));
        }
        return result;
    }

}
