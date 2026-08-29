package com.billing.invoice_manager.dto.request;

import jakarta.validation.Valid;
import jakarta.validation.constraints.Future;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
public class CreateInvoiceRequest {

    @NotNull(message = "Customer ID is required")
    private Long customerId;

    @NotNull(message = "Issue date is required")
    private LocalDate issueDate;

    @NotNull(message = "Due date is required")
    @Future(message = "Due date must be in the future")
    private LocalDate dueDate;

    @Size(max = 1000, message = "Notes cannot exceed 1000 characters")
    private String notes;

    @NotEmpty(message = "At least one line item is required")
    @Valid
    private List<CreateLineItemRequest> lineItems;

//    public CreateInvoiceRequest() {
//    }
//
//    public Long getCustomerId() { return this.customerId; }
//    public void setCustomerId(Long customerId) { this.customerId = customerId; }
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
//    public List<CreateLineItemRequest> getLineItems() { return this.lineItems; }
//    public void setLineItems(List<CreateLineItemRequest> lineItems) { this.lineItems = lineItems; }
}