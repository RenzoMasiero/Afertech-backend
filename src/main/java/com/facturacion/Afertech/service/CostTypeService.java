package com.facturacion.Afertech.service;

import com.facturacion.Afertech.dto.CostTypeRequest;
import com.facturacion.Afertech.dto.CostTypeResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface CostTypeService {

    Page<CostTypeResponse> findAll(Pageable pageable);

    CostTypeResponse findById(Long id);

    CostTypeResponse create(CostTypeRequest request);

    CostTypeResponse update(Long id, CostTypeRequest request);

    void delete(Long id);
}

