package com.facturacion.Afertech.scheduler;

import com.facturacion.Afertech.model.ExchangeRate;
import com.facturacion.Afertech.repository.ExchangeRateRepository;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;
import java.util.Map;

@Component
public class ExchangeRateScheduler {

    private final ExchangeRateRepository repository;
    private final RestTemplate restTemplate = new RestTemplate();

    public ExchangeRateScheduler(ExchangeRateRepository repository) {
        this.repository = repository;
    }

    // 🏦 PRODUCTIVO (queda así)
    @Scheduled(cron = "0 0 18 * * *", zone = "America/Argentina/Buenos_Aires")
    public void scheduledLoad() {
        loadForDate(LocalDate.now());
    }

    // 🔧 Reutilizable para DEV
    public void loadForDate(LocalDate date) {

        if (repository.findByDateAndDeletedAtIsNull(date).isPresent()) {
            return;
        }

        try {
            String url = "https://api.bluelytics.com.ar/v2/latest";

            Map<String, Object> response =
                    restTemplate.getForObject(url, Map.class);

            Map<String, Object> oficial =
                    (Map<String, Object>) response.get("oficial");

            Double venta = (Double) oficial.get("value_sell");

            ExchangeRate rate = new ExchangeRate();
            rate.setDate(date);
            rate.setUsdArsRate(
                    BigDecimal.valueOf(venta)
                            .setScale(2, RoundingMode.HALF_UP)
            );

            repository.save(rate);

        } catch (Exception ex) {

            ExchangeRate rate = new ExchangeRate();
            rate.setDate(date);
            rate.setUsdArsRate(null);

            repository.save(rate);
        }
    }
}