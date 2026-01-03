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

    // solo si SUELDO
    private Long employeeId;

    @NotNull
    private BigDecimal amount;

    @NotNull
    private LocalDate allocationMonth;

    @NotNull
    private LocalDate paymentDate;

    private String description;
}
