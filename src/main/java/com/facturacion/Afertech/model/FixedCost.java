package com.facturacion.Afertech.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDate;

@Getter
@Setter
@Entity
@Table(name = "fixed_costs")
public class FixedCost {

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
}
