package com.facturacion.Afertech.dto;

import com.facturacion.Afertech.model.Currency;
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

    private Long employeeId;

    @NotNull
    private BigDecimal amount;

    @NotNull
    private Currency currencyOriginal;

    @NotNull
    private String allocationMonth;

    @NotNull
    private LocalDate paymentDate;

    private String description;
}