package com.billing.invoice_manager.service;

import com.billing.invoice_manager.entity.Customer;
import com.billing.invoice_manager.exception.DuplicateResourceException;
import com.billing.invoice_manager.exception.ResourceNotFoundException;
import com.billing.invoice_manager.repository.CustomerRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Slf4j
@Service
public class CustomerService {

    private final CustomerRepository customerRepository;

    //constructor injection of repository class
    //i.e. CustomerRepository is a dependency for CustomerService Entity
    public CustomerService(CustomerRepository customerRepository) {
        this.customerRepository = customerRepository;
    }

    //when function is called by Controller class, the customer is checked in db
    //existsByEmail function comes from CustomerRepository, and it uses
    //customer entity's getEmail getter function , which will be reduced by lombok boilerplate code
    public Customer createCustomer(Customer customer) {
        log.info("Creating customer with email: {}", customer.getEmail());
        if (customerRepository.existsByEmail(customer.getEmail())) {
            log.warn("Duplicate customer creation attempted for email: {}", customer.getEmail());
            throw new DuplicateResourceException("Customer", "email", customer.getEmail());
        }
        customer.setCreatedAt(LocalDateTime.now());
        customer.setUpdatedAt(LocalDateTime.now());
        Customer saved = customerRepository.save(customer);
        log.info("Customer created successfully with id: {}", saved.getId());
        return saved;
    }

    public List<Customer> getAllCustomers() {
        return customerRepository.findAll();
    }

    public Optional<Customer> getCustomerById(Long id) {
        return customerRepository.findById(id);
    }

    public Optional<Customer> getCustomerByEmail(String email) {
        return customerRepository.findByEmail(email);
    }

    public Customer updateCustomer(Long id, Customer updatedCustomer) {
        log.info("Updating customer with id: {}", id);
        Customer existing = customerRepository.findById(id)
                .orElseThrow(() -> {
                    log.error("Customer not found with id: {}", id);
                    return new ResourceNotFoundException("Customer", "id", id);
                });
        // rest stays the same
        existing.setName(updatedCustomer.getName());
        existing.setEmail(updatedCustomer.getEmail());
        existing.setPhone(updatedCustomer.getPhone());
        existing.setAddressLine1(updatedCustomer.getAddressLine1());
        existing.setAddressLine2(updatedCustomer.getAddressLine2());
        existing.setCity(updatedCustomer.getCity());
        existing.setCountry(updatedCustomer.getCountry());
        existing.setUpdatedAt(LocalDateTime.now());

        Customer updated = customerRepository.save(existing);
        log.info("Customer updated successfully with id: {}", updated.getId());
        return updated;
    }

    public void deleteCustomer(Long id) {
        log.info("Deleting customer with id: {}", id);

        if (!customerRepository.existsById(id)) {
            log.error("Customer not found with id: {}", id);
            throw new ResourceNotFoundException("Customer", "id", id);
        }
        customerRepository.deleteById(id);
        log.info("Customer deleted successfully with id: {}", id);
    }
}