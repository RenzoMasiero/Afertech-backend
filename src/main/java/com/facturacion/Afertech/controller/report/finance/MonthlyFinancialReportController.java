package com.facturacion.Afertech.controller.report.finance;

import com.facturacion.Afertech.dto.report.finance.MonthlyFinancialSummaryResponse;
import com.facturacion.Afertech.service.report.finance.MonthlyFinancialReportService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.time.YearMonth;

@RestController
@RequestMapping("/reports/finance")
public class MonthlyFinancialReportController {

    private final MonthlyFinancialReportService service;

    public MonthlyFinancialReportController(MonthlyFinancialReportService service) {
        this.service = service;
    }

    @GetMapping("/monthly")
    public MonthlyFinancialSummaryResponse getMonthlyReport(
            @RequestParam("month") String month
    ) {
        return service.getMonthlySummary(YearMonth.parse(month));
    }
}
