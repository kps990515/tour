package com.flab.tour.common.validation;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.*;

@Documented
@Constraint(validatedBy = PhoneNumberValidator.class)
@Target({ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
public @interface Date {
    String message() default "날짜 형식이 맞지 않습니다";
    Class<?>[] groups() default { };
    Class<? extends Payload>[] payload() default { };
}

