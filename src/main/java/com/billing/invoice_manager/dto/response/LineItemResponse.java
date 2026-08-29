package com.billing.invoice_manager.dto.response;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
@NoArgsConstructor
public class LineItemResponse {

    private Long id;
    private String description;
    private BigDecimal quantity;
    private BigDecimal unitPrice;
    private BigDecimal totalPrice;

//    public LineItemResponse() {
//    }
//
//    public Long getId() { return this.id; }
//    public void setId(Long id) { this.id = id; }
//
//    public String getDescription() { return this.description; }
//    public void setDescription(String description) { this.description = description; }
//
//    public BigDecimal getQuantity() { return this.quantity; }
//    public void setQuantity(BigDecimal quantity) { this.quantity = quantity; }
//
//    public BigDecimal getUnitPrice() { return this.unitPrice; }
//    public void setUnitPrice(BigDecimal unitPrice) { this.unitPrice = unitPrice; }
//
//    public BigDecimal getTotalPrice() { return this.totalPrice; }
//    public void setTotalPrice(BigDecimal totalPrice) { this.totalPrice = totalPrice; }
}