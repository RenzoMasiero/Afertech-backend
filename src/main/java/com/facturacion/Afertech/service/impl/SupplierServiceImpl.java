package com.facturacion.Afertech.service.impl;

import com.facturacion.Afertech.dto.SupplierRequest;
import com.facturacion.Afertech.dto.SupplierResponse;
import com.facturacion.Afertech.mapper.SupplierMapper;
import com.facturacion.Afertech.model.Supplier;
import com.facturacion.Afertech.repository.SupplierRepository;
import com.facturacion.Afertech.service.SupplierService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SupplierServiceImpl implements SupplierService {

    private final SupplierRepository repository;
    private final SupplierMapper mapper;

    public SupplierServiceImpl(SupplierRepository repository, SupplierMapper mapper) {
        this.repository = repository;
        this.mapper = mapper;
    }

    @Override
    public List<SupplierResponse> findAll() {
        return repository.findAll()
                .stream()
                .map(mapper::toResponse)
                .toList();
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
        supplier.setActive(request.getActive());

        return mapper.toResponse(repository.save(supplier));
    }

    @Override
    public void deactivate(Long id) {
        Supplier supplier = repository.findById(id)
                .orElseThrow(() -> new RuntimeException("Supplier not found"));

        supplier.setActive(false);
        repository.save(supplier);
    }
}
