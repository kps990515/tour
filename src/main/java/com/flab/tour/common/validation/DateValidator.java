package com.flab.tour.common.validation;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class DateValidator implements ConstraintValidator<Date, String> {
    private String regexp;

    @Override
    public boolean isValid(String date, ConstraintValidatorContext context) {
        return date != null && date.length() == 8;
    }
}
