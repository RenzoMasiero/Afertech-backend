package com.facturacion.Afertech.service;

import com.facturacion.Afertech.dto.FixedCostRequest;
import com.facturacion.Afertech.dto.FixedCostResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface FixedCostService {

    Page<FixedCostResponse> findAll(Pageable pageable);

    FixedCostResponse findById(Long id);

    FixedCostResponse create(FixedCostRequest request);

    FixedCostResponse update(Long id, FixedCostRequest request);

    void delete(Long id);
}
