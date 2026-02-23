package com.aztelekom.internship.mapper;

import com.aztelekom.internship.domain.entities.Customer;
import com.aztelekom.internship.dto.customer.CustomerCreateRequest;
import com.aztelekom.internship.dto.customer.CustomerResponse;
import com.aztelekom.internship.dto.customer.CustomerUpdateRequest;
import org.mapstruct.*;

@Mapper(componentModel = "spring")
public interface CustomerMapper {

    CustomerResponse toResponse(Customer customer);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    Customer toEntity(CustomerCreateRequest request);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    void updateEntity(CustomerUpdateRequest request, @MappingTarget Customer customer);
}