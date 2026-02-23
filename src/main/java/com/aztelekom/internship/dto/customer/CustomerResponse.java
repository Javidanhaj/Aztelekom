package com.aztelekom.internship.dto.customer;

import java.time.LocalDateTime;
import java.util.UUID;

public record CustomerResponse(
        UUID id,
        String fullName,
        String phone,
        String email,
        LocalDateTime createdAt
) {}