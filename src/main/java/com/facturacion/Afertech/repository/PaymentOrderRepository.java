package com.facturacion.Afertech.repository;

import com.facturacion.Afertech.model.PaymentOrder;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PaymentOrderRepository extends JpaRepository<PaymentOrder, Long> {

    // 🔒 Usado por ProjectServiceImpl
    boolean existsByProjectIdAndDeletedAtIsNull(Long projectId);

    // 🔒 Usado por InvoiceServiceImpl
    boolean existsByInvoiceIdAndDeletedAtIsNull(Long invoiceId);

    // 🔒 Usado por PurchaseOrderServiceImpl (si aplica a futuro)
    boolean existsByPurchaseOrderIdAndDeletedAtIsNull(Long purchaseOrderId);
}
