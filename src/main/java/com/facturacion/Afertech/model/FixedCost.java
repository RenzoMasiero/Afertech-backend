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

    // Tipo de costo
    @ManyToOne(optional = false)
    @JoinColumn(name = "cost_type_id", nullable = false)
    private CostType costType;

    // 🔗 Employee (solo SUELDO)
    @ManyToOne
    @JoinColumn(name = "employee_id")
    private Employee employee;

    @Column(nullable = false)
    private BigDecimal amount;

    // Mes de imputación en formato YYYY-MM
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
