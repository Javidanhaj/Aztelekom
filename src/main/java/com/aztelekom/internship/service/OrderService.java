package com.aztelekom.internship.service;


import com.aztelekom.internship.domain.entities.Order;
import com.aztelekom.internship.domain.enums.OrderStatus;
import com.aztelekom.internship.dto.order.OrderCreateRequest;
import com.aztelekom.internship.dto.order.OrderStatusUpdateRequest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.time.LocalDate;
import java.util.UUID;

public interface OrderService {

    Page<Order> getOrders(OrderStatus status, UUID customerId, LocalDate dateFrom, LocalDate dateTo, Pageable pageable);
    Order createOrder(OrderCreateRequest request);
    Order getOrderById(UUID id);
    Order updateOrderStatus(UUID id, OrderStatusUpdateRequest request);
    void deleteOrder(UUID id);
}
