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
        name = "clients",
        uniqueConstraints = {
                @UniqueConstraint(columnNames = "tax_id")
        }
)
@Where(clause = "deleted_at IS NULL")
public class Client extends BaseAuditableEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name;

    @Column(name = "tax_id", nullable = false, unique = true)
    private String taxId;

    @Column(nullable = false, updatable = false)
    private LocalDateTime loadedAt;

    @Column(nullable = false, updatable = false)
    private String loadedBy;

    @PrePersist
    private void onPrePersist() {
        this.loadedAt = LocalDateTime.now();
    }
}
