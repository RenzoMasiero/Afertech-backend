package com.facturacion.Afertech.dto;

import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDate;

@Getter
@Setter
public class FixedCostRequest {

    @NotNull
    private Long costTypeId;

    @NotNull
    private BigDecimal amount;

    // Mes al que se imputa el costo (ej: 2025-01-01)
    @NotNull
    private LocalDate allocationMonth;

    // Fecha real de pago
    @NotNull
    private LocalDate paymentDate;

    private String description;
}

