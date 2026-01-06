package com.facturacion.Afertech.repository;

import com.facturacion.Afertech.model.FixedCost;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FixedCostRepository extends JpaRepository<FixedCost, Long> {

    // 🔒 Regla SUELDO (Employee)
    boolean existsByEmployeeIdAndCostType_NameIgnoreCaseAndDeletedAtIsNull(
            Long employeeId,
            String costTypeName
    );

    // 🔒 Regla CostType
    boolean existsByCostTypeIdAndDeletedAtIsNull(Long costTypeId);
}
