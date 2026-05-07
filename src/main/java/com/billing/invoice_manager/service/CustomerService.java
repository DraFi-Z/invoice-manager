package com.billing.invoice_manager.service;

import com.billing.invoice_manager.entity.Customer;
import com.billing.invoice_manager.exception.DuplicateResourceException;
import com.billing.invoice_manager.exception.ResourceNotFoundException;
import com.billing.invoice_manager.repository.CustomerRepository;
import org.springframework.stereotype.Service;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

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
        if (customerRepository.existsByEmail(customer.getEmail())) {
            throw new DuplicateResourceException("Customer", "email", customer.getEmail());
        }
        customer.setCreatedAt(LocalDateTime.now());
        customer.setUpdatedAt(LocalDateTime.now());
        return customerRepository.save(customer);
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
        Customer existing = customerRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Customer", "id", id));
        // rest stays the same
        existing.setName(updatedCustomer.getName());
        existing.setEmail(updatedCustomer.getEmail());
        existing.setPhone(updatedCustomer.getPhone());
        existing.setAddressLine1(updatedCustomer.getAddressLine1());
        existing.setAddressLine2(updatedCustomer.getAddressLine2());
        existing.setCity(updatedCustomer.getCity());
        existing.setCountry(updatedCustomer.getCountry());
        existing.setUpdatedAt(LocalDateTime.now());
        return customerRepository.save(existing);
    }

    public void deleteCustomer(Long id) {
        if (!customerRepository.existsById(id)) {
            throw new ResourceNotFoundException("Customer", "id", id);
        }
        customerRepository.deleteById(id);
    }
}