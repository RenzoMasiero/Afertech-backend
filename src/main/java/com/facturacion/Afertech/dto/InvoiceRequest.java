package com.facturacion.Afertech.dto;

import com.facturacion.Afertech.model.Currency;
import jakarta.validation.constraints.*;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDate;

@Getter
@Setter
public class InvoiceRequest {

    @NotNull
    private Long clientId;

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
    private Currency currencyOriginal;

    @NotNull
    @Min(0)
    private Integer deferredPaymentDays;

    @NotNull
    private Long projectId;

    @NotNull
    private Long purchaseOrderId;

    @NotNull
    @Min(0)
    @Max(100)
    private Integer purchaseOrderPercentage;

    // opcional
    private Long paymentOrderId;
}