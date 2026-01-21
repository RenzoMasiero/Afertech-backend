package com.facturacion.Afertech.service.report.finance;

import com.facturacion.Afertech.dto.report.finance.*;
import com.facturacion.Afertech.model.FixedCost;
import com.facturacion.Afertech.model.PaymentOrder;
import com.facturacion.Afertech.model.VariableCost;
import com.facturacion.Afertech.repository.report.finance.MonthlyFinancialReportRepository;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.YearMonth;
import java.util.ArrayList;
import java.util.List;

@Service
public class MonthlyFinancialReportServiceImpl implements MonthlyFinancialReportService {

    private final MonthlyFinancialReportRepository repository;

    public MonthlyFinancialReportServiceImpl(MonthlyFinancialReportRepository repository) {
        this.repository = repository;
    }

    @Override
    public MonthlyFinancialSummaryResponse getMonthlySummary(YearMonth month) {

        LocalDate from = month.atDay(1);
        LocalDate to = month.atEndOfMonth();
        String allocationMonth = month.toString();

        // =========================
        // INGRESOS (criterio nuevo)
        // =========================
        List<PaymentOrder> paymentOrders =
                repository.findExecutedPaymentOrdersBetween(from, to);

        List<IncomeItemResponse> incomeItems = new ArrayList<>();
        BigDecimal totalIncomeWithTax = BigDecimal.ZERO;
        BigDecimal totalIncomeWithoutTax = BigDecimal.ZERO;

        for (PaymentOrder po : paymentOrders) {

            IncomeItemResponse item = new IncomeItemResponse();
            item.setPaymentOrderId(po.getId());
            item.setPaymentOrderNumber(po.getPaymentOrderNumber());
            item.setCashInDate(po.getExecutionDate());
            item.setAmountWithTax(po.getTotalWithTax());
            item.setAmountWithoutTax(po.getTotalWithoutTax());

            if (po.getInvoice() != null) {
                item.setInvoiceNumber(po.getInvoice().getInvoiceNumber());
            }

            incomeItems.add(item);

            totalIncomeWithTax = totalIncomeWithTax.add(po.getTotalWithTax());
            totalIncomeWithoutTax = totalIncomeWithoutTax.add(po.getTotalWithoutTax());
        }

        // =========================
        // COSTOS FIJOS
        // =========================
        List<FixedCost> fixedCosts =
                repository.findFixedCostsByAllocationMonth(allocationMonth);

        List<FixedCostItemResponse> fixedCostItems = new ArrayList<>();
        BigDecimal totalFixedCosts = BigDecimal.ZERO;

        for (FixedCost fc : fixedCosts) {

            FixedCostItemResponse item = new FixedCostItemResponse();
            item.setFixedCostId(fc.getId());
            item.setCostTypeName(fc.getCostType().getName());

            // día real del costo (campo existente)
            item.setAllocationMonth(fc.getPaymentDate().toString());

            item.setAmount(fc.getAmount());
            item.setSalary(fc.getEmployee() != null);

            fixedCostItems.add(item);
            totalFixedCosts = totalFixedCosts.add(fc.getAmount());
        }

        // =========================
        // COSTOS VARIABLES
        // =========================
        List<VariableCost> variableCosts =
                repository.findVariableCostsByAllocationMonth(allocationMonth);

        List<VariableCostItemResponse> variableCostItems = new ArrayList<>();
        BigDecimal totalVariableCosts = BigDecimal.ZERO;

        for (VariableCost vc : variableCosts) {

            VariableCostItemResponse item = new VariableCostItemResponse();
            item.setVariableCostId(vc.getId());
            item.setCostTypeName(vc.getCostType().getName());

            // día real del costo (campo existente)
            item.setAllocationMonth(vc.getPaymentDate().toString());

            item.setAmount(vc.getAmount());
            item.setSupplierName(vc.getSupplier().getName());

            variableCostItems.add(item);
            totalVariableCosts = totalVariableCosts.add(vc.getAmount());
        }

        BigDecimal totalCosts = totalFixedCosts.add(totalVariableCosts);

        // =========================
        // RESPUESTA
        // =========================
        MonthlyFinancialSummaryResponse response = new MonthlyFinancialSummaryResponse();
        response.setMonth(allocationMonth);

        response.setIncomes(incomeItems);
        response.setTotalIncomeWithTax(totalIncomeWithTax);
        response.setTotalIncomeWithoutTax(totalIncomeWithoutTax);

        response.setFixedCosts(fixedCostItems);
        response.setVariableCosts(variableCostItems);
        response.setTotalFixedCosts(totalFixedCosts);
        response.setTotalVariableCosts(totalVariableCosts);
        response.setTotalCosts(totalCosts);

        response.setProfitWithoutTax(
                totalIncomeWithoutTax.subtract(totalCosts)
        );

        response.setProfitWithTax(
                totalIncomeWithTax.subtract(totalCosts)
        );

        return response;
    }
}
