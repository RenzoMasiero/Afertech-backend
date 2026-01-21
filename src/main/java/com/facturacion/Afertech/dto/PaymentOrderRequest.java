package com.facturacion.Afertech.dto;

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

    private String concept;

    private Long invoiceId;
    private Long purchaseOrderId;

    private BigDecimal withholdings;

    // Estado de ejecución
    @NotNull
    private Boolean executed;

    // Obligatoria si executed = true
    private LocalDate executionDate;
}
