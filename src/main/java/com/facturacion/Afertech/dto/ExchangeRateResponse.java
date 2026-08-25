package com.facturacion.Afertech.dto;

import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDate;

@Getter
@Setter
public class ExchangeRateResponse {

    private Long id;
    private LocalDate date;
    private BigDecimal usdArsRate;
}
