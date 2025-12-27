package com.facturacion.Afertech.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Getter
@Setter
@Entity
@Table(name = "purchase_orders")
public class PurchaseOrder {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // Empresa / proveedor
    @Column(nullable = false)
    private String company;

    // Número de Orden de Compra
    @Column(nullable = false)
    private String purchaseOrderNumber;

    // Fecha de emisión de la OC
    @Column(nullable = false)
    private LocalDate issueDate;

    // Fecha de carga en el sistema
    @Column(nullable = false, updatable = false)
    private LocalDateTime createdAt;

    // Número de proyecto (referencia lógica a Project)
    private String projectNumber;

    // Importes
    @Column(nullable = false)
    private BigDecimal totalWithoutTax;

    @Column(nullable = false)
    private BigDecimal totalWithTax;

    // Descripción
    @Column(length = 500)
    private String description;

    @PrePersist
    protected void onCreate() {
        this.createdAt = LocalDateTime.now();
    }
}
