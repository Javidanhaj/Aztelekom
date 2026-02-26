package com.aztelekom.internship.mapper;

import com.aztelekom.internship.domain.entities.Category;
import com.aztelekom.internship.dto.category.CategoryCreateRequest;
import com.aztelekom.internship.dto.category.CategoryResponse;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface CategoryMapper {

    CategoryResponse toResponse(Category category);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    Category toEntity(CategoryCreateRequest request);


}
