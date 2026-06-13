//package com.billing.invoice_manager.mapper;
//
//import com.billing.invoice_manager.dto.request.CreateInvoiceRequest;
//import com.billing.invoice_manager.dto.request.CreateLineItemRequest;
//import com.billing.invoice_manager.dto.response.InvoiceResponse;
//import com.billing.invoice_manager.dto.response.LineItemResponse;
//import com.billing.invoice_manager.entity.Invoice;
//import com.billing.invoice_manager.entity.InvoiceLineItem;
//import java.math.BigDecimal;
//import java.util.ArrayList;
//import java.util.List;
//
//public class InvoiceMapper {
//
//    public static Invoice toEntity(CreateInvoiceRequest request) {
//        Invoice invoice = new Invoice();
//        invoice.setIssueDate(request.getIssueDate());
//        invoice.setDueDate(request.getDueDate());
//        invoice.setNotes(request.getNotes());
//
//        if (request.getLineItems() != null) {
//            List<InvoiceLineItem> lineItems = new ArrayList<>();
//            for (CreateLineItemRequest itemRequest : request.getLineItems()) {
//                InvoiceLineItem item = new InvoiceLineItem();
//                item.setDescription(itemRequest.getDescription());
//                item.setQuantity(itemRequest.getQuantity());
//                item.setUnitPrice(itemRequest.getUnitPrice());
//                item.setTotalPrice(
//                        itemRequest.getQuantity().multiply(itemRequest.getUnitPrice())
//                );
//                lineItems.add(item);
//            }
//            invoice.setLineItems(lineItems);
//        }
//
//        return invoice;
//    }
//
//    public static InvoiceResponse toResponse(Invoice invoice) {
//        InvoiceResponse response = new InvoiceResponse();
//        response.setId(invoice.getId());
//        response.setInvoiceNumber(invoice.getInvoiceNumber());
//        response.setStatus(invoice.getStatus());
//        response.setIssueDate(invoice.getIssueDate());
//        response.setDueDate(invoice.getDueDate());
//        response.setNotes(invoice.getNotes());
//        response.setTotalAmount(invoice.getTotalAmount());
//        response.setCreatedAt(invoice.getCreatedAt());
//        response.setCustomerName(invoice.getCustomer().getName());
//        response.setCreatedByName(invoice.getCreatedBy().getFullName());
//
//        if (invoice.getLineItems() != null) {
//            List<LineItemResponse> lineItemResponses = new ArrayList<>();
//            for (InvoiceLineItem item : invoice.getLineItems()) {
//                LineItemResponse itemResponse = new LineItemResponse();
//                itemResponse.setId(item.getId());
//                itemResponse.setDescription(item.getDescription());
//                itemResponse.setQuantity(item.getQuantity());
//                itemResponse.setUnitPrice(item.getUnitPrice());
//                itemResponse.setTotalPrice(item.getTotalPrice());
//                lineItemResponses.add(itemResponse);
//            }
//            response.setLineItems(lineItemResponses);
//        }
//
//        return response;
//    }
//}


package com.billing.invoice_manager.mapper;

import com.billing.invoice_manager.dto.request.CreateInvoiceRequest;
import com.billing.invoice_manager.dto.request.CreateLineItemRequest;
import com.billing.invoice_manager.dto.response.InvoiceResponse;
import com.billing.invoice_manager.dto.response.LineItemResponse;
import com.billing.invoice_manager.entity.Invoice;
import com.billing.invoice_manager.entity.InvoiceLineItem;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

@Mapper
public interface InvoiceMapper {

    InvoiceMapper INSTANCE = Mappers.getMapper(InvoiceMapper.class);

    @Mapping(target = "customer", ignore = true)
    @Mapping(target = "createdBy", ignore = true)
    @Mapping(target = "status", ignore = true)
    @Mapping(target = "invoiceNumber", ignore = true)
    @Mapping(target = "totalAmount", ignore = true)
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    Invoice toEntity(CreateInvoiceRequest request);

    @Mapping(source = "customer.name", target = "customerName")
    @Mapping(source = "createdBy.fullName", target = "createdByName")
    InvoiceResponse toResponse(Invoice invoice);

    @Mapping(target = "totalPrice", expression = "java(request.getQuantity().multiply(request.getUnitPrice()))")
    @Mapping(target = "invoice", ignore = true)
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    InvoiceLineItem toLineItemEntity(CreateLineItemRequest request);

    LineItemResponse toLineItemResponse(InvoiceLineItem item);
}