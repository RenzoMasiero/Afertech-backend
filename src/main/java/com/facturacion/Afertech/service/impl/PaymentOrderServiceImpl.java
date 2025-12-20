package com.facturacion.Afertech.service.impl;

import com.facturacion.Afertech.dto.PaymentOrderRequest;
import com.facturacion.Afertech.dto.PaymentOrderResponse;
import com.facturacion.Afertech.mapper.PaymentOrderMapper;
import com.facturacion.Afertech.model.PaymentOrder;
import com.facturacion.Afertech.repository.PaymentOrderRepository;
import com.facturacion.Afertech.service.PaymentOrderService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PaymentOrderServiceImpl implements PaymentOrderService {

    private final PaymentOrderRepository repository;

    public PaymentOrderServiceImpl(PaymentOrderRepository repository) {
        this.repository = repository;
    }

    @Override
    public List<PaymentOrderResponse> findAll() {
        return repository.findAll()
                .stream()
                .map(PaymentOrderMapper::toResponse)
                .toList();
    }

    @Override
    public PaymentOrderResponse findById(Long id) {
        PaymentOrder order = repository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("PaymentOrder not found"));
        return PaymentOrderMapper.toResponse(order);
    }

    @Override
    public PaymentOrderResponse create(PaymentOrderRequest request) {
        PaymentOrder order = PaymentOrderMapper.toEntity(request);
        return PaymentOrderMapper.toResponse(repository.save(order));
    }

    @Override
    public PaymentOrderResponse update(Long id, PaymentOrderRequest request) {
        PaymentOrder order = repository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("PaymentOrder not found"));

        order.setSupplier(request.getSupplier());
        order.setDescription(request.getDescription());
        order.setAmount(request.getAmount());
        order.setDate(request.getDate());

        return PaymentOrderMapper.toResponse(repository.save(order));
    }

    @Override
    public void delete(Long id) {
        repository.deleteById(id);
    }
}
