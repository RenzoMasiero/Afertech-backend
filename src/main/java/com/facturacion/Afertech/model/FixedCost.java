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

    // Tipo de costo (Sueldo, ART, Alquiler, etc.)
    @ManyToOne(optional = false)
    @JoinColumn(name = "cost_type_id")
    private CostType costType;

    // Monto del costo
    @Column(nullable = false)
    private BigDecimal amount;

    // Mes al que se imputa el costo (ej: 2025-01-01)
    @Column(nullable = false)
    private LocalDate allocationMonth;

    // Fecha real de pago
    @Column(nullable = false)
    private LocalDate paymentDate;

    // Observaciones / detalle opcional
    @Column(length = 500)
    private String description;

    // Fecha de carga funcional
    @Column(nullable = false, updatable = false)
    private LocalDateTime loadedAt;

    // Usuario que cargó
    @Column(nullable = false, updatable = false)
    private String loadedBy;

    @PrePersist
    private void onPrePersist() {
        this.loadedAt = LocalDateTime.now();
        // loadedBy se setea desde el service
    }
}
