package com.billing.invoice_manager.service;

import com.billing.invoice_manager.dto.response.DashboardSummaryResponse;
import com.billing.invoice_manager.dto.response.MonthlyRevenueResponse;
import com.billing.invoice_manager.dto.response.OverdueInvoiceResponse;
import com.billing.invoice_manager.entity.Invoice;
import com.billing.invoice_manager.repository.InvoiceRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.Month;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;

@Slf4j
@Service
public class DashboardService {

    private final InvoiceRepository invoiceRepository;

    public DashboardService(InvoiceRepository invoiceRepository) {
        this.invoiceRepository = invoiceRepository;
    }

    @Transactional(readOnly = true)
    public DashboardSummaryResponse getSummary() {
        log.info("Fetching dashboard summary");

        DashboardSummaryResponse response = new DashboardSummaryResponse();

        response.setTotalInvoices(invoiceRepository.count());
        response.setDraftCount(invoiceRepository.countByStatus("DRAFT"));
        response.setPendingCount(invoiceRepository.countByStatus("PENDING"));
        response.setSentCount(invoiceRepository.countByStatus("SENT"));
        response.setPaidCount(invoiceRepository.countByStatus("PAID"));
        response.setCancelledCount(invoiceRepository.countByStatus("CANCELLED"));

        List<Invoice> overdueInvoices = invoiceRepository
                .findOverdueInvoices(LocalDate.now());
        response.setOverdueCount(overdueInvoices.size());

        response.setTotalRevenue(invoiceRepository.getTotalRevenue());
        response.setPendingRevenue(invoiceRepository.getPendingRevenue());
        response.setEstimatedMonthlyRevenue(invoiceRepository.getCurrentMonthRevenue());

        return response;
    }

    @Transactional(readOnly = true)
    public List<MonthlyRevenueResponse> getMonthlyRevenue() {
        log.info("Fetching monthly revenue for last 12 months");

        LocalDate startDate = LocalDate.now().minusMonths(11).withDayOfMonth(1);
        List<Object[]> rawResults = invoiceRepository.getMonthlyRevenue(startDate);

        List<MonthlyRevenueResponse> responses = new ArrayList<>();
        for (Object[] row : rawResults) {
            MonthlyRevenueResponse monthly = new MonthlyRevenueResponse();
            monthly.setYear(((Number) row[0]).intValue());
            monthly.setMonth(((Number) row[1]).intValue());
            monthly.setMonthName(Month.of(((Number) row[1]).intValue()).name());
            monthly.setRevenue((BigDecimal) row[2]);
            monthly.setInvoiceCount(((Number) row[3]).longValue());
            responses.add(monthly);
        }

        return responses;
    }

    @Transactional(readOnly = true)
    public List<OverdueInvoiceResponse> getOverdueInvoices() {
        log.info("Fetching overdue invoices");

        List<Invoice> overdueInvoices = invoiceRepository
                .findOverdueInvoices(LocalDate.now());

        List<OverdueInvoiceResponse> responses = new ArrayList<>();
        for (Invoice invoice : overdueInvoices) {
            OverdueInvoiceResponse response = new OverdueInvoiceResponse();
            response.setId(invoice.getId());
            response.setInvoiceNumber(invoice.getInvoiceNumber());
            response.setCustomerName(invoice.getCustomer().getName());
            response.setCustomerEmail(invoice.getCustomer().getEmail());
            response.setDueDate(invoice.getDueDate());
            response.setDaysOverdue(
                    ChronoUnit.DAYS.between(invoice.getDueDate(), LocalDate.now())
            );
            response.setTotalAmount(invoice.getTotalAmount());
            response.setCreatedAt(invoice.getCreatedAt());
            responses.add(response);
        }

        return responses;
    }
}