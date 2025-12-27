package com.facturacion.Afertech.service;

import com.facturacion.Afertech.dto.SupplierRequest;
import com.facturacion.Afertech.dto.SupplierResponse;

import java.util.List;

public interface SupplierService {

    List<SupplierResponse> findAll();

    SupplierResponse findById(Long id);

    SupplierResponse create(SupplierRequest request);

    SupplierResponse update(Long id, SupplierRequest request);

    void deactivate(Long id);
}
