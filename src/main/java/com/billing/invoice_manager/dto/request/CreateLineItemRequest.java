package com.billing.invoice_manager.dto.request;

import java.math.BigDecimal;

public class CreateLineItemRequest {

    private String description;
    private BigDecimal quantity;
    private BigDecimal unitPrice;

    public CreateLineItemRequest() {
    }

    public String getDescription() { return this.description; }
    public void setDescription(String description) { this.description = description; }

    public BigDecimal getQuantity() { return this.quantity; }
    public void setQuantity(BigDecimal quantity) { this.quantity = quantity; }

    public BigDecimal getUnitPrice() { return this.unitPrice; }
    public void setUnitPrice(BigDecimal unitPrice) { this.unitPrice = unitPrice; }
}