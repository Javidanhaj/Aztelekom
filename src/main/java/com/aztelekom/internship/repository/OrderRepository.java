package com.aztelekom.internship.repository;

import com.aztelekom.internship.domain.entities.Order;
import com.aztelekom.internship.domain.enums.OrderStatus;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface OrderRepository extends JpaRepository<Order, UUID> {
    Page<Order> findByStatus(OrderStatus status, Pageable pageable);

    Page<Order> findByCustomer_Id(UUID customerId, Pageable pageable);

    Page<Order> findByStatusAndCustomer_Id(OrderStatus status, UUID customerId, Pageable pageable);
}
