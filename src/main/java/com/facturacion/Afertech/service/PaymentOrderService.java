package com.facturacion.Afertech.service;

import com.facturacion.Afertech.dto.PaymentOrderRequest;
import com.facturacion.Afertech.dto.PaymentOrderResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface PaymentOrderService {

    Page<PaymentOrderResponse> findAll(Pageable pageable);

    PaymentOrderResponse findById(Long id);

    PaymentOrderResponse create(PaymentOrderRequest request);

    PaymentOrderResponse update(Long id, PaymentOrderRequest request);

    void delete(Long id);
}
