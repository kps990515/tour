package com.flab.tour.common.validation;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.*;

@Documented
@Constraint(validatedBy = PhoneNumberValidator.class)
@Target({ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
public @interface PhoneNumber {
    String message() default "핸드폰 번호 양식이 맞지 않습니다";
    String regexp() default "^01([0|1|6|7|8|9])-?([0-9]{3,4})-?([0-9]{4})$";
    Class<?>[] groups() default { };
    Class<? extends Payload>[] payload() default { };
}

