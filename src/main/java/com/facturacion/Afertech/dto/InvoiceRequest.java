package com.facturacion.Afertech.dto;

import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDate;

@Getter
@Setter
public class InvoiceRequest {

    private String company;
    private String invoiceNumber;
    private LocalDate issueDate;
    private String description;
    private BigDecimal totalWithoutTax;
    private BigDecimal totalWithTax;
    private Integer deferredPaymentDays;
    private String projectNumber;
    private String purchaseOrder;
    private Integer purchaseOrderPercentage;
    private String paymentOrder;
}
