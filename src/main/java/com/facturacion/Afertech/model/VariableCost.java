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

    // Tipo de costo variable
    @ManyToOne(optional = false)
    @JoinColumn(name = "variable_cost_type_id", nullable = false)
    private VariableCostType costType;

    @Column(length = 500)
    private String description;

    @Column(nullable = false)
    private BigDecimal amount;

    @Column(nullable = false)
    private LocalDate allocationMonth;

    @Column(nullable = false)
    private LocalDate paymentDate;

    // 🔗 Supplier
    @ManyToOne(optional = false)
    @JoinColumn(name = "supplier_id", nullable = false)
    private Supplier supplier;

    // 🔗 Project
    @ManyToOne
    @JoinColumn(name = "project_id")
    private Project project;

    @Column(nullable = false, updatable = false)
    private LocalDateTime loadedAt;

    @Column(nullable = false, updatable = false)
    private String loadedBy;

    @PrePersist
    private void onPrePersist() {
        this.loadedAt = LocalDateTime.now();
    }
}
