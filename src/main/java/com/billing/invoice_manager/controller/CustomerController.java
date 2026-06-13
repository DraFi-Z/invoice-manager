package com.billing.invoice_manager.controller;

import com.billing.invoice_manager.dto.request.CreateCustomerRequest;
import com.billing.invoice_manager.dto.response.CustomerResponse;
import com.billing.invoice_manager.entity.Customer;
import com.billing.invoice_manager.exception.ResourceNotFoundException;
import com.billing.invoice_manager.mapper.CustomerMapper;
import com.billing.invoice_manager.service.CustomerService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/customers")
public class CustomerController {

    private final CustomerService customerService;

    public CustomerController(CustomerService customerService) {
        this.customerService = customerService;
    }

    @PostMapping
    public ResponseEntity<CustomerResponse> createCustomer(
            @Valid @RequestBody CreateCustomerRequest request) {
        Customer customer = CustomerMapper.INSTANCE.toEntity(request);
        Customer created = customerService.createCustomer(customer);
        return ResponseEntity.status(HttpStatus.CREATED).body(CustomerMapper.INSTANCE.toResponse(created));
    }

    @GetMapping
    public ResponseEntity<List<CustomerResponse>> getAllCustomers() {
        List<CustomerResponse> customers = customerService.getAllCustomers()
                .stream()
                .map(customer -> CustomerMapper.INSTANCE.toResponse(customer))
                .collect(Collectors.toList());
        return ResponseEntity.ok(customers);
    }

    @GetMapping("/{id}")
    public ResponseEntity<CustomerResponse> getCustomerById(@PathVariable Long id) {
        Customer customer = customerService.getCustomerById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Customer", "id", id));
        return ResponseEntity.ok(CustomerMapper.INSTANCE.toResponse(customer));
    }

    @PutMapping("/{id}")
    public ResponseEntity<CustomerResponse> updateCustomer(@PathVariable Long id,
                                                           @RequestBody CreateCustomerRequest request) {
        Customer customer = CustomerMapper.INSTANCE.toEntity(request);
        Customer updated = customerService.updateCustomer(id, customer);
        return ResponseEntity.ok(CustomerMapper.INSTANCE.toResponse(updated));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteCustomer(@PathVariable Long id) {
        customerService.deleteCustomer(id);
        return ResponseEntity.noContent().build();
    }
}