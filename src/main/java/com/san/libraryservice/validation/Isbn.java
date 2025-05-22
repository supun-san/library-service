package com.san.libraryservice.validation;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.*;

@Documented
@Constraint(validatedBy = IsbnValidator.class)
@Target({ElementType.FIELD, ElementType.PARAMETER})
@Retention(RetentionPolicy.RUNTIME)
public @interface Isbn {
    String message() default "Invalid ISBN number";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
