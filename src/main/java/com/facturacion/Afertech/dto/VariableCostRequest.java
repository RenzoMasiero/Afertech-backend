package com.facturacion.Afertech.dto;

import java.math.BigDecimal;
import java.time.LocalDate;

public class VariableCostRequest {

    private Long costTypeId;
    private String description;
    private BigDecimal amount;
    private LocalDate date;
    private String businessName;
    private Long projectId;

    public Long getCostTypeId() {
        return costTypeId;
    }

    public void setCostTypeId(Long costTypeId) {
        this.costTypeId = costTypeId;
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

    public Long getProjectId() {
        return projectId;
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

    public void setProjectId(Long projectId) {
        this.projectId = projectId;
    }
}
