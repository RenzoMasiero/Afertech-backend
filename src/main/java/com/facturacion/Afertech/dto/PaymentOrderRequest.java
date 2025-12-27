package com.facturacion.Afertech.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDate;

@Getter
@Setter
public class PaymentOrderRequest {

    @NotBlank
    private String company;

    @NotBlank
    private String paymentOrderNumber;

    @NotNull
    private LocalDate issueDate;

    private String projectNumber;

    @NotNull
    private BigDecimal totalWithoutTax;

    @NotNull
    private BigDecimal totalWithTax;

    private String concept;
    private String invoiceNumber;
    private String purchaseOrderNumber;
    private BigDecimal withholdings;
}

