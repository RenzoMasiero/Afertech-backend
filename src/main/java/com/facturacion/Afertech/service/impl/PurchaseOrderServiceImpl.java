package com.facturacion.Afertech.service.impl;

import com.facturacion.Afertech.dto.PurchaseOrderRequest;
import com.facturacion.Afertech.dto.PurchaseOrderResponse;
import com.facturacion.Afertech.mapper.PurchaseOrderMapper;
import com.facturacion.Afertech.model.PurchaseOrder;
import com.facturacion.Afertech.repository.PurchaseOrderRepository;
import com.facturacion.Afertech.service.PurchaseOrderService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PurchaseOrderServiceImpl implements PurchaseOrderService {

    private final PurchaseOrderRepository repository;

    public PurchaseOrderServiceImpl(PurchaseOrderRepository repository) {
        this.repository = repository;
    }

    @Override
    public List<PurchaseOrderResponse> findAll() {
        return repository.findAll()
                .stream()
                .map(PurchaseOrderMapper::toResponse)
                .toList();
    }

    @Override
    public PurchaseOrderResponse findById(Long id) {
        PurchaseOrder order = repository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("PurchaseOrder not found"));
        return PurchaseOrderMapper.toResponse(order);
    }

    @Override
    public PurchaseOrderResponse create(PurchaseOrderRequest request) {
        PurchaseOrder order = PurchaseOrderMapper.toEntity(request);
        return PurchaseOrderMapper.toResponse(repository.save(order));
    }

    @Override
    public PurchaseOrderResponse update(Long id, PurchaseOrderRequest request) {
        PurchaseOrder order = repository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("PurchaseOrder not found"));

        order.setSupplier(request.getSupplier());
        order.setDescription(request.getDescription());
        order.setAmount(request.getAmount());
        order.setDate(request.getDate());

        return PurchaseOrderMapper.toResponse(repository.save(order));
    }

    @Override
    public void delete(Long id) {
        repository.deleteById(id);
    }
}
