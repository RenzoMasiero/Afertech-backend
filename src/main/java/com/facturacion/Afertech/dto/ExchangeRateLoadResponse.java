package com.facturacion.Afertech.dto;

import java.math.BigDecimal;
import java.time.LocalDate;

public class ExchangeRateLoadResponse {

    private LocalDate date;
    private BigDecimal usdArsRate;

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public BigDecimal getUsdArsRate() {
        return usdArsRate;
    }

    public void setUsdArsRate(BigDecimal usdArsRate) {
        this.usdArsRate = usdArsRate;
    }
}
