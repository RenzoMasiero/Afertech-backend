package com.facturacion.Afertech.repository;

import com.facturacion.Afertech.model.ExchangeRate;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.util.Optional;

public interface ExchangeRateRepository extends JpaRepository<ExchangeRate, Long> {

    // Exact match (usado por getByDate)
    Optional<ExchangeRate> findByDateAndDeletedAtIsNull(LocalDate date);

    // Optimizado: última anterior o igual (con soft delete)
    Optional<ExchangeRate>
    findTopByDateLessThanEqualAndDeletedAtIsNullOrderByDateDesc(LocalDate date);
}