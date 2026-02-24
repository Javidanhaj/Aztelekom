package com.aztelekom.internship.dto.order;

import java.math.BigDecimal;
import java.util.UUID;

public record OrderItemResponse(
        UUID productId,
        int qty,
        BigDecimal unitPrice,
        BigDecimal lineTotal
) {}