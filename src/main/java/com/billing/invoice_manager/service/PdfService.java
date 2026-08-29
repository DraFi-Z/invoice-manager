package com.billing.invoice_manager.service;

import com.billing.invoice_manager.entity.Invoice;
import com.billing.invoice_manager.entity.InvoiceLineItem;
import com.itextpdf.text.BaseColor;
import com.itextpdf.text.Chunk;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Element;
import com.itextpdf.text.Font;
import com.itextpdf.text.FontFactory;
import com.itextpdf.text.PageSize;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.Phrase;
import com.itextpdf.text.Rectangle;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import java.io.ByteArrayOutputStream;
import java.time.format.DateTimeFormatter;

@Slf4j
@Service
public class PdfService {

    private static final BaseColor HEADER_COLOR = new BaseColor(24, 95, 165);
    private static final BaseColor LIGHT_GRAY = new BaseColor(245, 245, 245);
    private static final DateTimeFormatter DATE_FORMAT =
            DateTimeFormatter.ofPattern("dd MMM yyyy");

    public byte[] generateInvoicePdf(Invoice invoice) {
        log.info("Generating PDF for invoice: {}", invoice.getInvoiceNumber());

        try {
            ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
            Document document = new Document(PageSize.A4, 50, 50, 50, 50);
            PdfWriter.getInstance(document, outputStream);

            document.open();
            addHeader(document, invoice);
            addCustomerAndInvoiceDetails(document, invoice);
            addLineItemsTable(document, invoice);
            addTotalSection(document, invoice);
            addFooter(document);
            document.close();

            log.info("PDF generated successfully for invoice: {}",
                    invoice.getInvoiceNumber());
            return outputStream.toByteArray();

        } catch (DocumentException e) {
            log.error("Failed to generate PDF for invoice: {}",
                    invoice.getInvoiceNumber(), e);
            throw new RuntimeException("Failed to generate PDF", e);
        }
    }

    private void addHeader(Document document, Invoice invoice) throws DocumentException {
        Font companyFont = FontFactory.getFont(FontFactory.HELVETICA_BOLD, 22,
                new BaseColor(24, 95, 165));
        Font taglineFont = FontFactory.getFont(FontFactory.HELVETICA, 10,
                BaseColor.GRAY);
        Font invoiceTitleFont = FontFactory.getFont(FontFactory.HELVETICA_BOLD,
                28, BaseColor.WHITE);

        Paragraph company = new Paragraph("Invoice Manager", companyFont);
        company.setAlignment(Element.ALIGN_LEFT);
        document.add(company);

        Paragraph tagline = new Paragraph("Billing & Invoice Management System",
                taglineFont);
        tagline.setAlignment(Element.ALIGN_LEFT);
        tagline.setSpacingAfter(20);
        document.add(tagline);

        PdfPTable headerTable = new PdfPTable(1);
        headerTable.setWidthPercentage(100);

        PdfPCell invoiceCell = new PdfPCell(
                new Phrase("INVOICE", invoiceTitleFont));
        invoiceCell.setBackgroundColor(HEADER_COLOR);
        invoiceCell.setPadding(15);
        invoiceCell.setHorizontalAlignment(Element.ALIGN_CENTER);
        invoiceCell.setBorder(Rectangle.NO_BORDER);
        headerTable.addCell(invoiceCell);

        document.add(headerTable);
        document.add(Chunk.NEWLINE);
    }

    private void addCustomerAndInvoiceDetails(Document document, Invoice invoice)
            throws DocumentException {
        Font labelFont = FontFactory.getFont(FontFactory.HELVETICA_BOLD, 10,
                BaseColor.DARK_GRAY);
        Font valueFont = FontFactory.getFont(FontFactory.HELVETICA, 10,
                BaseColor.BLACK);
        Font sectionFont = FontFactory.getFont(FontFactory.HELVETICA_BOLD, 11,
                BaseColor.WHITE);

        PdfPTable detailsTable = new PdfPTable(2);
        detailsTable.setWidthPercentage(100);
        detailsTable.setSpacingAfter(20);

        // Bill To section
        PdfPCell billToHeader = new PdfPCell(new Phrase("BILL TO", sectionFont));
        billToHeader.setBackgroundColor(HEADER_COLOR);
        billToHeader.setPadding(8);
        billToHeader.setBorder(Rectangle.NO_BORDER);
        detailsTable.addCell(billToHeader);

        // Invoice Details section
        PdfPCell invoiceDetailsHeader = new PdfPCell(
                new Phrase("INVOICE DETAILS", sectionFont));
        invoiceDetailsHeader.setBackgroundColor(HEADER_COLOR);
        invoiceDetailsHeader.setPadding(8);
        invoiceDetailsHeader.setBorder(Rectangle.NO_BORDER);
        detailsTable.addCell(invoiceDetailsHeader);

        // Customer info
        PdfPCell customerInfo = new PdfPCell();
        customerInfo.setPadding(10);
        customerInfo.setBorder(Rectangle.BOX);
        customerInfo.addElement(new Phrase(invoice.getCustomer().getName(),
                labelFont));
        if (invoice.getCustomer().getCity() != null) {
            customerInfo.addElement(new Phrase(invoice.getCustomer().getCity(),
                    valueFont));
        }
        if (invoice.getCustomer().getCountry() != null) {
            customerInfo.addElement(new Phrase(invoice.getCustomer().getCountry(),
                    valueFont));
        }
        customerInfo.addElement(new Phrase(invoice.getCustomer().getEmail(),
                valueFont));
        detailsTable.addCell(customerInfo);

        // Invoice metadata
        PdfPCell invoiceMeta = new PdfPCell();
        invoiceMeta.setPadding(10);
        invoiceMeta.setBorder(Rectangle.BOX);
        invoiceMeta.addElement(new Phrase("Invoice Number: " +
                invoice.getInvoiceNumber(), labelFont));
        invoiceMeta.addElement(new Phrase("Status: " +
                invoice.getStatus(), valueFont));
        invoiceMeta.addElement(new Phrase("Issue Date: " +
                invoice.getIssueDate().format(DATE_FORMAT), valueFont));
        invoiceMeta.addElement(new Phrase("Due Date: " +
                invoice.getDueDate().format(DATE_FORMAT), valueFont));
        invoiceMeta.addElement(new Phrase("Created By: " +
                invoice.getCreatedBy().getFullName(), valueFont));
        detailsTable.addCell(invoiceMeta);

        document.add(detailsTable);
    }

