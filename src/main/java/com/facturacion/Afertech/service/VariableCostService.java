package com.facturacion.Afertech.service;

import com.facturacion.Afertech.dto.VariableCostRequest;
import com.facturacion.Afertech.dto.VariableCostResponse;

import java.util.List;

public interface VariableCostService {

    List<VariableCostResponse> findAll();

    VariableCostResponse findById(Long id);

    VariableCostResponse create(VariableCostRequest request);

    VariableCostResponse update(Long id, VariableCostRequest request);

    void delete(Long id);
}
