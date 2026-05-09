package com.facturacion.Afertech.controller;

import com.facturacion.Afertech.dto.ExchangeRateResponse;
import com.facturacion.Afertech.dto.PageResponse;
import com.facturacion.Afertech.service.ExchangeRateReadService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/exchange-rates")
public class ExchangeRateController {

    private final ExchangeRateReadService service;

    public ExchangeRateController(ExchangeRateReadService service) {
        this.service = service;
    }

    @GetMapping
    public ResponseEntity<PageResponse<ExchangeRateResponse>> findAll(Pageable pageable) {

        Page<ExchangeRateResponse> page = service.findAll(pageable);

        PageResponse<ExchangeRateResponse> response = new PageResponse<>(
                page.getContent(),
                page.getNumber(),
                page.getSize(),
                page.getTotalElements(),
                page.getTotalPages()
        );

        return ResponseEntity.ok(response);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ExchangeRateResponse> findById(@PathVariable Long id) {
        return ResponseEntity.ok(service.findById(id));
    }
}
