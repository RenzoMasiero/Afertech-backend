package com.facturacion.Afertech.dto;

import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Getter
@Setter
public class InvoiceResponse {

    private Long id;
    private String company;
    private String invoiceNumber;
    private LocalDate issueDate;

    // Auditoría (ya estaba, no se toca)
    private LocalDateTime createdAt;

    // Fecha de carga funcional (NUEVO)
    private LocalDateTime loadedAt;

    private String description;
    private BigDecimal totalWithoutTax;
    private BigDecimal totalWithTax;
    private Integer deferredPaymentDays;
    private String projectNumber;
    private String purchaseOrder;
    private Integer purchaseOrderPercentage;
    private String paymentOrder;
}
