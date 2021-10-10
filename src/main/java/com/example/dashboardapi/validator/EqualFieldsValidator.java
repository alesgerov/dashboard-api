package com.example.dashboardapi.validator;

import org.springframework.stereotype.Component;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.lang.reflect.Field;

@Component
public class EqualFieldsValidator implements ConstraintValidator<EqualFields, Object> {

    private String field1;
    private String field2;

    @Override
    public void initialize(EqualFields constraintAnnotation) {
        this.field1 = constraintAnnotation.field1();
        this.field2 = constraintAnnotation.field2();
    }

    @Override
    public boolean isValid(Object o, ConstraintValidatorContext constraintValidatorContext) {
        try {
            Object value1 = getValue(field1, o);
            Object value2 = getValue(field2, o);
            return value1 != null && value1.equals(value2);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    private Object getValue(String fieldName, Object o) throws Exception {
        Class<?> smth = o.getClass();
        Field field = smth.getDeclaredField(fieldName);
        field.setAccessible(true);
        return field.get(o);
    }
}
