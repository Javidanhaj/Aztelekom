package com.aztelekom.internship.dto.order;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;

import java.util.List;
import java.util.UUID;

public record OrderCreateRequest(
        @NotNull UUID customerId,
        @NotEmpty @Valid List<OrderItemRequest> items
) {}