package com.billing.invoice_manager.controller;

import com.billing.invoice_manager.dto.response.DashboardSummaryResponse;
import com.billing.invoice_manager.dto.response.MonthlyRevenueResponse;
import com.billing.invoice_manager.dto.response.OverdueInvoiceResponse;
import com.billing.invoice_manager.service.DashboardService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import java.util.List;

@Slf4j
@RestController
@RequestMapping("/api/dashboard")
public class DashboardController {

    private final DashboardService dashboardService;

    public DashboardController(DashboardService dashboardService) {
        this.dashboardService = dashboardService;
    }

    @GetMapping("/summary")
    public ResponseEntity<DashboardSummaryResponse> getSummary() {
        return ResponseEntity.ok(dashboardService.getSummary());
    }

    @GetMapping("/revenue-by-month")
    public ResponseEntity<List<MonthlyRevenueResponse>> getMonthlyRevenue() {
        return ResponseEntity.ok(dashboardService.getMonthlyRevenue());
    }

    @GetMapping("/overdue-invoices")
    public ResponseEntity<List<OverdueInvoiceResponse>> getOverdueInvoices() {
        return ResponseEntity.ok(dashboardService.getOverdueInvoices());
    }
}