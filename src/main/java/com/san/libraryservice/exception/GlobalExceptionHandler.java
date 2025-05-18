package com.san.libraryservice.exception;

import jakarta.validation.ConstraintViolationException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

import static com.san.libraryservice.constant.ExceptionConstants.*;

@RestControllerAdvice
@Slf4j
public class GlobalExceptionHandler {

    /**
     * Map to associate exceptions with their corresponding HTTP status codes
     */
    private static final Map<Class<? extends Exception>, HttpStatus> EXCEPTION_STATUS_MAP = Map.ofEntries(
            Map.entry(UnsupportedOperationException.class, HttpStatus.NOT_IMPLEMENTED),
            Map.entry(MethodArgumentNotValidException.class, HttpStatus.BAD_REQUEST),
            Map.entry(ConstraintViolationException.class, HttpStatus.BAD_REQUEST),
            Map.entry(RecordNotFoundException.class, HttpStatus.NOT_FOUND)
    );

    /**
     * Handles specific exceptions and returns a response entity with the appropriate status code.
     *
     * @param ex The exception to be handled
     * @return ResponseEntity<ApiErrors> The response entity containing error details
     * @author Supunsan
     */
    @ExceptionHandler(Exception.class)
    public ResponseEntity<ApiErrors> handleCustomExceptions(Exception ex) {
        HttpStatus status = getHttpStatus(ex);
        return buildResponseEntity(ex, status);
    }

    /**
     * Determines the HTTP status code for the given exception.
     *
     * @param ex The exception to be handled
     * @return HttpStatus The corresponding HTTP status code
     * @author Supunsan
     */
    private HttpStatus getHttpStatus(Exception ex) {
        return EXCEPTION_STATUS_MAP.getOrDefault(ex.getClass(), HttpStatus.BAD_REQUEST);
    }

    /**
     * Builds a response entity for the given exception and status code.
     *
     * @param ex     The exception to be handled
     * @param status The HTTP status code
     * @return ResponseEntity<ApiErrors> The response entity containing error details
     * @author Supunsan
     */
    private ResponseEntity<ApiErrors> buildResponseEntity(Exception ex, HttpStatus status) {
        log.error(LOG_ERROR_FORMAT, ex.getClass().getSimpleName(), ex.getMessage());

        final String message;
        final List<String> details;

        if (ex instanceof MethodArgumentNotValidException e) {
            message = VALIDATION_FAILED_MSG;
            details = extractValidationDetails(e);
        } else if (ex instanceof ConstraintViolationException e) {
            message = VALIDATION_FAILED_MSG;
            details = extractViolationDetails(e);
        } else {
            message = ex.getMessage();
            details = List.of(ex.getClass().getSimpleName());
        }

        return ResponseEntity.status(status)
                .body(new ApiErrors(message, details, status.value(), LocalDateTime.now()));
    }

    /**
     * Extracts validation error messages from MethodArgumentNotValidException.
     *
     * @param ex the MethodArgumentNotValidException thrown during @Valid validation
     * @return a list of detailed validation error messages (field: message)
     * @author Supunsan
     */
    private List<String> extractValidationDetails(MethodArgumentNotValidException ex) {
        return ex.getBindingResult().getFieldErrors().stream()
                .map(error -> FIELD_ERROR_FORMAT.formatted(error.getField(), error.getDefaultMessage()))
                .toList();
    }

    /**
     * Extracts constraint violation messages from ConstraintViolationException.
     *
     * @param ex the ConstraintViolationException thrown during validation
     * @return a list of detailed constraint violation messages (propertyPath: message)
     * @author Supunsan
     */
    private List<String> extractViolationDetails(ConstraintViolationException ex) {
        return ex.getConstraintViolations().stream()
                .map(v -> FIELD_ERROR_FORMAT.formatted(v.getPropertyPath(), v.getMessage()))
                .toList();
    }
}
