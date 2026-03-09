package com.facturacion.Afertech.service.impl;

import com.facturacion.Afertech.model.ReminderRecipient;
import com.facturacion.Afertech.repository.ReminderRecipientRepository;
import com.facturacion.Afertech.service.EmailService;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

@Service
public class SmtpEmailService implements EmailService {

    private final JavaMailSender mailSender;
    private final ReminderRecipientRepository recipientRepository;

    public SmtpEmailService(
            JavaMailSender mailSender,
            ReminderRecipientRepository recipientRepository
    ) {
        this.mailSender = mailSender;
        this.recipientRepository = recipientRepository;
    }

    @Override
    public void sendEmail(String to, String subject, String body) {

        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(to);
        message.setSubject(subject);
        message.setText(body);

        mailSender.send(message);
    }

    @Override
    public void sendInvoiceReminder(
            String empresa,
            String numeroFactura,
            LocalDate fechaEmision,
            LocalDate fechaVencimiento,
            BigDecimal total,
            Integer diasDiferidos
    ) {

        List<ReminderRecipient> recipients =
                recipientRepository.findAllByActiveTrueAndDeletedAtIsNull();

        if (recipients.isEmpty()) {
            return;
        }

        String subject = "Recordatorio Factura: " + empresa;

        String body = """
                Recordatorio de cobro de factura:

                Empresa: %s
                Número de Factura: %s
                Fecha de Emisión: %s
                Fecha de Vencimiento: %s
                Días Diferidos: %d
                Total: %s
                """.formatted(
                empresa,
                numeroFactura,
                fechaEmision,
                fechaVencimiento,
                diasDiferidos,
                total
        );

        for (ReminderRecipient recipient : recipients) {
            sendEmail(recipient.getEmail(), subject, body);
        }
    }
}
