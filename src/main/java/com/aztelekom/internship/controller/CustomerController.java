package com.aztelekom.internship.controller;

import com.aztelekom.internship.domain.entities.Customer;
import com.aztelekom.internship.dto.customer.CustomerCreateRequest;
import com.aztelekom.internship.dto.customer.CustomerResponse;
import com.aztelekom.internship.dto.customer.CustomerUpdateRequest;
import com.aztelekom.internship.mapper.CustomerMapper;
import com.aztelekom.internship.service.CustomerService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springdoc.core.annotations.ParameterObject;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/api/v1/customers")
@RequiredArgsConstructor
public class CustomerController {

    private final CustomerService customerService;
    private final CustomerMapper customerMapper;

    @GetMapping
    public Page<CustomerResponse> getCustomers(@RequestParam(required = false) String search,
                                               @ParameterObject Pageable pageable) {
        return customerService.getCustomers(search, pageable)
                .map(customerMapper::toResponse);
    }

    @PostMapping
    public ResponseEntity<CustomerResponse> createCustomer(@Valid @RequestBody CustomerCreateRequest request){
        Customer customerToCreate = customerMapper.toEntity(request);
        Customer savedCustomer = customerService.createCustomer(customerToCreate);
        return ResponseEntity.status(HttpStatus.CREATED).body(customerMapper.toResponse(savedCustomer));
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public ResponseEntity<Void> deleteCustomer(@PathVariable UUID id){
        customerService.deleteCustomer(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @PutMapping("/{id}")
    public ResponseEntity<CustomerResponse> updateCustomer(@PathVariable UUID id,
                                                           @Valid @RequestBody CustomerUpdateRequest request) {
        Customer updatedCustomer = customerService.updateCustomer(id, request);
        CustomerResponse updatedCustomerResponse = customerMapper.toResponse(updatedCustomer);
        return ResponseEntity.status(HttpStatus.OK).body(updatedCustomerResponse);
    }
}
