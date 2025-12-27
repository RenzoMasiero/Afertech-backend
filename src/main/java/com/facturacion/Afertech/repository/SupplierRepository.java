package com.facturacion.Afertech.repository;

import com.facturacion.Afertech.model.Supplier;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SupplierRepository extends JpaRepository<Supplier, Long> {

    boolean existsByTaxId(String taxId);
}