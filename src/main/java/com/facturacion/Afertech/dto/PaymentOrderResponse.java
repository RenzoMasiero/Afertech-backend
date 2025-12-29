package com.facturacion.Afertech.dto;

import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Getter
@Setter
public class PaymentOrderResponse {

    private Long id;
    private String company;
    private String paymentOrderNumber;
    private LocalDate issueDate;

    // Auditoría (ya estaba)
    private LocalDateTime createdAt;

    // Fecha de carga funcional
    private LocalDateTime loadedAt;

    private String projectNumber;
    private BigDecimal totalWithoutTax;
    private BigDecimal totalWithTax;
    private String concept;
    private String invoiceNumber;
    private String purchaseOrderNumber;
    private BigDecimal withholdings;
}
