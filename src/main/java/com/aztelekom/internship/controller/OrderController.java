package com.aztelekom.internship.controller;

import com.aztelekom.internship.domain.entities.Order;
import com.aztelekom.internship.domain.enums.OrderStatus;
import com.aztelekom.internship.dto.order.OrderCreateRequest;
import com.aztelekom.internship.dto.order.OrderResponse;
import com.aztelekom.internship.dto.order.OrderStatusUpdateRequest;
import com.aztelekom.internship.mapper.OrderMapper;
import com.aztelekom.internship.service.OrderService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springdoc.core.annotations.ParameterObject;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.UUID;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/orders")
public class OrderController {

    private final OrderService orderService;
    private final OrderMapper orderMapper;

    @GetMapping
    public Page<OrderResponse> getOrders(
            @RequestParam(required = false) OrderStatus status,
            @RequestParam(required = false) UUID customerId,
            @RequestParam(required = false)
            @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate dateFrom,
            @RequestParam(required = false)
            @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate dateTo,
            @ParameterObject Pageable pageable) {
        return orderService.getOrders(status, customerId, dateFrom, dateTo, pageable)
                .map(orderMapper::toResponse);
    }

    @PostMapping
    public ResponseEntity<OrderResponse> createOrder(@Valid @RequestBody OrderCreateRequest request) {
        Order saved = orderService.createOrder(request);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(orderMapper.toResponse(saved));
    }

    @GetMapping("/{id}")
    public OrderResponse getOrderById(@PathVariable UUID id) {
        Order order = orderService.getOrderById(id);
        return orderMapper.toResponse(order);
    }

    @PatchMapping("/{id}/status")
    public OrderResponse updateOrderStatus(@PathVariable UUID id,
                                           @Valid @RequestBody OrderStatusUpdateRequest request) {
        Order updatedOrder = orderService.updateOrderStatus(id, request);
        return orderMapper.toResponse(updatedOrder);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteOrderById(@PathVariable UUID id) {
        orderService.deleteOrder(id);
    }
}
