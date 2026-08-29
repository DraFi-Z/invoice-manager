package com.billing.invoice_manager.repository;

import com.billing.invoice_manager.entity.Invoice;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.Query;
import java.math.BigDecimal;
import java.time.LocalDate;

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



    @Query("SELECT COUNT(i) FROM Invoice i WHERE i.status = :status")
    long countByStatus(@Param("status") String status);

    @Query("SELECT COALESCE(SUM(i.totalAmount), 0) FROM Invoice i WHERE i.status = 'PAID'")
    BigDecimal getTotalRevenue();

    @Query("SELECT COALESCE(SUM(i.totalAmount), 0) FROM Invoice i WHERE i.status IN ('SENT', 'PENDING')")
    BigDecimal getPendingRevenue();

    @Query("SELECT COALESCE(SUM(i.totalAmount), 0) FROM Invoice i " +
            "WHERE i.status = 'PAID' " +
            "AND MONTH(i.issueDate) = MONTH(CURRENT_DATE) " +
            "AND YEAR(i.issueDate) = YEAR(CURRENT_DATE)")
    BigDecimal getCurrentMonthRevenue();

    @Query("SELECT i FROM Invoice i " +
            "WHERE i.status = 'SENT' " +
            "AND i.dueDate < :today " +
            "ORDER BY i.dueDate ASC")
    List<Invoice> findOverdueInvoices(@Param("today") LocalDate today);

    @Query("SELECT YEAR(i.issueDate) as year, MONTH(i.issueDate) as month, " +
            "SUM(i.totalAmount) as revenue, COUNT(i) as invoiceCount " +
            "FROM Invoice i " +
            "WHERE i.status = 'PAID' " +
            "AND i.issueDate >= :startDate " +
            "GROUP BY YEAR(i.issueDate), MONTH(i.issueDate) " +
            "ORDER BY YEAR(i.issueDate), MONTH(i.issueDate)")
    List<Object[]> getMonthlyRevenue(@Param("startDate") LocalDate startDate);
}