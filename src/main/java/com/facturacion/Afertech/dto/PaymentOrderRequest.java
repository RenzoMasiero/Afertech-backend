package com.facturacion.Afertech.dto;

import com.facturacion.Afertech.model.Currency;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDate;

@Getter
@Setter
public class PaymentOrderRequest {

    @NotNull
    private Long clientId;

    @NotNull
    private String paymentOrderNumber;

    @NotNull
    private LocalDate issueDate;

    @NotNull
    private Long projectId;

    @NotNull
    private BigDecimal totalWithoutTax;

    @NotNull
    private BigDecimal totalWithTax;

    @NotNull
    private Currency currencyOriginal;

    private String concept;

    private Long invoiceId;
    private Long purchaseOrderId;

    private BigDecimal withholdings;

    @NotNull
    private Boolean executed;

    private LocalDate executionDate;
}