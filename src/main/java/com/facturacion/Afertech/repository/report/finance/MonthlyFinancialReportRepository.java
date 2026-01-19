package com.facturacion.Afertech.repository.report.finance;

import com.facturacion.Afertech.model.FixedCost;
import com.facturacion.Afertech.model.PaymentOrder;
import com.facturacion.Afertech.model.VariableCost;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;
import java.util.List;

/**
 * Repository de solo lectura para reporting financiero mensual.
 */
public interface MonthlyFinancialReportRepository extends JpaRepository<PaymentOrder, Long> {

    // =========================
    // INGRESOS (REGLA FINAL)
    // =========================
    @Query("""
        SELECT po
        FROM PaymentOrder po
        WHERE po.executed = true
          AND po.executionDate BETWEEN :from AND :to
    """)
    List<PaymentOrder> findExecutedPaymentOrdersBetween(
            @Param("from") LocalDate from,
            @Param("to") LocalDate to
    );

    // =========================
    // COSTOS FIJOS
    // =========================
    @Query("""
        SELECT fc
        FROM FixedCost fc
        WHERE fc.allocationMonth = :allocationMonth
    """)
    List<FixedCost> findFixedCostsByAllocationMonth(
            @Param("allocationMonth") String allocationMonth
    );

    // =========================
    // COSTOS VARIABLES
    // =========================
    @Query("""
        SELECT vc
        FROM VariableCost vc
        WHERE vc.allocationMonth = :allocationMonth
    """)
    List<VariableCost> findVariableCostsByAllocationMonth(
            @Param("allocationMonth") String allocationMonth
    );
}
