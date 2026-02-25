package com.aztelekom.internship.service.impl;

import com.aztelekom.internship.domain.entities.Customer;
import com.aztelekom.internship.domain.entities.Order;
import com.aztelekom.internship.domain.entities.OrderItem;
import com.aztelekom.internship.domain.enums.OrderStatus;
import com.aztelekom.internship.dto.order.OrderCreateRequest;
import com.aztelekom.internship.dto.order.OrderItemRequest;
import com.aztelekom.internship.dto.order.OrderStatusUpdateRequest;
import com.aztelekom.internship.repository.CustomerRepository;
import com.aztelekom.internship.repository.OrderItemRepository;
import com.aztelekom.internship.repository.OrderRepository;
import com.aztelekom.internship.service.OrderService;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.List;
import java.util.UUID;

@Service
public class OrderServiceImpl implements OrderService {

    private final OrderRepository orderRepository;
    private final OrderItemRepository orderItemRepository;
    private final CustomerRepository customerRepository;

    public OrderServiceImpl(OrderRepository orderRepository, OrderItemRepository orderItemRepository, CustomerRepository customerRepository) {
        this.orderRepository = orderRepository;
        this.orderItemRepository = orderItemRepository;
        this.customerRepository = customerRepository;
    }

    @Override
    public Page<Order> getOrders(OrderStatus status, UUID customerId, LocalDate dateFrom, LocalDate dateTo, Pageable pageable) {

        if (status == null && customerId == null) {
            return orderRepository.findAll(pageable);
        }
        if (status != null && customerId == null) {
            return orderRepository.findByStatus(status, pageable);
        }
        if (status == null) { // customerId != null
            return orderRepository.findByCustomer_Id(customerId, pageable);
        }
        // both provided
        return orderRepository.findByStatusAndCustomer_Id(status, customerId, pageable);
    }

    @Override
    @Transactional
    public Order createOrder(OrderCreateRequest request) {
        UUID customerId = request.customerId();
        Customer customer = customerRepository.findById(customerId).orElseThrow(() -> new EntityNotFoundException("Customer not found with id " + customerId));

        Order order = new Order();
        order.setCustomer(customer);

        for (OrderItemRequest itemReq : request.items()) {
            UUID productId = itemReq.productId();
            Product product = productRepository.findById(productId).orElseThrow(() -> new EntityNotFoundException("Product not found with id " + productId));

            if (product.getStockQty() < itemReq.qty()) {
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Insufficient stock for product " + productId + ": requested " + itemReq.qty() + ", available " + product.getStockQty());
            }

            BigDecimal unitPrice = product.getPrice();
            BigDecimal lineTotal = unitPrice.multiply(BigDecimal.valueOf(itemReq.qty()));

            OrderItem orderItem = new OrderItem();
            orderItem.setProduct(product);
            orderItem.setQty(itemReq.qty());
            orderItem.setUnitPrice(unitPrice);
            orderItem.setLineTotal(lineTotal);

            order.addItem(orderItem);

            product.setStockQty(product.getStockQty() - itemReq.qty());
        }

        BigDecimal total = BigDecimal.ZERO;
        Set<Product> updatedProducts = new HashSet<>();

        for (OrderItem item : order.getItems()) {
            total = total.add(item.getLineTotal());
            updatedProducts.add(item.getProduct());
        }

        order.setTotalAmount(total);

        Order saved = orderRepository.save(order);
        productRepository.saveAll(updatedProducts);

        return saved;
    }

    @Override
    public Order getOrderById(UUID id) {
        Order order = orderRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("Order not found with id " + id));
        List<OrderItem> items = orderItemRepository.findByOrder_Id(id);
        order.setItems(items);

        return order;
    }

    @Override
    @Transactional
    public Order updateOrderStatus(UUID id, OrderStatusUpdateRequest request) {
        Order order = orderRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("Order not found with id " + id));

        OrderStatus newStatus = request.status();
        OrderStatus oldStatus = order.getStatus();

        if (newStatus == OrderStatus.CANCELLED && oldStatus != OrderStatus.CANCELLED) {
            List<OrderItem> items = orderItemRepository.findByOrder_Id(id);

            Set<Product> updatedProducts = new HashSet<>();
            for (OrderItem item : items) {
                Product product = item.getProduct();
                product.setStockQty(product.getStockQty() + item.getQty());
                updatedProducts.add(product);
            }

            productRepository.saveAll(updatedProducts);
        }

        order.setStatus(newStatus);
        return orderRepository.save(order);
    }

    @Override
    public void deleteOrder(UUID id) {
        if (!orderRepository.existsById(id)) {
            throw new EntityNotFoundException("Order with id " + id + " not found.");
        }
        orderRepository.deleteById(id);
    }
}
