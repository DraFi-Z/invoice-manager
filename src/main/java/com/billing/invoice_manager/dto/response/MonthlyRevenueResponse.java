package com.billing.invoice_manager.dto.response;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
@NoArgsConstructor
public class MonthlyRevenueResponse {

    private int year;
    private int month;
    private String monthName;
    private BigDecimal revenue;
    private long invoiceCount;

}