package com.facturacion.Afertech.dto;

import java.math.BigDecimal;
import java.time.LocalDate;

public class InvoiceRequest {

    private String number;
    private String customerName;
    private LocalDate date;
    private BigDecimal total;

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public String getCustomerName() {
        return customerName;
    }

    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public BigDecimal getTotal() {
        return total;
    }

    public void setTotal(BigDecimal total) {
        this.total = total;
    }
}
