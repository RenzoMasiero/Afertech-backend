package com.facturacion.Afertech.service;

import com.facturacion.Afertech.dto.VariableCostRequest;
import com.facturacion.Afertech.dto.VariableCostResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface VariableCostService {

    Page<VariableCostResponse> findAll(Pageable pageable);

    VariableCostResponse findById(Long id);

    VariableCostResponse create(VariableCostRequest request);

    VariableCostResponse update(Long id, VariableCostRequest request);

    void delete(Long id);
}
