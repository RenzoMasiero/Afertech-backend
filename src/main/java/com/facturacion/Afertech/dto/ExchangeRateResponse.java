package com.facturacion.Afertech.dto;

import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Getter
@Setter
public class ExchangeRateResponse {

    private Long id;
    private LocalDate date;
    private BigDecimal usdArsRate;
    private LocalDateTime loadedAt;
    private String loadedBy;
}
