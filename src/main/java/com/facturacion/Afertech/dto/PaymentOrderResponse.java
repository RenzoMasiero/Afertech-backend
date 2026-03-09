package com.facturacion.Afertech.dto;

import com.facturacion.Afertech.model.Currency;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Getter
@Setter
public class PaymentOrderResponse {

    private Long id;

    private Long clientId;
    private String clientName;

    private String paymentOrderNumber;
    private LocalDate issueDate;

    private boolean executed;
    private LocalDate executionDate;

    private LocalDateTime loadedAt;
    private String loadedBy;

    private Long projectId;
    private String projectName;

    private BigDecimal totalWithoutTax;
    private BigDecimal totalWithTax;

    // 💵 Modelo monetario
    private Currency currencyOriginal;
    private BigDecimal exchangeRateUsed;
    private BigDecimal totalWithoutTaxUsd;
    private BigDecimal totalWithTaxUsd;

    private String concept;

    private Long invoiceId;
    private String invoiceNumber;

    private Long purchaseOrderId;
    private String purchaseOrderNumber;

    private BigDecimal withholdings;
}