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

    private Long clientId;
    private String clientName;

    private String invoiceNumber;
    private LocalDate issueDate;

    private LocalDateTime loadedAt;
    private String loadedBy;

    private String description;
    private BigDecimal totalWithoutTax;
    private BigDecimal totalWithTax;
    private Integer deferredPaymentDays;

    private Long projectId;
    private String projectName;

    private Long purchaseOrderId;
    private Integer purchaseOrderPercentage;

    private Long paymentOrderId;
}
