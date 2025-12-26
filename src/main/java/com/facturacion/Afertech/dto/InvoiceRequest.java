package com.facturacion.Afertech.dto;

import jakarta.validation.constraints.*;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDate;

@Getter
@Setter
public class InvoiceRequest {

    @NotBlank
    private String company;

    @NotBlank
    private String invoiceNumber;

    @NotNull
    private LocalDate issueDate;

    @NotBlank
    private String description;

    @NotNull
    @DecimalMin(value = "0.0", inclusive = false)
    private BigDecimal totalWithoutTax;

    @NotNull
    @DecimalMin(value = "0.0", inclusive = false)
    private BigDecimal totalWithTax;

    @NotNull
    @Min(0)
    private Integer deferredPaymentDays;

    @NotBlank
    private String projectNumber;

    @NotBlank
    private String purchaseOrder;

    @NotNull
    @Min(0)
    @Max(100)
    private Integer purchaseOrderPercentage;

    @NotBlank
    private String paymentOrder;
}
