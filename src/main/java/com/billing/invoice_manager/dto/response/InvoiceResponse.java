package com.billing.invoice_manager.dto.response;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
public class InvoiceResponse {

    private Long id;
    private String invoiceNumber;
    private String customerName;
    private String createdByName;
    private String status;
    private LocalDate issueDate;
    private LocalDate dueDate;
    private String notes;
    private BigDecimal totalAmount;
    private List<LineItemResponse> lineItems;
    private LocalDateTime createdAt;

//    public InvoiceResponse() {
//    }
//
//    public Long getId() { return this.id; }
//    public void setId(Long id) { this.id = id; }
//
//    public String getInvoiceNumber() { return this.invoiceNumber; }
//    public void setInvoiceNumber(String invoiceNumber) { this.invoiceNumber = invoiceNumber; }
//
//    public String getCustomerName() { return this.customerName; }
//    public void setCustomerName(String customerName) { this.customerName = customerName; }
//
//    public String getCreatedByName() { return this.createdByName; }
//    public void setCreatedByName(String createdByName) { this.createdByName = createdByName; }
//
//    public String getStatus() { return this.status; }
//    public void setStatus(String status) { this.status = status; }
//
//    public LocalDate getIssueDate() { return this.issueDate; }
//    public void setIssueDate(LocalDate issueDate) { this.issueDate = issueDate; }
//
//    public LocalDate getDueDate() { return this.dueDate; }
//    public void setDueDate(LocalDate dueDate) { this.dueDate = dueDate; }
//
//    public String getNotes() { return this.notes; }
//    public void setNotes(String notes) { this.notes = notes; }
//
//    public BigDecimal getTotalAmount() { return this.totalAmount; }
//    public void setTotalAmount(BigDecimal totalAmount) { this.totalAmount = totalAmount; }
//
//    public List<LineItemResponse> getLineItems() { return this.lineItems; }
//    public void setLineItems(List<LineItemResponse> lineItems) { this.lineItems = lineItems; }
//
//    public LocalDateTime getCreatedAt() { return this.createdAt; }
//    public void setCreatedAt(LocalDateTime createdAt) { this.createdAt = createdAt; }
}