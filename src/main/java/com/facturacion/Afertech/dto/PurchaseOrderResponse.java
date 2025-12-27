package com.facturacion.Afertech.dto;

import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Getter
@Setter
public class PurchaseOrderResponse {

    private Long id;
    private String company;
    private String purchaseOrderNumber;
    private LocalDate issueDate;
    private LocalDateTime createdAt;
    private String projectNumber;
    private BigDecimal totalWithoutTax;
    private BigDecimal totalWithTax;
    private String description;
}
