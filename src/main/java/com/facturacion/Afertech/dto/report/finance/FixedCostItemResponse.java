package com.facturacion.Afertech.dto.report.finance;

import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
public class FixedCostItemResponse {

    private Long fixedCostId;
    private String costTypeName;
    private String allocationMonth; // YYYY-MM
    private BigDecimal amount;
    private boolean salary;
}
