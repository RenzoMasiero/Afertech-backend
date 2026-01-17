package com.facturacion.Afertech.dto.report.finance;

import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDate;

@Getter
@Setter
public class IncomeItemResponse {

    private Long paymentOrderId;
    private String paymentOrderNumber;

    private String invoiceNumber;

    private LocalDate cashInDate;

    private BigDecimal amountWithoutTax;
    private BigDecimal amountWithTax;
}
