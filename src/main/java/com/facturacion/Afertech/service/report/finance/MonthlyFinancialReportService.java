package com.facturacion.Afertech.service.report.finance;

import com.facturacion.Afertech.dto.report.finance.MonthlyFinancialSummaryResponse;

import java.time.YearMonth;

public interface MonthlyFinancialReportService {

    MonthlyFinancialSummaryResponse getMonthlySummary(YearMonth month);
}
