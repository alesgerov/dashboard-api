package com.example.dashboardapi.validator;

import com.example.dashboardapi.service.CompanyService;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class CompanyNameValidator  implements ConstraintValidator<UniqueCompanyName, String> {

    private final CompanyService service;

    public CompanyNameValidator(CompanyService service) {
        this.service = service;
    }


    @Override
    public boolean isValid(String s, ConstraintValidatorContext constraintValidatorContext) {
        return service.getCompanyByName(s).isEmpty();
    }
}
