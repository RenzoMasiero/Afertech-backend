package com.facturacion.Afertech.dto;

import java.math.BigDecimal;
import java.time.LocalDate;

public class VariableCostResponse {

    private Long id;
    private Long costTypeId;
    private String costTypeName;
    private String description;
    private BigDecimal amount;
    private LocalDate date;
    private String businessName;
    private Long projectId;
    private String projectName;

    public Long getId() {
        return id;
    }

    public Long getCostTypeId() {
        return costTypeId;
    }

    public String getCostTypeName() {
        return costTypeName;
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

    public String getProjectName() {
        return projectName;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setCostTypeId(Long costTypeId) {
        this.costTypeId = costTypeId;
    }

    public void setCostTypeName(String costTypeName) {
        this.costTypeName = costTypeName;
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

    public void setProjectName(String projectName) {
        this.projectName = projectName;
    }
}
