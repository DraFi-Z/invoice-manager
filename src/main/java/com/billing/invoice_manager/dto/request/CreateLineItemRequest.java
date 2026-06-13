package com.billing.invoice_manager.dto.request;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
@NoArgsConstructor
public class CreateLineItemRequest {

    @NotBlank(message = "Description is required")
    @Size(max = 255, message = "Description cannot exceed 255 characters")
    private String description;

    @NotNull(message = "Quantity is required")
    @DecimalMin(value = "0.01", message = "Quantity must be greater than 0")
    private BigDecimal quantity;

    @NotNull(message = "Unit price is required")
    @DecimalMin(value = "0.00", inclusive = true, message = "Unit price cannot be negative")
    private BigDecimal unitPrice;

//    public CreateLineItemRequest() {
//    }
//
//    public String getDescription() { return this.description; }
//    public void setDescription(String description) { this.description = description; }
//
//    public BigDecimal getQuantity() { return this.quantity; }
//    public void setQuantity(BigDecimal quantity) { this.quantity = quantity; }
//
//    public BigDecimal getUnitPrice() { return this.unitPrice; }
//    public void setUnitPrice(BigDecimal unitPrice) { this.unitPrice = unitPrice; }
}