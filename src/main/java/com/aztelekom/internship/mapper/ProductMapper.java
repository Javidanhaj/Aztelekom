package com.aztelekom.internship.mapper;

import com.aztelekom.internship.domain.entities.Product;
import com.aztelekom.internship.dto.product.ProductCreateRequest;
import com.aztelekom.internship.dto.product.ProductResponse;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface ProductMapper {

    @Mapping(target = "categoryId", source = "category.id")
    ProductResponse toResponse(Product product);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "category", ignore = true)
    Product createToEntity(ProductCreateRequest request);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "category", ignore = true)
    Product updateToEntity(ProductCreateRequest request, @MappingTarget Product product);
}
