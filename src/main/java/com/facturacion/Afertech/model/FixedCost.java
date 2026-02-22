package com.facturacion.Afertech.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.Where;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Getter
@Setter
@Entity
@Table(name = "fixed_costs")
@Where(clause = "deleted_at IS NULL")
public class FixedCost extends BaseAuditableEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(optional = false)
    @JoinColumn(name = "cost_type_id", nullable = false)
    private CostType costType;

    @ManyToOne
    @JoinColumn(name = "employee_id")
    private Employee employee;

    @Column(nullable = false)
    private BigDecimal amount;

    // 💵 MODELO MONETARIO (igual patrón Invoice)

    @Enumerated(EnumType.STRING)
    @Column(name = "currency_original", nullable = false)
    private Currency currencyOriginal;

    @Column(name = "exchange_rate_used", precision = 15, scale = 2)
    private BigDecimal exchangeRateUsed;

    @Column(name = "amount_usd", precision = 15, scale = 2)
    private BigDecimal amountUsd;

    @Column(nullable = false, length = 7)
    private String allocationMonth;

    @Column(nullable = false)
    private LocalDate paymentDate;

    @Column(length = 500)
    private String description;

    @Column(nullable = false, updatable = false)
    private LocalDateTime loadedAt;

    @Column(nullable = false, updatable = false)
    private String loadedBy;

    @PrePersist
    private void onPrePersist() {
        this.loadedAt = LocalDateTime.now();
    }
}