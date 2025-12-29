package com.facturacion.Afertech.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDate;

@Getter
@Setter
@Entity
@Table(name = "payment_orders")
public class PaymentOrder extends BaseAuditableEntity {

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
}
