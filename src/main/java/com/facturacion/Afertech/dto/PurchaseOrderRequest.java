package com.facturacion.Afertech.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDate;

@Getter
@Setter
public class PurchaseOrderRequest {

    @NotBlank
    private String company;

    @NotBlank
    private String purchaseOrderNumber;

    @NotNull
    private LocalDate issueDate;

    private String projectNumber;

    @NotNull
    private BigDecimal totalWithoutTax;

    @NotNull
    private BigDecimal totalWithTax;

    private String description;
}
