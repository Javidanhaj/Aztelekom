package com.aztelekom.internship.repository;

import com.aztelekom.internship.domain.entities.Customer;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CustomerRepository extends JpaRepository<Customer, Integer> {

    Page<Customer> findByFullNameContainingIgnoreCaseOrPhoneContainingIgnoreCaseOrEmailContainingIgnoreCase(
            String fullName, String phone, String email, Pageable pageable
    );

}