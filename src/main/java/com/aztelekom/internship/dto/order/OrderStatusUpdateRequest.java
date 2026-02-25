package com.aztelekom.internship.dto.order;

import com.aztelekom.internship.domain.enums.OrderStatus;
import jakarta.validation.constraints.NotNull;

public record OrderStatusUpdateRequest(
        @NotNull OrderStatus status
) {}