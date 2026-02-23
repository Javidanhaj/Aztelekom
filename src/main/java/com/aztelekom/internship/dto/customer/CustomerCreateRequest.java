package com.aztelekom.internship.dto.customer;

public record CustomerCreateRequest(
        String fullName,
        String phone,
        String email
) {}