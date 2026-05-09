package com.facturacion.Afertech.service.impl;

import com.facturacion.Afertech.dto.ExchangeRateResponse;
import com.facturacion.Afertech.mapper.ExchangeRateMapper;
import com.facturacion.Afertech.model.ExchangeRate;
import com.facturacion.Afertech.repository.ExchangeRateRepository;
import com.facturacion.Afertech.service.ExchangeRateReadService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class ExchangeRateReadServiceImpl implements ExchangeRateReadService {

    private final ExchangeRateRepository repository;
    private final ExchangeRateMapper mapper;

    public ExchangeRateReadServiceImpl(
            ExchangeRateRepository repository,
            ExchangeRateMapper mapper
    ) {
        this.repository = repository;
        this.mapper = mapper;
    }

    @Override
    public Page<ExchangeRateResponse> findAll(Pageable pageable) {
        return repository.findAll(pageable)
                .map(mapper::toResponse);
    }

    @Override
    public ExchangeRateResponse findById(Long id) {
        ExchangeRate exchangeRate = repository.findById(id)
                .orElseThrow(() -> new RuntimeException("Exchange rate not found"));

        return mapper.toResponse(exchangeRate);
    }
}
