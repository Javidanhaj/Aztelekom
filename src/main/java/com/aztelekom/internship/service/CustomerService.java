package com.aztelekom.internship.service;

import com.aztelekom.internship.domain.entities.Customer;
import com.aztelekom.internship.dto.customer.CustomerUpdateRequest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.UUID;

public interface CustomerService {

    Page<Customer> getCustomers(String search, Pageable pageable);
    Customer createCustomer(Customer customer);
    Customer updateCustomer(UUID id, CustomerUpdateRequest customerUpdateRequest);
    void deleteCustomer(UUID id);
}