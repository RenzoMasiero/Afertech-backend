package com.facturacion.Afertech.service;

import com.facturacion.Afertech.dto.ExchangeRateLoadResponse;

import java.time.LocalDate;
import java.util.List;

public interface ExchangeRateHistoricalService {

    List<ExchangeRateLoadResponse> loadFromTo(LocalDate start, LocalDate end);
}
