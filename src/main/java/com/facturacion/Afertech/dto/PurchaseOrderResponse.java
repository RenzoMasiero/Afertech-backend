package com.facturacion.Afertech.dto;

import com.facturacion.Afertech.model.Currency;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Getter
@Setter
public class PurchaseOrderResponse {

    private Long id;

    private Long clientId;
    private String clientName;

    private String purchaseOrderNumber;
    private LocalDate issueDate;

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

    private String description;
}