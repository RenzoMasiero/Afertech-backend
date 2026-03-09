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
@Table(name = "payment_orders")
@Where(clause = "deleted_at IS NULL")
public class PaymentOrder extends BaseAuditableEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // 🔗 Client
    @ManyToOne(optional = false)
    @JoinColumn(name = "client_id", nullable = false)
    private Client client;

    @Column(nullable = false)
    private String paymentOrderNumber;

    @Column(nullable = false)
    private LocalDate issueDate;

    @Column(nullable = false)
    private boolean executed = false;

    private LocalDate executionDate;

    @Column(nullable = false, updatable = false)
    private LocalDateTime loadedAt;

    @Column(nullable = false, updatable = false)
    private String loadedBy;

    // 🔗 Project
    @ManyToOne(optional = false)
    @JoinColumn(name = "project_id", nullable = false)
    private Project project;

    @Column(nullable = false)
    private BigDecimal totalWithoutTax;

    @Column(nullable = false)
    private BigDecimal totalWithTax;

    // 💵 --- MODELO MONETARIO ---

    @Enumerated(EnumType.STRING)
    @Column(name = "currency_original", nullable = false)
    private Currency currencyOriginal;

    @Column(name = "exchange_rate_used", precision = 15, scale = 2)
    private BigDecimal exchangeRateUsed;

    @Column(name = "total_without_tax_usd", precision = 15, scale = 2)
    private BigDecimal totalWithoutTaxUsd;

    @Column(name = "total_with_tax_usd", precision = 15, scale = 2)
    private BigDecimal totalWithTaxUsd;

    @Column(length = 500)
    private String concept;

    @OneToOne
    @JoinColumn(name = "invoice_id")
    private Invoice invoice;

    @ManyToOne
    @JoinColumn(name = "purchase_order_id")
    private PurchaseOrder purchaseOrder;

    private BigDecimal withholdings;

    @PrePersist
    protected void onPrePersist() {
        this.loadedAt = LocalDateTime.now();
    }
}