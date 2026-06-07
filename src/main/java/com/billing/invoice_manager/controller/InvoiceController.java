package com.billing.invoice_manager.controller;

import com.billing.invoice_manager.dto.request.CreateInvoiceRequest;
import com.billing.invoice_manager.dto.response.InvoiceResponse;
import com.billing.invoice_manager.entity.Invoice;
import com.billing.invoice_manager.exception.ResourceNotFoundException;
import com.billing.invoice_manager.mapper.InvoiceMapper;
import com.billing.invoice_manager.service.InvoiceService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/invoices")
public class InvoiceController {

    private final InvoiceService invoiceService;

    public InvoiceController(InvoiceService invoiceService) {
        this.invoiceService = invoiceService;
    }

    @PostMapping
    public ResponseEntity<InvoiceResponse> createInvoice(
            @Valid @RequestBody CreateInvoiceRequest request,
            @RequestParam Long userId) {
        Invoice invoice = InvoiceMapper.toEntity(request);
        Invoice created = invoiceService.createInvoice(invoice, request.getCustomerId(), userId);
        return ResponseEntity.status(HttpStatus.CREATED).body(InvoiceMapper.toResponse(created));
    }

    @GetMapping
    public ResponseEntity<List<InvoiceResponse>> getAllInvoices() {
        List<InvoiceResponse> invoices = invoiceService.getAllInvoices()
                .stream()
                .map(InvoiceMapper::toResponse)
                .collect(Collectors.toList());
        return ResponseEntity.ok(invoices);
    }

    @GetMapping("/{id}")
    public ResponseEntity<InvoiceResponse> getInvoiceById(@PathVariable Long id) {
        Invoice invoice = invoiceService.getInvoiceById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Invoice", "id", id));
        return ResponseEntity.ok(InvoiceMapper.toResponse(invoice));
    }

    @GetMapping("/status/{status}")
    public ResponseEntity<List<InvoiceResponse>> getInvoicesByStatus(@PathVariable String status) {
        List<InvoiceResponse> invoices = invoiceService.getInvoicesByStatus(status)
                .stream()
                .map(InvoiceMapper::toResponse)
                .collect(Collectors.toList());
        return ResponseEntity.ok(invoices);
    }

    @GetMapping("/customer/{customerId}")
    public ResponseEntity<List<InvoiceResponse>> getInvoicesByCustomer(
            @PathVariable Long customerId) {
        List<InvoiceResponse> invoices = invoiceService.getInvoicesByCustomer(customerId)
                .stream()
                .map(InvoiceMapper::toResponse)
                .collect(Collectors.toList());
        return ResponseEntity.ok(invoices);
    }

    @PutMapping("/{id}/status")
    public ResponseEntity<InvoiceResponse> updateInvoiceStatus(@PathVariable Long id,
                                                               @RequestBody Map<String, String> body) {
        String newStatus = body.get("status");
        Invoice updated = invoiceService.updateInvoiceStatus(id, newStatus);
        return ResponseEntity.ok(InvoiceMapper.toResponse(updated));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteInvoice(@PathVariable Long id) {
        invoiceService.deleteInvoice(id);
        return ResponseEntity.noContent().build();
    }
}