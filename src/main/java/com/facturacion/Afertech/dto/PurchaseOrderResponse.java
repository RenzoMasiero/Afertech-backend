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

    private Long clientId;
    private String clientName;

    private String purchaseOrderNumber;
    private LocalDate issueDate;

    private LocalDateTime loadedAt;
    private String loadedBy;

    private Long projectId;
    private String projectName;

    private BigDecimal totalWithoutTax;
    private BigDecimal totalWithTax;
    private String description;
}
