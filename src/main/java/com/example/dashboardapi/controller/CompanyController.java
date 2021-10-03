package com.example.dashboardapi.controller;


import com.example.dashboardapi.controller.utils.ShortcutUtils;
import com.example.dashboardapi.entity.Company;
import com.example.dashboardapi.form.CompanyForm;
import com.example.dashboardapi.service.CompanyService;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.Optional;

@RestController
public class CompanyController extends ApiControllerV1 {

    private final CompanyService companyService;
    private final ShortcutUtils shortcutUtils;

    public CompanyController(CompanyService companyService, ShortcutUtils shortcutUtils) {
        this.companyService = companyService;
        this.shortcutUtils = shortcutUtils;
    }

    @GetMapping(value = {"/companies/","/companies"})
    public ResponseEntity<List<Company>> getCompanies(){
        return ResponseEntity.ok().body(companyService.getAllCompanies());
    }

    @GetMapping(value = {"/companies/{id}","/companies/{id}/"})
    public ResponseEntity<?> getCompanyById(@PathVariable("id") long id){
        Optional<Company> optionalCompany=companyService.getCompanyById(id);
        if(optionalCompany.isPresent()){
            return ResponseEntity.ok().body(optionalCompany.get());
        }
        return ResponseEntity.status(409).body(shortcutUtils.getErrorForm("This id not found",409));
    }

    @PostMapping(value = {"/add/company","/add/company/"})
    public ResponseEntity<?> addCompany(@Valid @RequestBody CompanyForm form, BindingResult result){
        if (result.hasErrors()){
            return ResponseEntity.status(409).body(shortcutUtils.getErrorForm(result.getAllErrors().get(0).getDefaultMessage(), 409));
        }
        CompanyForm companyForm=companyService.saveCompany(form);
        if (companyForm==null){
            return ResponseEntity.status(409).body(shortcutUtils.getErrorForm("Bad inputs", 409));
        }

        return ResponseEntity.status(201).body(companyForm);

    }

    @DeleteMapping(value = {"/delete/company/{id}","/delete/company/{id}/"})
    public ResponseEntity<?> deleteCompany(@PathVariable("id") long id){
        return companyService.deleteCompany(id);
    }



    @GetMapping(value = {"/companies/", "/companies"})
    public ResponseEntity<?> filterCompany(@RequestParam(required = false, name = "name") String name) {
        return ResponseEntity.ok().body(companyService.getAllCompaniesByName(name));
    }



    @PutMapping(value = {"/update/company/{id}","/update/company/{id}/"})
    public ResponseEntity<?> updateCompany(@PathVariable("id") long id,
                                           @Valid @RequestBody CompanyForm form,BindingResult result){
        Optional<Company> optionalCompany=companyService.getCompanyById(id);
        if (result.hasErrors()) {
            return ResponseEntity.status(409).body(shortcutUtils.getErrorForm(result.getAllErrors().get(0).getDefaultMessage(), 409));
        }else  if (optionalCompany.isEmpty()){
            return ResponseEntity.status(409).body(shortcutUtils.getErrorForm("This company does not exists.",409));
        }
        CompanyForm updated=companyService.updateCompany(form,optionalCompany.get());
        if (updated==null){
            return ResponseEntity.status(409).body(shortcutUtils.getErrorForm("Bad inputs", 409));
        }
        return ResponseEntity.status(200).body(updated);
    }


}
