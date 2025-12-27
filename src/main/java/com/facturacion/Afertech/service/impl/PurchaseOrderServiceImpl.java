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
    private final PurchaseOrderMapper mapper;

    public PurchaseOrderServiceImpl(
            PurchaseOrderRepository repository,
            PurchaseOrderMapper mapper
    ) {
        this.repository = repository;
        this.mapper = mapper;
    }

    @Override
    public List<PurchaseOrderResponse> findAll() {
        return repository.findAll()
                .stream()
                .map(mapper::toResponse)
                .toList();
    }

    @Override
    public PurchaseOrderResponse findById(Long id) {
        PurchaseOrder purchaseOrder = repository.findById(id)
                .orElseThrow(() -> new RuntimeException("Purchase order not found"));
        return mapper.toResponse(purchaseOrder);
    }

    @Override
    public PurchaseOrderResponse create(PurchaseOrderRequest request) {
        PurchaseOrder purchaseOrder = mapper.toEntity(request);
        return mapper.toResponse(repository.save(purchaseOrder));
    }

    @Override
    public PurchaseOrderResponse update(Long id, PurchaseOrderRequest request) {
        PurchaseOrder purchaseOrder = repository.findById(id)
                .orElseThrow(() -> new RuntimeException("Purchase order not found"));

        purchaseOrder.setCompany(request.getCompany());
        purchaseOrder.setPurchaseOrderNumber(request.getPurchaseOrderNumber());
        purchaseOrder.setIssueDate(request.getIssueDate());
        purchaseOrder.setProjectNumber(request.getProjectNumber());
        purchaseOrder.setTotalWithoutTax(request.getTotalWithoutTax());
        purchaseOrder.setTotalWithTax(request.getTotalWithTax());
        purchaseOrder.setDescription(request.getDescription());

        return mapper.toResponse(repository.save(purchaseOrder));
    }

    @Override
    public void delete(Long id) {
        PurchaseOrder purchaseOrder = repository.findById(id)
                .orElseThrow(() -> new RuntimeException("Purchase order not found"));
        repository.delete(purchaseOrder);
    }
}
