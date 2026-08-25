package com.facturacion.Afertech.service;

import com.facturacion.Afertech.dto.ExchangeRateRequest;
import com.facturacion.Afertech.dto.ExchangeRateResponse;
import com.facturacion.Afertech.model.ExchangeRate;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.time.LocalDate;

public interface ExchangeRateService {

    Page<ExchangeRateResponse> findAll(Pageable pageable);

    ExchangeRateResponse findById(Long id);

    ExchangeRateResponse create(ExchangeRateRequest request);

    ExchangeRateResponse update(Long id, ExchangeRateRequest request);

    void delete(Long id);

    ExchangeRate getByDate(LocalDate date);

    ExchangeRate getLatestBeforeOrEqual(LocalDate date);

}
