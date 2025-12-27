package com.facturacion.Afertech.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(
        name = "suppliers",
        uniqueConstraints = {
                @UniqueConstraint(columnNames = "tax_id")
        }
)
public class Supplier {

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
}
