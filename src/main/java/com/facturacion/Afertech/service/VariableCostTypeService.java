package com.facturacion.Afertech.service;

import com.facturacion.Afertech.dto.VariableCostTypeRequest;
import com.facturacion.Afertech.dto.VariableCostTypeResponse;

import java.util.List;

public interface VariableCostTypeService {

    List<VariableCostTypeResponse> findAll();

    VariableCostTypeResponse findById(Long id);

    VariableCostTypeResponse create(VariableCostTypeRequest request);

    VariableCostTypeResponse update(Long id, VariableCostTypeRequest request);

    void delete(Long id);
}
