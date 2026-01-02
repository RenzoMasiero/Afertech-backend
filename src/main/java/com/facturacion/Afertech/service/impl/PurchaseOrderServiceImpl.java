package com.facturacion.Afertech.service.impl;

import com.facturacion.Afertech.dto.PurchaseOrderRequest;
import com.facturacion.Afertech.dto.PurchaseOrderResponse;
import com.facturacion.Afertech.mapper.PurchaseOrderMapper;
import com.facturacion.Afertech.model.PurchaseOrder;
import com.facturacion.Afertech.repository.PurchaseOrderRepository;
import com.facturacion.Afertech.service.PurchaseOrderService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

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
    public Page<PurchaseOrderResponse> findAll(Pageable pageable) {
        return repository.findAll(pageable)
                .map(mapper::toResponse);
    }

    @Override
    public PurchaseOrderResponse findById(Long id) {
        PurchaseOrder purchaseOrder = repository.findById(id)
                .orElseThrow(() -> new RuntimeException("Purchase order not found"));
        return mapper.toResponse(purchaseOrder);
    }

    @Override
    public PurchaseOrderResponse create(PurchaseOrderRequest request) {

        Authentication auth = SecurityContextHolder
                .getContext()
                .getAuthentication();

        PurchaseOrder purchaseOrder = mapper.toEntity(request);

        // dato funcional: quién cargó la orden de compra
        purchaseOrder.setLoadedBy(auth.getName());

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

        Authentication auth = SecurityContextHolder
                .getContext()
                .getAuthentication();

        purchaseOrder.setDeletedAt(LocalDateTime.now());
        purchaseOrder.setDeletedBy(auth.getName());

        repository.save(purchaseOrder);
    }
}
