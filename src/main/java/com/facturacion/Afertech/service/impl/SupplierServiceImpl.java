package com.facturacion.Afertech.service.impl;

import com.facturacion.Afertech.dto.SupplierRequest;
import com.facturacion.Afertech.dto.SupplierResponse;
import com.facturacion.Afertech.mapper.SupplierMapper;
import com.facturacion.Afertech.model.Supplier;
import com.facturacion.Afertech.repository.SupplierRepository;
import com.facturacion.Afertech.repository.VariableCostRepository;
import com.facturacion.Afertech.service.SupplierService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class SupplierServiceImpl implements SupplierService {

    private final SupplierRepository repository;
    private final VariableCostRepository variableCostRepository;
    private final SupplierMapper mapper;

    public SupplierServiceImpl(
            SupplierRepository repository,
            VariableCostRepository variableCostRepository,
            SupplierMapper mapper
    ) {
        this.repository = repository;
        this.variableCostRepository = variableCostRepository;
        this.mapper = mapper;
    }

    @Override
    public Page<SupplierResponse> findAll(Pageable pageable) {
        return repository.findAll(pageable)
                .map(mapper::toResponse);
    }

    @Override
    public SupplierResponse findById(Long id) {
        Supplier supplier = repository.findById(id)
                .orElseThrow(() -> new RuntimeException("Supplier not found"));
        return mapper.toResponse(supplier);
    }

    @Override
    public SupplierResponse create(SupplierRequest request) {

        if (repository.existsByTaxId(request.getTaxId())) {
            throw new RuntimeException("Supplier with this taxId already exists");
        }

        Supplier supplier = mapper.toEntity(request);
        supplier.setLoadedBy(
                SecurityContextHolder.getContext()
                        .getAuthentication()
                        .getName()
        );

        return mapper.toResponse(repository.save(supplier));
    }

    @Override
    public SupplierResponse update(Long id, SupplierRequest request) {

        Supplier supplier = repository.findById(id)
                .orElseThrow(() -> new RuntimeException("Supplier not found"));

        if (!supplier.getTaxId().equals(request.getTaxId())
                && repository.existsByTaxId(request.getTaxId())) {
            throw new RuntimeException("Supplier with this taxId already exists");
        }

        supplier.setName(request.getName());
        supplier.setTaxId(request.getTaxId());

        return mapper.toResponse(repository.save(supplier));
    }

    @Override
    public void delete(Long id) {

        Supplier supplier = repository.findById(id)
                .orElseThrow(() -> new RuntimeException("Supplier not found"));

        // 🔒 Regla de negocio
        if (variableCostRepository.existsBySupplierIdAndDeletedAtIsNull(id)) {
            throw new IllegalStateException(
                    "Cannot delete supplier with existing variable costs"
            );
        }

        supplier.setDeletedAt(LocalDateTime.now());
        supplier.setDeletedBy(
                SecurityContextHolder.getContext()
                        .getAuthentication()
                        .getName()
        );

        repository.save(supplier);
    }
}
