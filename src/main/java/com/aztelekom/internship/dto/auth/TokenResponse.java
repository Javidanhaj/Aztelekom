package com.aztelekom.internship.dto.auth;

public record TokenResponse(
        String tokenType,
        String accessToken,
        long expiresInSeconds
) {}