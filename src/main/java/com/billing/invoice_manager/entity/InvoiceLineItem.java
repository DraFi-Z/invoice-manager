package com.billing.invoice_manager.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "invoice_line_items")
public class InvoiceLineItem {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "invoice_id", nullable = false)
    private Invoice invoice;

    @Column(name = "description", nullable = false)
    private String description;

    @Column(name = "quantity", nullable = false)
    private BigDecimal quantity;

    @Column(name = "unit_price", nullable = false)
    private BigDecimal unitPrice;

    @Column(name = "total_price", nullable = false)
    private BigDecimal totalPrice;

    @Column(name = "created_at", nullable = false)
    private LocalDateTime createdAt;

    @Column(name = "updated_at", nullable = false)
    private LocalDateTime updatedAt;

//    public InvoiceLineItem() {
//    }
//
//    public Long getId() { return id; }
//    public void setId(Long id) { this.id = id; }
//
//    public Invoice getInvoice() { return invoice; }
//    public void setInvoice(Invoice invoice) { this.invoice = invoice; }
//
//    public String getDescription() { return description; }
//    public void setDescription(String description) { this.description = description; }
//
//    public BigDecimal getQuantity() { return quantity; }
//    public void setQuantity(BigDecimal quantity) { this.quantity = quantity; }
//
//    public BigDecimal getUnitPrice() { return unitPrice; }
//    public void setUnitPrice(BigDecimal unitPrice) { this.unitPrice = unitPrice; }
//
//    public BigDecimal getTotalPrice() { return totalPrice; }
//    public void setTotalPrice(BigDecimal totalPrice) { this.totalPrice = totalPrice; }
//
//    public LocalDateTime getCreatedAt() { return createdAt; }
//    public void setCreatedAt(LocalDateTime createdAt) { this.createdAt = createdAt; }
//
//    public LocalDateTime getUpdatedAt() { return updatedAt; }
//    public void setUpdatedAt(LocalDateTime updatedAt) { this.updatedAt = updatedAt; }
}