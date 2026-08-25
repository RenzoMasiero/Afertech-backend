package com.facturacion.Afertech.controller;

import com.facturacion.Afertech.dto.ExchangeRateLoadResponse;
import com.facturacion.Afertech.dto.ExchangeRateRequest;
import com.facturacion.Afertech.dto.ExchangeRateResponse;
import com.facturacion.Afertech.dto.PageResponse;
import com.facturacion.Afertech.service.ExchangeRateHistoricalService;
import com.facturacion.Afertech.service.ExchangeRateService;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/admin/exchange-rate")
public class ExchangeRateAdminController {

    private final ExchangeRateHistoricalService historicalService;
    private final ExchangeRateService exchangeRateService;

    public ExchangeRateAdminController(
            ExchangeRateHistoricalService historicalService,
            ExchangeRateService exchangeRateService
    ) {
        this.historicalService = historicalService;
        this.exchangeRateService = exchangeRateService;
    }

    @GetMapping
    public ResponseEntity<PageResponse<ExchangeRateResponse>> findAll(Pageable pageable) {
        Page<ExchangeRateResponse> page = exchangeRateService.findAll(pageable);

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
        return ResponseEntity.ok(exchangeRateService.findById(id));
    }

    @PostMapping
    public ResponseEntity<ExchangeRateResponse> create(
            @Valid @RequestBody ExchangeRateRequest request
    ) {
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(exchangeRateService.create(request));
    }

    @PutMapping("/{id}")
    public ResponseEntity<ExchangeRateResponse> update(
            @PathVariable Long id,
            @Valid @RequestBody ExchangeRateRequest request
    ) {
        return ResponseEntity.ok(exchangeRateService.update(id, request));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        exchangeRateService.delete(id);
        return ResponseEntity.noContent().build();
    }

    @PostMapping("/load-historical")
    @PreAuthorize("hasRole('ADMIN')")
    public List<ExchangeRateLoadResponse> loadHistorical(
            @RequestParam LocalDate start,
            @RequestParam LocalDate end
    ) {
        return historicalService.loadFromTo(start, end);
    }
}
