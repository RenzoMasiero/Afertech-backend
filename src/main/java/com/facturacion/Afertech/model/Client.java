package com.facturacion.Afertech.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(
        name = "clients",
        uniqueConstraints = {
                @UniqueConstraint(columnNames = "tax_id")
        }
)
public class Client extends BaseAuditableEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // Razón social / nombre
    @Column(nullable = false)
    private String name;

    // CUIT
    @Column(name = "tax_id", nullable = false, unique = true)
    private String taxId;

    @Column(nullable = false)
    private boolean active = true;
}
