package com.facturacion.Afertech.service;

import com.facturacion.Afertech.dto.ExchangeRateLoadResponse;

import java.time.LocalDate;

public interface ExchangeRateHistoricalService {

    void loadFromTo(LocalDate start, LocalDate end);
}
