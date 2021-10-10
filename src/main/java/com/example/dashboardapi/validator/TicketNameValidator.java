package com.example.dashboardapi.validator;

import com.example.dashboardapi.service.TicketService;
import org.springframework.stereotype.Component;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

@Component
public class TicketNameValidator implements ConstraintValidator<UniqueTicketName, String> {

    private final TicketService service;

    public TicketNameValidator(TicketService service) {
        this.service = service;
    }

    @Override
    public boolean isValid(String s, ConstraintValidatorContext constraintValidatorContext) {
        return service.getTicketByTitle(s).isEmpty();
    }
}
