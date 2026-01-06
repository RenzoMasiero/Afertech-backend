package com.facturacion.Afertech.repository;

import com.facturacion.Afertech.model.VariableCost;
import org.springframework.data.jpa.repository.JpaRepository;

public interface VariableCostRepository extends JpaRepository<VariableCost, Long> {

    // 🔒 Regla Project
    boolean existsByProjectIdAndDeletedAtIsNull(Long projectId);

    // 🔒 Regla Supplier
    boolean existsBySupplierIdAndDeletedAtIsNull(Long supplierId);

    // 🔒 Regla VariableCostType
    boolean existsByCostTypeIdAndDeletedAtIsNull(Long variableCostTypeId);
}
