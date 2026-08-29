package com.billing.invoice_manager.dto.response;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
public class OverdueInvoiceResponse {

    private Long id;
    private String invoiceNumber;
    private String customerName;
    private String customerEmail;
    private LocalDate dueDate;
    private long daysOverdue;
    private BigDecimal totalAmount;
    private LocalDateTime createdAt;

}