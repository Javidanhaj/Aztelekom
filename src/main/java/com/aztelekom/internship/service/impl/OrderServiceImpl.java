package com.aztelekom.internship.service.impl;

import com.aztelekom.internship.domain.entities.Order;
import com.aztelekom.internship.domain.enums.OrderStatus;
import com.aztelekom.internship.dto.order.OrderCreateRequest;
import com.aztelekom.internship.dto.order.OrderStatusUpdateRequest;
import com.aztelekom.internship.repository.OrderItemRepository;
import com.aztelekom.internship.repository.OrderRepository;
import com.aztelekom.internship.service.OrderService;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.UUID;

@Service
public class OrderServiceImpl implements OrderService {

    private final OrderRepository orderRepository;
    private final OrderItemRepository orderItemRepository;

    public OrderServiceImpl(OrderRepository orderRepository, OrderItemRepository orderItemRepository) {
        this.orderRepository = orderRepository;
        this.orderItemRepository = orderItemRepository;
    }

    @Override
    public Page<Order> getOrders(OrderStatus status, UUID customerId, LocalDate dateFrom, LocalDate dateTo, Pageable pageable) {
        return null;
    }

    @Override
    public Order createOrder(OrderCreateRequest orderCreateRequest) {
        return null;
    }

    @Override
    public Order getOrderById(UUID id) {
        return null;
    }

    @Override
    public Order updateOrderStatus(UUID id, OrderStatusUpdateRequest request) {
        return null;
    }

    @Override
    public void deleteOrder(UUID id) {
        if (!orderRepository.existsById(id)) {
        throw new EntityNotFoundException("Order with id " + id + " not found.");
    }
        orderRepository.deleteById(id);
    }
}
