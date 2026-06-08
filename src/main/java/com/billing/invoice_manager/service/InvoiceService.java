package com.billing.invoice_manager.service;

import com.billing.invoice_manager.entity.Customer;
import com.billing.invoice_manager.entity.Invoice;
import com.billing.invoice_manager.entity.InvoiceLineItem;
import com.billing.invoice_manager.entity.User;
import com.billing.invoice_manager.exception.InvalidOperationException;
import com.billing.invoice_manager.exception.ResourceNotFoundException;
import com.billing.invoice_manager.repository.CustomerRepository;
import com.billing.invoice_manager.repository.InvoiceRepository;
import com.billing.invoice_manager.repository.UserRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class InvoiceService {

    private final InvoiceRepository invoiceRepository;
    private final CustomerRepository customerRepository;
    private final UserRepository userRepository;

    //constructor injection
    public InvoiceService(InvoiceRepository invoiceRepository,
                          CustomerRepository customerRepository,
                          UserRepository userRepository) {
        this.invoiceRepository = invoiceRepository;
        this.customerRepository = customerRepository;
        this.userRepository = userRepository;
    }

    @Transactional
    public Invoice createInvoice(Invoice invoice, Long customerId, Long userId) {
        Customer customer = customerRepository.findById(customerId)
                .orElseThrow(() -> new ResourceNotFoundException("Customer", "id", customerId));
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("User", "id", userId));

        invoice.setCustomer(customer);
        invoice.setCreatedBy(user);
        invoice.setStatus("DRAFT");
        invoice.setInvoiceNumber(generateInvoiceNumber());
        invoice.setCreatedAt(LocalDateTime.now());
        invoice.setUpdatedAt(LocalDateTime.now());

        if (invoice.getLineItems() != null) {
            for (InvoiceLineItem item : invoice.getLineItems()) {
                item.setInvoice(invoice);
                item.setCreatedAt(LocalDateTime.now());
                item.setUpdatedAt(LocalDateTime.now());
            }
        }

        calculateTotal(invoice);

        return invoiceRepository.save(invoice);
    }
//    Old method without pagination
//    public List<Invoice> getAllInvoices() {
//        return invoiceRepository.findAll();
//    }

    public Page<Invoice> getAllInvoices(Pageable pageable) {
        return invoiceRepository.findAll(pageable);
    }

    public Optional<Invoice> getInvoiceById(Long id) {
        return invoiceRepository.findById(id);
    }

//    public List<Invoice> getInvoicesByStatus(String status) {
//        return invoiceRepository.findByStatus(status);
//    }

    public Page<Invoice> getInvoicesByStatus(String status, Pageable pageable) {
        return invoiceRepository.findByStatus(status, pageable);
    }

//    public List<Invoice> getInvoicesByCustomer(Long customerId) {
//        return invoiceRepository.findByCustomerId(customerId);
//    }

    public Page<Invoice> getInvoicesByCustomer(Long customerId, Pageable pageable) {
        return invoiceRepository.findByCustomerId(customerId, pageable);
    }

    @Transactional
    public Invoice updateInvoiceStatus(Long id, String newStatus) {
        Invoice existing = invoiceRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Invoice", "id", id));
        validateStatusTransition(existing.getStatus(), newStatus);
        existing.setStatus(newStatus);
        existing.setUpdatedAt(LocalDateTime.now());
        return invoiceRepository.save(existing);
    }

    @Transactional
    public void deleteInvoice(Long id) {
        Invoice existing = invoiceRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Invoice", "id", id));
        if (!existing.getStatus().equals("DRAFT")) {
            throw new InvalidOperationException("Only DRAFT invoices can be deleted");
        }
        invoiceRepository.deleteById(id);
    }

    private void calculateTotal(Invoice invoice) {
        if (invoice.getLineItems() == null || invoice.getLineItems().isEmpty()) {
            invoice.setTotalAmount(BigDecimal.ZERO);
            return;
        }
        BigDecimal total = BigDecimal.ZERO;
        for (InvoiceLineItem item : invoice.getLineItems()) {
            total = total.add(item.getTotalPrice());
        }
        invoice.setTotalAmount(total);
    }

    private void validateStatusTransition(String currentStatus, String newStatus) {
        boolean valid = switch (currentStatus) {
            case "DRAFT"     -> newStatus.equals("PENDING") || newStatus.equals("CANCELLED");
            case "PENDING"   -> newStatus.equals("SENT")    || newStatus.equals("CANCELLED");
            case "SENT"      -> newStatus.equals("PAID")    || newStatus.equals("CANCELLED");
            case "PAID"      -> false;
            case "CANCELLED" -> false;
            default -> throw new InvalidOperationException("Unknown status: " + currentStatus);
        };

        if (!valid) {
            throw new InvalidOperationException(
                    "Invalid status transition from " + currentStatus + " to " + newStatus
            );
        }
    }

    private String generateInvoiceNumber() {
        long count = invoiceRepository.count();
        return String.format("INV-%05d", count + 1);
    }
}