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
 * ❌ No lógica de negocio
 * ❌ No cálculos temporales complejos
 */
public interface MonthlyFinancialReportRepository extends JpaRepository<PaymentOrder, Long> {

    /**
     * INGRESOS (base):
     * Trae PaymentOrders por issueDate.
     * El cálculo de cashInDate (issueDate + deferredPaymentDays)
     * y el filtrado final por mes se hacen en el Service.
     */
    @Query("""
        SELECT po
        FROM PaymentOrder po
        WHERE po.issueDate BETWEEN :from AND :to
    """)
    List<PaymentOrder> findPaymentOrdersIssuedBetween(
            @Param("from") LocalDate from,
            @Param("to") LocalDate to
    );

    /**
     * COSTOS FIJOS del mes (allocationMonth)
     */
    @Query("""
        SELECT fc
        FROM FixedCost fc
        WHERE fc.allocationMonth BETWEEN :from AND :to
    """)
    List<FixedCost> findFixedCostsBetween(
            @Param("from") LocalDate from,
            @Param("to") LocalDate to
    );

    /**
     * COSTOS VARIABLES del mes (allocationMonth)
     */
    @Query("""
        SELECT vc
        FROM VariableCost vc
        WHERE vc.allocationMonth BETWEEN :from AND :to
    """)
    List<VariableCost> findVariableCostsBetween(
            @Param("from") LocalDate from,
            @Param("to") LocalDate to
    );
}
