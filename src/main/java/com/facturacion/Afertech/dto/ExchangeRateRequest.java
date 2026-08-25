package com.facturacion.Afertech.dto;

import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDate;

@Getter
@Setter
public class ExchangeRateRequest {

    @NotNull
    private LocalDate date;

    private BigDecimal usdArsRate;
}
