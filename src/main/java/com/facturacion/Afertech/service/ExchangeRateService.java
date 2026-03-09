package com.facturacion.Afertech.service;

import com.facturacion.Afertech.model.ExchangeRate;

import java.time.LocalDate;

public interface ExchangeRateService {

    ExchangeRate getByDate(LocalDate date);

    ExchangeRate getLatestBeforeOrEqual(LocalDate date);

}