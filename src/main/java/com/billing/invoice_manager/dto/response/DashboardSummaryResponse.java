package com.billing.invoice_manager.dto.response;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
@NoArgsConstructor
public class DashboardSummaryResponse {

    private long totalInvoices;
    private long draftCount;
    private long pendingCount;
    private long sentCount;
    private long paidCount;
    private long cancelledCount;
    private long overdueCount;
    private BigDecimal totalRevenue;
    private BigDecimal pendingRevenue;
    private BigDecimal estimatedMonthlyRevenue;

}