package com.san.libraryservice.constant;

public class ExceptionConstants {

    private ExceptionConstants() {
    }

    public static final String LOG_ERROR_FORMAT = "{}: {}";
    public static final String FIELD_ERROR_FORMAT = "%s: %s";
    public static final String VALIDATION_FAILED_MSG = "Validation failed";
    public static final String ALREADY_EXISTS = " already exists";
    public static final String UNIQUE_CONSTRAINT_COLUMN_REGEX = "Key \\((\\w+)\\)=";
    public static final String LEFT_PARENTHESIS = "(";
    public static final String RIGHT_PARENTHESIS = ")";

}
