package com.aztelekom.internship.dto.customer;

public record CustomerUpdateRequest(
        String fullName,
        String phone,
        String email
) {}