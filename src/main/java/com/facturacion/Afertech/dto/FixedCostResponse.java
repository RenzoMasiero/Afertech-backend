package com.facturacion.Afertech.dto;

import com.facturacion.Afertech.model.Currency;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Getter
@Setter
public class FixedCostResponse {

    private Long id;

    private Long costTypeId;
    private String costTypeName;

    private Long employeeId;
    private String employeeName;

    private BigDecimal amount;

    // 💵 Modelo monetario
    private Currency currencyOriginal;
    private BigDecimal exchangeRateUsed;
    private BigDecimal amountUsd;

    private String allocationMonth;
    private LocalDate paymentDate;
    private String description;

    private LocalDateTime loadedAt;
    private String loadedBy;
}