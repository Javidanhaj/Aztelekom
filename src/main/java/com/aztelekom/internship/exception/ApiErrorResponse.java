package com.aztelekom.internship.exception;

import java.time.LocalDateTime;
import java.util.List;

//JSON error template:
public record ApiErrorResponse(
        LocalDateTime timestamp,
        int status,
        String error,
        String message,
        String path,
        List<ApiFieldError> fieldErrors
) {}