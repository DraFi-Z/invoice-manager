package com.billing.invoice_manager.service;

import com.billing.invoice_manager.entity.Invoice;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class EmailService {

    private final JavaMailSender mailSender;
    private final PdfService pdfService;

    @Value("${spring.mail.from}")
    private String fromEmail;

    public EmailService(JavaMailSender mailSender, PdfService pdfService) {
        this.mailSender = mailSender;
        this.pdfService = pdfService;
    }

    @Async
    public void sendInvoiceEmail(Invoice invoice, String customerEmail, String customerName) {
        log.info("Sending invoice email for: {} to: {}", invoice.getInvoiceNumber(), customerEmail);
        try {
            MimeMessage message = mailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(message, true);

            helper.setFrom(fromEmail);
            helper.setTo(customerEmail);
            helper.setSubject("Invoice " + invoice.getInvoiceNumber() + " from Invoice Manager");
            helper.setText(buildEmailBody(invoice, customerName), true);

            byte[] pdfBytes = pdfService.generateInvoicePdf(invoice);
            helper.addAttachment(invoice.getInvoiceNumber() + ".pdf",
                    () -> new java.io.ByteArrayInputStream(pdfBytes));

            mailSender.send(message);
            log.info("Invoice email sent successfully for: {}", invoice.getInvoiceNumber());

        } catch (MessagingException e) {
            log.error("Failed to send invoice email for: {}", invoice.getInvoiceNumber(), e);
        }
    }

    @Async
    public void sendOverdueNotification(Invoice invoice) {
        log.info("Sending overdue notification for: {}",
                invoice.getInvoiceNumber());
        try {
            MimeMessage message = mailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(message, true);

            helper.setFrom(fromEmail);
            helper.setSubject("Payment Overdue - Invoice "
                    + invoice.getInvoiceNumber());
            helper.setText(buildOverdueEmailBody(invoice), true);

            mailSender.send(message);
            log.info("Overdue notification sent for: {}",
                    invoice.getInvoiceNumber());

        } catch (MessagingException e) {
            log.error("Failed to send overdue notification for: {}",
                    invoice.getInvoiceNumber(), e);
        }
    }

    private String buildEmailBody(Invoice invoice, String customerName) {
        return """
            <html>
            <body style="font-family: Arial, sans-serif; color: #333;">
                <h2 style="color: #185fa5;">Invoice Manager</h2>
                <p>Dear %s,</p>
                <p>Please find attached your invoice <strong>%s</strong>.</p>
                <table style="border-collapse: collapse; width: 100%%;">
                    <tr>
                        <td style="padding: 8px; border: 1px solid #ddd;">
                            <strong>Invoice Number</strong></td>
                        <td style="padding: 8px; border: 1px solid #ddd;">%s</td>
                    </tr>
                    <tr>
                        <td style="padding: 8px; border: 1px solid #ddd;">
                            <strong>Issue Date</strong></td>
                        <td style="padding: 8px; border: 1px solid #ddd;">%s</td>
                    </tr>
                    <tr>
                        <td style="padding: 8px; border: 1px solid #ddd;">
                            <strong>Due Date</strong></td>
                        <td style="padding: 8px; border: 1px solid #ddd;">%s</td>
                    </tr>
                    <tr style="background-color: #185fa5; color: white;">
                        <td style="padding: 8px; border: 1px solid #ddd;">
                            <strong>Total Amount</strong></td>
                        <td style="padding: 8px; border: 1px solid #ddd;">₹%s</td>
                    </tr>
                </table>
                <p>Please make payment by the due date.</p>
                <p>Thank you for your business.</p>
                <p style="color: #888; font-size: 12px;">Invoice Manager System</p>
            </body>
            </html>
            """.formatted(
                customerName,
                invoice.getInvoiceNumber(),
                invoice.getInvoiceNumber(),
                invoice.getIssueDate(),
                invoice.getDueDate(),
                invoice.getTotalAmount()
        );
    }

    private String buildOverdueEmailBody(Invoice invoice) {
        return """
                <html>
                <body style="font-family: Arial, sans-serif; color: #333;">
                    <h2 style="color: #e24b4a;">Payment Overdue Notice</h2>
                    <p>Dear %s,</p>
                    <p>This is a reminder that invoice <strong>%s</strong>
                    is overdue for payment.</p>
                    <table style="border-collapse: collapse; width: 100%%;">
                        <tr>
                            <td style="padding: 8px; border: 1px solid #ddd;">
                                <strong>Invoice Number</strong></td>
                            <td style="padding: 8px; border: 1px solid #ddd;">
                                %s</td>
                        </tr>
                        <tr style="background-color: #e24b4a; color: white;">
                            <td style="padding: 8px; border: 1px solid #ddd;">
                                <strong>Amount Due</strong></td>
                            <td style="padding: 8px; border: 1px solid #ddd;">
                                ₹%s</td>
                        </tr>
                    </table>
                    <p>Please arrange payment immediately to avoid further action.</p>
                    <p style="color: #888; font-size: 12px;">Invoice Manager System</p>
                </body>
                </html>
                """.formatted(
                invoice.getCustomer().getName(),
                invoice.getInvoiceNumber(),
                invoice.getInvoiceNumber(),
                invoice.getTotalAmount()
        );
    }
}