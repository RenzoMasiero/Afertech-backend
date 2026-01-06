package com.facturacion.Afertech.repository;

import com.facturacion.Afertech.model.Invoice;
import org.springframework.data.jpa.repository.JpaRepository;

public interface InvoiceRepository extends JpaRepository<Invoice, Long> {

    // 🔒 Usado por ClientServiceImpl
    boolean existsByClientId(Long clientId);

    // 🔒 Usado por ProjectServiceImpl
    boolean existsByProjectIdAndDeletedAtIsNull(Long projectId);

    // 🔒 Usado por PurchaseOrderServiceImpl
    boolean existsByPurchaseOrderIdAndDeletedAtIsNull(Long purchaseOrderId);
}
