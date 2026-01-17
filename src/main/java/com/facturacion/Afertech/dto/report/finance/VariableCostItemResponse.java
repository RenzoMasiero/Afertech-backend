package com.facturacion.Afertech.dto.report.finance;

import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
public class VariableCostItemResponse {

    private Long variableCostId;
    private String costTypeName;
    private String allocationMonth; // YYYY-MM
    private BigDecimal amount;
    private String supplierName;
}
