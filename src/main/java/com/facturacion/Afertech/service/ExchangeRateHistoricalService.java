package com.facturacion.Afertech.service;

import java.time.LocalDate;

public interface ExchangeRateHistoricalService {

    void loadFromTo(LocalDate start, LocalDate end);
}
