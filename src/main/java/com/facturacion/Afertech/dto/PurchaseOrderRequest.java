package com.facturacion.Afertech.dto;

import java.math.BigDecimal;
import java.time.LocalDate;

public class PurchaseOrderRequest {

    private String supplier;
    private String description;
    private BigDecimal amount;
    private LocalDate date;

    public String getSupplier() {
        return supplier;
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

    public void setSupplier(String supplier) {
        this.supplier = supplier;
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
}
