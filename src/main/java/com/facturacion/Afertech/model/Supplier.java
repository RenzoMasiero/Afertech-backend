package com.facturacion.Afertech.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.Where;

import java.time.LocalDateTime;

@Getter
@Setter
@Entity
@Table(
        name = "suppliers",
        uniqueConstraints = {
                @UniqueConstraint(columnNames = "tax_id")
        }
)
@Where(clause = "deleted_at IS NULL")
public class Supplier extends BaseAuditableEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // Razón social
    @Column(nullable = false)
    private String name;

    // CUIT
    @Column(name = "tax_id", nullable = false, unique = true)
    private String taxId;

    @Column(nullable = false)
    private boolean active = true;

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
