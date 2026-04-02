package com.facturacion.Afertech.controller;

import com.facturacion.Afertech.dto.ExchangeRateLoadResponse;
import com.facturacion.Afertech.service.ExchangeRateHistoricalService;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/admin/exchange-rate")
public class ExchangeRateAdminController {

    private final ExchangeRateHistoricalService historicalService;

    public ExchangeRateAdminController(ExchangeRateHistoricalService historicalService) {
        this.historicalService = historicalService;
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
