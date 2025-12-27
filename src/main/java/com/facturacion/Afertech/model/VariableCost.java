package com.facturacion.Afertech.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDate;

@Getter
@Setter
@Entity
@Table(name = "variable_costs")
public class VariableCost {

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

    // Proyecto asociado (solo para algunos tipos)
    // Referencia lógica por ahora
    private String projectNumber;
}
