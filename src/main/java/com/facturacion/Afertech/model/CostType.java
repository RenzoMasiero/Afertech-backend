package com.facturacion.Afertech.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.Where;

import java.time.LocalDateTime;

@Getter
@Setter
@Entity
@Table(name = "cost_types")
@Where(clause = "deleted_at IS NULL")
public class CostType extends BaseAuditableEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private String name;

    // Fecha de carga funcional
    @Column(nullable = false, updatable = false)
    private LocalDateTime loadedAt;

    // Usuario que cargó (dato de uso)
    @Column(nullable = false, updatable = false)
    private String loadedBy;

    @PrePersist
    private void onPrePersist() {
        this.loadedAt = LocalDateTime.now();
        // loadedBy se setea desde el service
    }
}
