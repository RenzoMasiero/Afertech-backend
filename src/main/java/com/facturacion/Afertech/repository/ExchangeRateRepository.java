package com.facturacion.Afertech.repository;

import com.facturacion.Afertech.model.ExchangeRate;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.util.Optional;

public interface ExchangeRateRepository extends JpaRepository<ExchangeRate, Long> {

    Optional<ExchangeRate> findByDateAndDeletedAtIsNull(LocalDate date);

    Optional<ExchangeRate> findTopByDateLessThanEqualAndDeletedAtIsNullOrderByDateDesc(LocalDate date);

    boolean existsByDateAndDeletedAtIsNull(LocalDate date);
}
