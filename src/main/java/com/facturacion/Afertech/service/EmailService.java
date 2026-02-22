package com.facturacion.Afertech.service;

import java.math.BigDecimal;
import java.time.LocalDate;

public interface EmailService {

    void sendEmail(String to, String subject, String body);

    void sendInvoiceReminder(
            String empresa,
            String numeroFactura,
            LocalDate fechaEmision,
            LocalDate fechaVencimiento,
            BigDecimal total,
            Integer diasDiferidos
    );
}
