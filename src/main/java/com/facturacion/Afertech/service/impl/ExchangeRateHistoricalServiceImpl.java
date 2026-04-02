package com.facturacion.Afertech.service.impl;

import com.facturacion.Afertech.model.ExchangeRate;
import com.facturacion.Afertech.repository.ExchangeRateRepository;
import com.facturacion.Afertech.service.ExchangeRateHistoricalService;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;
import java.util.List;
import java.util.Map;

@Service
public class ExchangeRateHistoricalServiceImpl implements ExchangeRateHistoricalService {

    private static final LocalDate MIN_DATE = LocalDate.of(2024, 1, 1);

    private final ExchangeRateRepository repository;
    private final RestTemplate restTemplate = new RestTemplate();

    public ExchangeRateHistoricalServiceImpl(ExchangeRateRepository repository) {
        this.repository = repository;
    }

    @Override
    public void loadFromTo(LocalDate start, LocalDate end) {

        String url = "https://api.bluelytics.com.ar/v2/evolution.json";

        List<Map<String, Object>> response =
                restTemplate.getForObject(url, List.class);

        if (response == null) {
            throw new RuntimeException("Failed to fetch historical exchange rates.");
        }

        for (Map<String, Object> entry : response) {

            String source = (String) entry.get("source");
            if (!"Oficial".equalsIgnoreCase(source)) {
                continue;
            }

            LocalDate date = LocalDate.parse((String) entry.get("date"));

            // Respeta fecha mínima del sistema
            if (date.isBefore(MIN_DATE)) {
                continue;
            }

            // Respeta rango solicitado
            if (start != null && date.isBefore(start)) {
                continue;
            }

            if (end != null && date.isAfter(end)) {
                continue;
            }

            // Evita duplicados
            if (repository.findByDateAndDeletedAtIsNull(date).isPresent()) {
                continue;
            }

            Double sellValue = (Double) entry.get("value_sell");

            // Protege contra valores nulos del API
            if (sellValue == null) {
                continue;
            }

            ExchangeRate rate = new ExchangeRate();
            rate.setDate(date);
            rate.setUsdArsRate(
                    BigDecimal.valueOf(sellValue)
                            .setScale(2, RoundingMode.HALF_UP)
            );

            repository.save(rate);
        }
    }
}
