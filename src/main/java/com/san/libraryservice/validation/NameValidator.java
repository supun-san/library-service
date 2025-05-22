package com.san.libraryservice.validation;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

import java.util.Objects;
import java.util.regex.Pattern;

/**
 * NameValidator is a custom Jakarta Bean Validation constraint validator
 * used to ensure that name fields conform to a valid format.
 * <p>
 * Validation Rules:
 * - Allows only alphabetic characters, spaces, hyphens, and apostrophes.
 * - Disallows digits, special characters, or symbols not typically found in names.
 * - Enforces a reasonable length (e.g., 2 to 50 characters).
 *
 * @author Supunsan
 */

public class NameValidator implements ConstraintValidator<ValidName, String> {

    private static final Pattern NAME_PATTERN = Pattern.compile("^[A-Za-z][A-Za-z\\s'\\-]{1,48}[A-Za-z]$");

    @Override
    public boolean isValid(String name, ConstraintValidatorContext context) {
        if (Objects.isNull(name) || name.isBlank()) {
            return true;
        }
        return NAME_PATTERN.matcher(name.trim()).matches();
    }
}

