package com.facturacion.Afertech.dto.report.finance;

import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.List;

@Getter
@Setter
public class MonthlyFinancialSummaryResponse {

    private String month; // YYYY-MM

    // Ingresos
    private List<IncomeItemResponse> incomes;
    private BigDecimal totalIncomeWithoutTax;
    private BigDecimal totalIncomeWithTax;

    // Costos
    private List<FixedCostItemResponse> fixedCosts;
    private List<VariableCostItemResponse> variableCosts;
    private BigDecimal totalFixedCosts;
    private BigDecimal totalVariableCosts;
    private BigDecimal totalCosts;

    // Resultado
    private BigDecimal profitWithoutTax;
    private BigDecimal profitWithTax;
}
