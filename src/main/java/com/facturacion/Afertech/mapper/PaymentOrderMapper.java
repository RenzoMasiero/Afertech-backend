package com.facturacion.Afertech.mapper;

import com.facturacion.Afertech.dto.PaymentOrderRequest;
import com.facturacion.Afertech.dto.PaymentOrderResponse;
import com.facturacion.Afertech.model.PaymentOrder;

public class PaymentOrderMapper {

    public static PaymentOrder toEntity(PaymentOrderRequest request) {
        PaymentOrder order = new PaymentOrder();
        order.setSupplier(request.getSupplier());
        order.setDescription(request.getDescription());
        order.setAmount(request.getAmount());
        order.setDate(request.getDate());
        return order;
    }

    public static PaymentOrderResponse toResponse(PaymentOrder order) {
        PaymentOrderResponse response = new PaymentOrderResponse();
        response.setId(order.getId());
        response.setSupplier(order.getSupplier());
        response.setDescription(order.getDescription());
        response.setAmount(order.getAmount());
        response.setDate(order.getDate());
        return response;
    }
}
