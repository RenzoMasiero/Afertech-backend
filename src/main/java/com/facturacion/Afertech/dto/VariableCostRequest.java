package com.facturacion.Afertech.dto;

import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDate;

@Getter
@Setter
public class VariableCostRequest {

    @NotNull
    private Long costTypeId;

    @NotNull
    private BigDecimal amount;

    // Mes de imputación en formato YYYY-MM
    @NotNull
    private String allocationMonth;

    @NotNull
    private LocalDate paymentDate;

    @NotNull
    private Long supplierId;

    private String description;

    // opcional
    private Long projectId;
}
