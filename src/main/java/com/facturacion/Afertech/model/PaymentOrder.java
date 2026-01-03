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

    @Column(length = 500)
    private String concept;

    // 🔗 Invoice (1–1)
    @OneToOne
    @JoinColumn(name = "invoice_id")
    private Invoice invoice;

    // 🔗 PurchaseOrder
    @ManyToOne
    @JoinColumn(name = "purchase_order_id")
    private PurchaseOrder purchaseOrder;

    private BigDecimal withholdings;

    @PrePersist
    protected void onPrePersist() {
        this.loadedAt = LocalDateTime.now();
    }
}
