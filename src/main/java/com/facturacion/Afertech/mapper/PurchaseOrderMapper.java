package com.facturacion.Afertech.mapper;

import com.facturacion.Afertech.dto.PurchaseOrderRequest;
import com.facturacion.Afertech.dto.PurchaseOrderResponse;
import com.facturacion.Afertech.model.PurchaseOrder;

public class PurchaseOrderMapper {

    public static PurchaseOrder toEntity(PurchaseOrderRequest request) {
        PurchaseOrder order = new PurchaseOrder();
        order.setSupplier(request.getSupplier());
        order.setDescription(request.getDescription());
        order.setAmount(request.getAmount());
        order.setDate(request.getDate());
        return order;
    }

    public static PurchaseOrderResponse toResponse(PurchaseOrder order) {
        PurchaseOrderResponse response = new PurchaseOrderResponse();
        response.setId(order.getId());
        response.setSupplier(order.getSupplier());
        response.setDescription(order.getDescription());
        response.setAmount(order.getAmount());
        response.setDate(order.getDate());
        return response;
    }
}
