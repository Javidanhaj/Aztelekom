package com.aztelekom.internship.dto.product;

import com.aztelekom.internship.domain.enums.ProductStatus;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.math.BigDecimal;
import java.util.UUID;

public record ProductUpdateRequest(
        @NotBlank String name,
        @NotBlank String sku,
        @NotNull @DecimalMin("0.01") BigDecimal price,
        @Min(0) int stockQty,
        @NotNull UUID categoryId,
        @NotNull ProductStatus status) {
}
