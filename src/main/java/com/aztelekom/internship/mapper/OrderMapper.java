package com.aztelekom.internship.mapper;

import com.aztelekom.internship.domain.entities.Order;
import com.aztelekom.internship.domain.entities.OrderItem;
import com.aztelekom.internship.dto.order.OrderItemResponse;
import com.aztelekom.internship.dto.order.OrderResponse;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface OrderMapper {

    // Order -> OrderResponse
    @Mapping(target = "customerId", source = "customer.id")
    OrderResponse toResponse(Order order);

    // OrderItem -> OrderItemResponse
    @Mapping(target = "productId", source = "product.id")
    OrderItemResponse toItemResponse(OrderItem item);
}