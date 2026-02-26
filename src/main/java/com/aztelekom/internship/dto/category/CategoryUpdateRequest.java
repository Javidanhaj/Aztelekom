package com.aztelekom.internship.dto.category;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record CategoryUpdateRequest(
        @NotBlank @Size(min = 2) String name
) {
}
