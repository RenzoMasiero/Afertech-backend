package com.facturacion.Afertech.model;

import jakarta.persistence.*;

import java.math.BigDecimal;
import java.time.LocalDate;

@Entity
@Table(name = "variable_costs")
public class VariableCost {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(optional = false)
    @JoinColumn(name = "variable_cost_type_id")
    private VariableCostType costType;

    @Column(length = 500)
    private String description;

    @Column(nullable = false)
    private BigDecimal amount;

    @Column(nullable = false)
    private LocalDate date;

    @Column(name = "business_name")
    private String businessName;

    @ManyToOne
    @JoinColumn(name = "project_id")
    private Project project;

    // ===== getters & setters =====

    public Long getId() {
        return id;
    }

    public VariableCostType getCostType() {
        return costType;
    }

    public void setCostType(VariableCostType costType) {
        this.costType = costType;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getDescription() {
        return description;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public LocalDate getDate() {
        return date;
    }

    public String getBusinessName() {
        return businessName;
    }

    public Project getProject() {
        return project;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public void setBusinessName(String businessName) {
        this.businessName = businessName;
    }

    public void setProject(Project project) {
        this.project = project;
    }
}
