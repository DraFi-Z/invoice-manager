package com.billing.invoice_manager.dto.request;

import java.time.LocalDate;
import java.util.List;

public class CreateInvoiceRequest {

    private Long customerId;
    private LocalDate issueDate;
    private LocalDate dueDate;
    private String notes;
    private List<CreateLineItemRequest> lineItems;

    public CreateInvoiceRequest() {
    }

    public Long getCustomerId() { return this.customerId; }
    public void setCustomerId(Long customerId) { this.customerId = customerId; }

    public LocalDate getIssueDate() { return this.issueDate; }
    public void setIssueDate(LocalDate issueDate) { this.issueDate = issueDate; }

    public LocalDate getDueDate() { return this.dueDate; }
    public void setDueDate(LocalDate dueDate) { this.dueDate = dueDate; }

    public String getNotes() { return this.notes; }
    public void setNotes(String notes) { this.notes = notes; }

    public List<CreateLineItemRequest> getLineItems() { return this.lineItems; }
    public void setLineItems(List<CreateLineItemRequest> lineItems) { this.lineItems = lineItems; }
}