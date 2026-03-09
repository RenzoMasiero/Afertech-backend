package com.facturacion.Afertech.dto;

import com.facturacion.Afertech.model.Currency;
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

    // 💵 Modelo monetario
    private Currency currencyOriginal;
    private BigDecimal exchangeRateUsed;
    private BigDecimal totalWithoutTaxUsd;
    private BigDecimal totalWithTaxUsd;

    private Integer deferredPaymentDays;

    private Long projectId;
    private String projectName;

    private Long purchaseOrderId;
    private String purchaseOrderNumber;
    private Integer purchaseOrderPercentage;

    private Long paymentOrderId;
    private String paymentOrderNumber;
}