package com.billing.invoice_manager.repository;

import com.billing.invoice_manager.entity.Invoice;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;
import java.util.Optional;

@Repository
public interface InvoiceRepository extends JpaRepository<Invoice, Long> {

    Optional<Invoice> findByInvoiceNumber(String invoiceNumber);

//    will create problems  if a set of 10000 invoices are being returned at once.
//    List<Invoice> findByStatus(String status);

    Page<Invoice> findAll(Pageable pageable);

    Page<Invoice> findByStatus(String status, Pageable pageable);

    Page<Invoice> findByCustomerId(Long customerId, Pageable pageable);

//    List<Invoice> findByCustomerId(Long customerId);

    List<Invoice> findByCreatedById(Long userId);

    boolean existsByInvoiceNumber(String invoiceNumber);
}