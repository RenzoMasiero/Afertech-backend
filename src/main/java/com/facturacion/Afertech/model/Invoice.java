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
@Table(name = "invoices")
@Where(clause = "deleted_at IS NULL")
public class Invoice extends BaseAuditableEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // Empresa emisora
    @Column(nullable = false)
    private String company;

    // Número de factura
    @Column(nullable = false)
    private String invoiceNumber;

    // Fecha de la factura (del comprobante)
    @Column(nullable = false)
    private LocalDate issueDate;

    // Fecha de carga funcional (visible para el usuario)
    @Column(name = "loaded_at", nullable = false, updatable = false)
    private LocalDateTime loadedAt;

    private String description;

    @Column(nullable = false)
    private BigDecimal totalWithoutTax;

    @Column(nullable = false)
    private BigDecimal totalWithTax;

    private Integer deferredPaymentDays;

    private String projectNumber;

    private String purchaseOrder;

    private Integer purchaseOrderPercentage;

    private String paymentOrder;

    @PrePersist
    protected void onLoad() {
        this.loadedAt = LocalDateTime.now();
    }
}
