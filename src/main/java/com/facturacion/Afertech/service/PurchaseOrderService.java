package com.facturacion.Afertech.service;

import com.facturacion.Afertech.dto.PurchaseOrderRequest;
import com.facturacion.Afertech.dto.PurchaseOrderResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface PurchaseOrderService {

    Page<PurchaseOrderResponse> findAll(Pageable pageable);

    PurchaseOrderResponse findById(Long id);

    PurchaseOrderResponse create(PurchaseOrderRequest request);

    PurchaseOrderResponse update(Long id, PurchaseOrderRequest request);

    void delete(Long id);
}
