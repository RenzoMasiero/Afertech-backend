package com.facturacion.Afertech.scheduler;

import com.facturacion.Afertech.model.Invoice;
import com.facturacion.Afertech.repository.InvoiceRepository;
import com.facturacion.Afertech.service.EmailService;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.time.*;
import java.util.List;

@Component
public class InvoiceReminderScheduler {

    private final InvoiceRepository invoiceRepository;
    private final EmailService emailService;

    public InvoiceReminderScheduler(
            InvoiceRepository invoiceRepository,
            EmailService emailService
    ) {
        this.invoiceRepository = invoiceRepository;
        this.emailService = emailService;
    }

    @Scheduled(cron = "0 0 9 * * ?", zone = "America/Argentina/Buenos_Aires")
    public void sendInvoiceReminders() {
        executeReminderLogic();
    }

    public void runNowForTest() {
        executeReminderLogic();
    }

    private void executeReminderLogic() {

        ZoneId zone = ZoneId.of("America/Argentina/Buenos_Aires");

        LocalDate today = LocalDate.now(zone);

        System.out.println("===== INVOICE REMINDER DEBUG =====");
        System.out.println("Hoy: " + today);

        List<Invoice> invoices = invoiceRepository.findAll();

        System.out.println("Facturas encontradas: " + invoices.size());

        for (Invoice invoice : invoices) {

            System.out.println("----------------------------------");
            System.out.println("Factura: " + invoice.getInvoiceNumber());

            if (invoice.getDeferredPaymentDays() == null) {
                System.out.println("No tiene días diferidos");
                continue;
            }

            LocalDate dueDate =
                    invoice.getIssueDate().plusDays(invoice.getDeferredPaymentDays());

            System.out.println("IssueDate: " + invoice.getIssueDate());
            System.out.println("DeferredDays: " + invoice.getDeferredPaymentDays());
            System.out.println("DueDate calculado: " + dueDate);

            if (!dueDate.equals(today)) {
                System.out.println("No vence hoy");
                continue;
            }

            LocalDateTime sendDateTime =
                    dueDate.atTime(9, 0);

            LocalDateTime loadedAt = invoice.getLoadedAt();

            System.out.println("LoadedAt: " + loadedAt);
            System.out.println("SendDateTime: " + sendDateTime);

            if (loadedAt == null) {
                System.out.println("LoadedAt es null");
                continue;
            }

            Duration difference =
                    Duration.between(loadedAt, sendDateTime);

            System.out.println("Horas de diferencia: " + difference.toHours());

            if (difference.toHours() < 24) {
                System.out.println("No cumple 24 horas");
                continue;
            }

            System.out.println(">>> ENVIANDO RECORDATORIO <<<");

            emailService.sendInvoiceReminder(
                    invoice.getClient().getName(),
                    invoice.getInvoiceNumber(),
                    invoice.getIssueDate(),
                    dueDate,
                    invoice.getTotalWithTax(),
                    invoice.getDeferredPaymentDays()
            );
        }

        System.out.println("===== FIN DEBUG =====");
    }
}
