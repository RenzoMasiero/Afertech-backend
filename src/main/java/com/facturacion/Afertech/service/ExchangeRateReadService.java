package com.facturacion.Afertech.service;

import com.facturacion.Afertech.dto.ExchangeRateResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface ExchangeRateReadService {

    Page<ExchangeRateResponse> findAll(Pageable pageable);

    ExchangeRateResponse findById(Long id);
}
