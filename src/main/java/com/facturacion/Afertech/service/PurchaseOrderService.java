package com.facturacion.Afertech.service;

import com.facturacion.Afertech.dto.PurchaseOrderRequest;
import com.facturacion.Afertech.dto.PurchaseOrderResponse;

import java.util.List;

public interface PurchaseOrderService {

    List<PurchaseOrderResponse> findAll();

    PurchaseOrderResponse findById(Long id);

    PurchaseOrderResponse create(PurchaseOrderRequest request);

    PurchaseOrderResponse update(Long id, PurchaseOrderRequest request);

    void delete(Long id);
}
