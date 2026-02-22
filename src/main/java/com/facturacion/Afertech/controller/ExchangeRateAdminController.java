package com.facturacion.Afertech.controller;

import com.facturacion.Afertech.service.ExchangeRateHistoricalService;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;

@RestController
@RequestMapping("/admin/exchange-rate")
public class ExchangeRateAdminController {

    private final ExchangeRateHistoricalService historicalService;

    public ExchangeRateAdminController(ExchangeRateHistoricalService historicalService) {
        this.historicalService = historicalService;
    }

    @PostMapping("/load-historical")
    @PreAuthorize("hasRole('ADMIN')")
    public void loadHistorical(
            @RequestParam LocalDate start,
            @RequestParam LocalDate end
    ) {
        historicalService.loadFromTo(start, end);
    }
}