package com.example.dashboardapi.controller;


import com.example.dashboardapi.controller.utils.ShortcutUtils;
import com.example.dashboardapi.entity.Company;
import com.example.dashboardapi.form.CompanyForm;
import com.example.dashboardapi.service.CompanyService;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Optional;

@RestController
public class CompanyController extends ApiControllerV1 {

    private final CompanyService companyService;
    private final ShortcutUtils shortcutUtils;

    public CompanyController(CompanyService companyService, ShortcutUtils shortcutUtils) {
        this.companyService = companyService;
        this.shortcutUtils = shortcutUtils;
    }

    @GetMapping(value = {"/companies/"})
    public ResponseEntity<?> getCompanies() {
        return ResponseEntity.ok().body(shortcutUtils.successForm(companyService.getAllCompanies()));
    }

    @GetMapping(value = {"/companies/{id}", "/companies/{id}/"})
    public ResponseEntity<?> getCompanyById(@PathVariable("id") long id) {
        Optional<Company> optionalCompany = companyService.getCompanyById(id);
        if (optionalCompany.isPresent()) {
            return ResponseEntity.ok().body(shortcutUtils.successForm(optionalCompany.get()));
        }
        return ResponseEntity.status(409).body(shortcutUtils.getErrorForm("This id not found", 409));
    }

    @PostMapping(value = {"/add/company", "/add/company/"})
    public ResponseEntity<?> addCompany(@Valid @RequestBody CompanyForm form, BindingResult result) {
        if (result.hasErrors()) {
            return ResponseEntity.status(409).body(shortcutUtils.getErrorForm(result.getAllErrors().get(0).getDefaultMessage(), 409, form));
        }
        CompanyForm companyForm = companyService.saveCompany(form);
        if (companyForm == null) {
            return ResponseEntity.status(409).body(shortcutUtils.getErrorForm("Bad inputs", 409, form));
        }

        return ResponseEntity.status(201).body(shortcutUtils.createdForm(companyForm));

    }

    @DeleteMapping(value = {"/delete/company/{id}", "/delete/company/{id}/"})
    public ResponseEntity<?> deleteCompany(@PathVariable("id") long id) {
        return companyService.deleteCompany(id);
    }


    @GetMapping(value = {"/companies"})
    public ResponseEntity<?> filterCompany(@RequestParam(required = false, name = "name") String name) {
        return ResponseEntity.ok().body(shortcutUtils.successForm(companyService.getAllCompaniesByName(name)));
    }


    @PutMapping(value = {"/update/company/{id}", "/update/company/{id}/"})
    public ResponseEntity<?> updateCompany(@PathVariable("id") long id,
                                           @Valid @RequestBody CompanyForm form, BindingResult result) {
        Optional<Company> optionalCompany = companyService.getCompanyById(id);
        if (result.hasErrors()) {
            return ResponseEntity.status(409).body(shortcutUtils.getErrorForm(result.getAllErrors().get(0).getDefaultMessage(), 409, form));
        } else if (optionalCompany.isEmpty()) {
            return ResponseEntity.status(409).body(shortcutUtils.getErrorForm("This company does not exists.", 409, "Company with id " + id + " does not exists"));
        }
        CompanyForm updated = companyService.updateCompany(form, optionalCompany.get());
        if (updated == null) {
            return ResponseEntity.status(409).body(shortcutUtils.getErrorForm("Bad inputs", 409, form));
        }
        return ResponseEntity.status(200).body(shortcutUtils.successForm(updated));
    }


}
