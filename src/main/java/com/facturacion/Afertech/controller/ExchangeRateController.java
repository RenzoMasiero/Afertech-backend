package com.facturacion.Afertech.controller;

import com.facturacion.Afertech.dto.ExchangeRateRequest;
import com.facturacion.Afertech.dto.ExchangeRateResponse;
import com.facturacion.Afertech.dto.PageResponse;
import com.facturacion.Afertech.service.ExchangeRateService;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/exchange-rates")
@PreAuthorize("hasRole('ADMIN')")
public class ExchangeRateController {

    private final ExchangeRateService service;

    public ExchangeRateController(ExchangeRateService service) {
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

    @PostMapping
    public ResponseEntity<ExchangeRateResponse> create(@Valid @RequestBody ExchangeRateRequest request) {
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(service.create(request));
    }

    @PutMapping("/{id}")
    public ResponseEntity<ExchangeRateResponse> update(
            @PathVariable Long id,
            @Valid @RequestBody ExchangeRateRequest request
    ) {
        return ResponseEntity.ok(service.update(id, request));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        service.delete(id);
        return ResponseEntity.noContent().build();
    }
}
