package com.san.libraryservice.exception;

import java.time.LocalDateTime;
import java.util.List;

public record ApiErrors(
        String message,
        List<String> details,
        int status,
        LocalDateTime timestamp) {
}
