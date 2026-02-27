package com.aztelekom.internship.exception;

public record ApiFieldError(
        String field,
        String message
) {}