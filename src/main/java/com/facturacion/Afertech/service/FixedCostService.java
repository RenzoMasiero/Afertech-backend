package com.facturacion.Afertech.service;

import com.facturacion.Afertech.dto.FixedCostRequest;
import com.facturacion.Afertech.dto.FixedCostResponse;

import java.util.List;

public interface FixedCostService {

    List<FixedCostResponse> findAll();

    FixedCostResponse findById(Long id);

    FixedCostResponse create(FixedCostRequest request);

    FixedCostResponse update(Long id, FixedCostRequest request);

    void delete(Long id);
}
