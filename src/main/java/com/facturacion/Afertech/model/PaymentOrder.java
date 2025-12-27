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
@Table(name = "payment_orders")
public class PaymentOrder {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // Empresa
    @Column(nullable = false)
    private String company;

    // Número de Orden de Pago
    @Column(nullable = false)
    private String paymentOrderNumber;

    // Fecha de emisión
    @Column(nullable = false)
    private LocalDate issueDate;

    // Fecha de carga en el sistema
    @Column(nullable = false, updatable = false)
    private LocalDateTime createdAt;

    // Número de proyecto (referencia lógica)
    private String projectNumber;

    // Importes
    @Column(nullable = false)
    private BigDecimal totalWithoutTax;

    @Column(nullable = false)
    private BigDecimal totalWithTax;

    // Concepto
    @Column(length = 500)
    private String concept;

    // Referencias
    private String invoiceNumber;
    private String purchaseOrderNumber;

    // Retenciones
    private BigDecimal withholdings;

    @PrePersist
    protected void onCreate() {
        this.createdAt = LocalDateTime.now();
    }
}
