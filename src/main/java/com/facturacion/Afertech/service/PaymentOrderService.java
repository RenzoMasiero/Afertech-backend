package com.facturacion.Afertech.service;

import com.facturacion.Afertech.dto.PaymentOrderRequest;
import com.facturacion.Afertech.dto.PaymentOrderResponse;

import java.util.List;

public interface PaymentOrderService {

    List<PaymentOrderResponse> findAll();

    PaymentOrderResponse findById(Long id);

    PaymentOrderResponse create(PaymentOrderRequest request);

    PaymentOrderResponse update(Long id, PaymentOrderRequest request);

    void delete(Long id);
}
