package com.example.dashboardapi.validator;

import com.example.dashboardapi.service.ProjectService;
import org.springframework.stereotype.Component;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

@Component
public class ProjectNameValidator implements ConstraintValidator<UniqueProjectName, String> {

    private final ProjectService service;

    public ProjectNameValidator(ProjectService service) {
        this.service = service;
    }


    @Override
    public boolean isValid(String s, ConstraintValidatorContext constraintValidatorContext) {
        return service.getProjectName(s).isEmpty();
    }
}
