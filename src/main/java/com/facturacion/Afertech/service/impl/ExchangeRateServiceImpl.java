package com.facturacion.Afertech.service.impl;

import com.facturacion.Afertech.model.ExchangeRate;
import com.facturacion.Afertech.repository.ExchangeRateRepository;
import com.facturacion.Afertech.service.ExchangeRateService;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;

@Service
public class ExchangeRateServiceImpl implements ExchangeRateService {

    private static final long MAX_TOLERANCE_DAYS = 5;

    private final ExchangeRateRepository exchangeRateRepository;

    public ExchangeRateServiceImpl(ExchangeRateRepository exchangeRateRepository) {
        this.exchangeRateRepository = exchangeRateRepository;
    }

    @Override
    public ExchangeRate getByDate(LocalDate date) {

        // 1️⃣ Exact match
        return exchangeRateRepository
                .findByDateAndDeletedAtIsNull(date)
                .orElseGet(() -> resolveLatestBeforeOrEqual(date));
    }

    @Override
    public ExchangeRate getLatestBeforeOrEqual(LocalDate date) {
        return resolveLatestBeforeOrEqual(date);
    }

    private ExchangeRate resolveLatestBeforeOrEqual(LocalDate date) {

        ExchangeRate rate = exchangeRateRepository
                .findTopByDateLessThanEqualAndDeletedAtIsNullOrderByDateDesc(date)
                .orElseThrow(() ->
                        new RuntimeException("No exchange rate available before or equal to date: " + date)
                );

        long daysBetween = ChronoUnit.DAYS.between(rate.getDate(), date);

        if (daysBetween > MAX_TOLERANCE_DAYS) {
            throw new IllegalStateException(
                    "Exchange rate is older than allowed tolerance (" + MAX_TOLERANCE_DAYS + " days)"
            );
        }

        if (rate.getUsdArsRate() == null) {
            throw new IllegalStateException(
                    "Exchange rate is marked as unavailable for date: " + rate.getDate()
            );
        }

        return rate;
    }
}