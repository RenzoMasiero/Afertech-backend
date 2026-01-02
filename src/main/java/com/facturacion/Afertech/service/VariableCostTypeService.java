package com.facturacion.Afertech.service;

import com.facturacion.Afertech.dto.VariableCostTypeRequest;
import com.facturacion.Afertech.dto.VariableCostTypeResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface VariableCostTypeService {

    Page<VariableCostTypeResponse> findAll(Pageable pageable);

    VariableCostTypeResponse findById(Long id);

    VariableCostTypeResponse create(VariableCostTypeRequest request);

    VariableCostTypeResponse update(Long id, VariableCostTypeRequest request);

    void delete(Long id);
}
