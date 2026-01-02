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

    // Fecha de carga funcional
    private LocalDateTime loadedAt;

    // Usuario que cargó
    private String loadedBy;

    private String description;
    private BigDecimal totalWithoutTax;
    private BigDecimal totalWithTax;
    private Integer deferredPaymentDays;
    private String projectNumber;
    private String purchaseOrder;
    private Integer purchaseOrderPercentage;
    private String paymentOrder;
}
