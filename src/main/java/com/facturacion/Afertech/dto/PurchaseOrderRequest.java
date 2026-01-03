package com.facturacion.Afertech.dto;

import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDate;

@Getter
@Setter
public class PurchaseOrderRequest {

    @NotNull
    private Long clientId;

    @NotNull
    private String purchaseOrderNumber;

    @NotNull
    private LocalDate issueDate;

    @NotNull
    private Long projectId;

    @NotNull
    private BigDecimal totalWithoutTax;

    @NotNull
    private BigDecimal totalWithTax;

    private String description;
}
