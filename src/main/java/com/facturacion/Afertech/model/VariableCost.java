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
@Table(name = "variable_costs")
@Where(clause = "deleted_at IS NULL")
public class VariableCost extends BaseAuditableEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // Tipo de costo variable (Combustible, Materiales, EPP, etc.)
    @ManyToOne(optional = false)
    @JoinColumn(name = "variable_cost_type_id")
    private VariableCostType costType;

    // Aclaración / detalle
    @Column(length = 500)
    private String description;

    // Monto del costo
    @Column(nullable = false)
    private BigDecimal amount;

    // Mes al que se imputa el costo (ej: 2025-01-01)
    @Column(nullable = false)
    private LocalDate allocationMonth;

    // Fecha real de pago
    @Column(nullable = false)
    private LocalDate paymentDate;

    // Razón social / proveedor
    @Column(nullable = false)
    private String businessName;

    // Proyecto asociado (referencia lógica)
    private String projectNumber;

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
