package com.example.dashboardapi.validator;

import com.example.dashboardapi.service.CompanyService;
import org.springframework.stereotype.Component;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

@Component
public class CompanyNameValidator implements ConstraintValidator<UniqueCompanyName, String> {

    private final CompanyService service;

    public CompanyNameValidator(CompanyService service) {
        this.service = service;
    }


    @Override
    public boolean isValid(String s, ConstraintValidatorContext constraintValidatorContext) {
        return service.getCompanyByName(s).isEmpty();
    }
}
