package com.aztelekom.internship.service.impl;

import com.aztelekom.internship.domain.entities.Customer;
import com.aztelekom.internship.dto.customer.CustomerUpdateRequest;
//import com.aztelekom.internship.mapper.CustomerMapper;
import com.aztelekom.internship.repository.CustomerRepository;
import com.aztelekom.internship.service.CustomerService;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class CustomerServiceImpl implements CustomerService {

    private final CustomerRepository customerRepository;
  //private final CustomerMapper customerMapper;
    public CustomerServiceImpl(CustomerRepository customerRepository) {
        this.customerRepository = customerRepository;
        //this.customerMapper = customerMapper;
    }

    @Override
    public Page<Customer> getCustomers(String search, Pageable pageable) {
        String query = (search == null) ? "" : search.trim();
        if (query.isEmpty()) {
            return customerRepository.findAll(pageable);
        }
        return customerRepository
                .findByFullNameContainingIgnoreCaseOrPhoneContainingIgnoreCaseOrEmailContainingIgnoreCase(
                        query, query, query, pageable);
    }

    @Override
    public Customer createCustomer(Customer customer) {
//        if(customerRepository.existsById(customer.getId())){
//            throw new IllegalArgumentException("Customer with id " + customer.getId() + " already exists.");
//        }
        return customerRepository.save(customer);
    }

    @Override
    public Customer updateCustomer(UUID id, CustomerUpdateRequest customerUpdateRequest) {
        Customer existingCustomer = customerRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Customer with id " + id + " not found."));

        existingCustomer.setFullName(customerUpdateRequest.fullName());
        existingCustomer.setPhone(customerUpdateRequest.phone());
        existingCustomer.setEmail(customerUpdateRequest.email());

        //customerMapper.updateEntity(customerUpdateRequest, existingCustomer);
        return customerRepository.save(existingCustomer);
    }

    @Override
    public void deleteCustomer(UUID customerId) {
        customerRepository.deleteById(customerId);
    }
}
