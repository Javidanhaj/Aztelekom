package com.aztelekom.internship.dto.product;

import com.aztelekom.internship.domain.enums.ProductStatus;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

public record ProductResponse(
        UUID id,
        String name,
        String sku,
        BigDecimal price,
        int stockQty,
        UUID categoryId,
        ProductStatus status,
        LocalDateTime createdAt) {}
