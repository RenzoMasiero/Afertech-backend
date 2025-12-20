package com.facturacion.Afertech.dto;

import java.math.BigDecimal;
import java.time.LocalDate;

public class FixedCostResponse {

    private Long id;
    private Long costTypeId;
    private String costTypeName;
    private String description;
    private BigDecimal amount;
    private LocalDate date;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getCostTypeId() {
        return costTypeId;
    }

    public void setCostTypeId(Long costTypeId) {
        this.costTypeId = costTypeId;
    }

    public String getCostTypeName() {
        return costTypeName;
    }

    public void setCostTypeName(String costTypeName) {
        this.costTypeName = costTypeName;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }
}
