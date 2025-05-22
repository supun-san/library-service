package com.san.libraryservice.validation;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

import java.util.Objects;
import java.util.regex.Pattern;

/**
 * IsbnValidator is a custom constraint validator for validating ISBN-10 and ISBN-13 formats.
 * <p>
 * This validator:
 * - Accepts both ISBN-10 and ISBN-13 inputs with optional spaces or hyphens.
 * - Ignores blank or null values (validation is skipped for optional fields).
 * - Normalizes input by removing whitespace and hyphens before validation.
 * - Uses standard checksum algorithms to validate ISBN integrity.
 *
 * @author Supunsan
 */

public class IsbnValidator implements ConstraintValidator<Isbn, String> {

    private static final Pattern ISBN_10_PATTERN = Pattern.compile("\\d{9}[\\dXx]");
    private static final Pattern ISBN_13_PATTERN = Pattern.compile("\\d{13}");

    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        if (Objects.isNull(value) || value.isBlank()) return true;

        String isbn = value.replaceAll("[\\s-]", "");

        return switch (isbn.length()) {
            case 10 -> isValidIsbn10(isbn);
            case 13 -> isValidIsbn13(isbn);
            default -> false;
        };
    }

    private boolean isValidIsbn10(String isbn) {
        if (!ISBN_10_PATTERN.matcher(isbn).matches()) return false;

        int sum = 0;
        for (int i = 0; i < 9; i++) {
            sum += Character.getNumericValue(isbn.charAt(i)) * (10 - i);
        }

        char checkChar = isbn.charAt(9);
        int checkValue = (checkChar == 'X' || checkChar == 'x') ? 10 : Character.getNumericValue(checkChar);

        return (sum + checkValue) % 11 == 0;
    }

    private boolean isValidIsbn13(String isbn) {
        if (!ISBN_13_PATTERN.matcher(isbn).matches()) return false;

        int sum = 0;
        for (int i = 0; i < 12; i++) {
            int digit = Character.getNumericValue(isbn.charAt(i));
            sum += digit * ((i % 2 == 0) ? 1 : 3);
        }

        int checkDigit = (10 - (sum % 10)) % 10;
        return checkDigit == Character.getNumericValue(isbn.charAt(12));
    }
}
