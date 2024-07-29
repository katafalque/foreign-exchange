package com.example.foreignexchange.utils.validators;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.*;

@Documented
@Constraint(validatedBy = {ConversionHistoryRequestValidator.class})
@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
public @interface ValidateConversionHistoryRequestModel {
    String message() default "Either id or date must be provided";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