    private void addLineItemsTable(Document document, Invoice invoice)
            throws DocumentException {
        Font headerFont = FontFactory.getFont(FontFactory.HELVETICA_BOLD, 10,
                BaseColor.WHITE);
        Font cellFont = FontFactory.getFont(FontFactory.HELVETICA, 10,
                BaseColor.BLACK);

        PdfPTable table = new PdfPTable(new float[]{3, 1, 1.5f, 1.5f});
        table.setWidthPercentage(100);
        table.setSpacingAfter(10);

        String[] headers = {"Description", "Quantity", "Unit Price", "Total Price"};
        for (String header : headers) {
            PdfPCell cell = new PdfPCell(new Phrase(header, headerFont));
            cell.setBackgroundColor(HEADER_COLOR);
            cell.setPadding(8);
            cell.setHorizontalAlignment(Element.ALIGN_CENTER);
            cell.setBorder(Rectangle.NO_BORDER);
            table.addCell(cell);
        }

        boolean alternate = false;
        for (InvoiceLineItem item : invoice.getLineItems()) {
            BaseColor rowColor = alternate ? LIGHT_GRAY : BaseColor.WHITE;

            PdfPCell descCell = new PdfPCell(
                    new Phrase(item.getDescription(), cellFont));
            descCell.setPadding(8);
            descCell.setBackgroundColor(rowColor);
            table.addCell(descCell);

            PdfPCell qtyCell = new PdfPCell(
                    new Phrase(item.getQuantity().stripTrailingZeros()
                            .toPlainString(), cellFont));
            qtyCell.setPadding(8);
            qtyCell.setHorizontalAlignment(Element.ALIGN_CENTER);
            qtyCell.setBackgroundColor(rowColor);
            table.addCell(qtyCell);

            PdfPCell unitCell = new PdfPCell(
                    new Phrase("₹ " + item.getUnitPrice(), cellFont));
            unitCell.setPadding(8);
            unitCell.setHorizontalAlignment(Element.ALIGN_RIGHT);
            unitCell.setBackgroundColor(rowColor);
            table.addCell(unitCell);

            PdfPCell totalCell = new PdfPCell(
                    new Phrase("₹ " + item.getTotalPrice(), cellFont));
            totalCell.setPadding(8);
            totalCell.setHorizontalAlignment(Element.ALIGN_RIGHT);
            totalCell.setBackgroundColor(rowColor);
            table.addCell(totalCell);

            alternate = !alternate;
        }

        document.add(table);
    }

    private void addTotalSection(Document document, Invoice invoice)
            throws DocumentException {
        Font totalLabelFont = FontFactory.getFont(FontFactory.HELVETICA_BOLD,
                12, BaseColor.WHITE);
        Font totalValueFont = FontFactory.getFont(FontFactory.HELVETICA_BOLD,
                12, BaseColor.WHITE);

        PdfPTable totalTable = new PdfPTable(new float[]{3, 1.5f});
        totalTable.setWidthPercentage(100);
        totalTable.setSpacingAfter(20);

        PdfPCell emptyCell = new PdfPCell(new Phrase(""));
        emptyCell.setBorder(Rectangle.NO_BORDER);
        totalTable.addCell(emptyCell);

        PdfPCell totalCell = new PdfPCell();
        totalCell.setBackgroundColor(HEADER_COLOR);
        totalCell.setPadding(10);
        totalCell.setBorder(Rectangle.NO_BORDER);
        totalCell.addElement(new Phrase("TOTAL AMOUNT", totalLabelFont));
        totalCell.addElement(new Phrase("₹ " + invoice.getTotalAmount(),
                totalValueFont));
        totalTable.addCell(totalCell);

        document.add(totalTable);

        if (invoice.getNotes() != null && !invoice.getNotes().isEmpty()) {
            Font notesLabelFont = FontFactory.getFont(FontFactory.HELVETICA_BOLD,
                    10, BaseColor.DARK_GRAY);
            Font notesFont = FontFactory.getFont(FontFactory.HELVETICA, 10,
                    BaseColor.DARK_GRAY);
            document.add(new Paragraph("Notes:", notesLabelFont));
            document.add(new Paragraph(invoice.getNotes(), notesFont));
        }
    }

    private void addFooter(Document document) throws DocumentException {
        Font footerFont = FontFactory.getFont(FontFactory.HELVETICA, 9,
                BaseColor.GRAY);
        Paragraph footer = new Paragraph(
                "Thank you for your business. Please make payment by the due date.",
                footerFont);
        footer.setAlignment(Element.ALIGN_CENTER);
        footer.setSpacingBefore(30);
        document.add(footer);
    }
}