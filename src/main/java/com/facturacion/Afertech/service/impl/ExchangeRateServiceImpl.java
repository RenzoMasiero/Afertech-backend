package com.facturacion.Afertech.service.impl;

import com.facturacion.Afertech.dto.ExchangeRateRequest;
import com.facturacion.Afertech.dto.ExchangeRateResponse;
import com.facturacion.Afertech.mapper.ExchangeRateMapper;
import com.facturacion.Afertech.model.ExchangeRate;
import com.facturacion.Afertech.repository.ExchangeRateRepository;
import com.facturacion.Afertech.service.ExchangeRateService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;

@Service
public class ExchangeRateServiceImpl implements ExchangeRateService {

    private static final long MAX_TOLERANCE_DAYS = 5;

    private final ExchangeRateRepository exchangeRateRepository;
    private final ExchangeRateMapper mapper;

    public ExchangeRateServiceImpl(
            ExchangeRateRepository exchangeRateRepository,
            ExchangeRateMapper mapper
    ) {
        this.exchangeRateRepository = exchangeRateRepository;
        this.mapper = mapper;
    }

    @Override
    public Page<ExchangeRateResponse> findAll(Pageable pageable) {
        return exchangeRateRepository.findAll(pageable)
                .map(mapper::toResponse);
    }

    @Override
    public ExchangeRateResponse findById(Long id) {
        ExchangeRate exchangeRate = exchangeRateRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Exchange rate not found"));
        return mapper.toResponse(exchangeRate);
    }

    @Override
    public ExchangeRateResponse create(ExchangeRateRequest request) {
        ExchangeRate exchangeRate = mapper.toEntity(request);
        return mapper.toResponse(exchangeRateRepository.save(exchangeRate));
    }

    @Override
    public ExchangeRateResponse update(Long id, ExchangeRateRequest request) {
        ExchangeRate exchangeRate = exchangeRateRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Exchange rate not found"));

        exchangeRate.setDate(request.getDate());
        exchangeRate.setUsdArsRate(request.getUsdArsRate());

        return mapper.toResponse(exchangeRateRepository.save(exchangeRate));
    }

    @Override
    public void delete(Long id) {
        ExchangeRate exchangeRate = exchangeRateRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Exchange rate not found"));

        exchangeRate.setDeletedAt(LocalDateTime.now());
        exchangeRate.setDeletedBy(
                SecurityContextHolder.getContext()
                        .getAuthentication()
                        .getName()
        );

        exchangeRateRepository.save(exchangeRate);
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
