package com.facturacion.Afertech.service;

import com.facturacion.Afertech.dto.CostTypeRequest;
import com.facturacion.Afertech.dto.CostTypeResponse;

import java.util.List;

public interface CostTypeService {

    List<CostTypeResponse> findAll();

    CostTypeResponse findById(Long id);

    CostTypeResponse create(CostTypeRequest request);

    CostTypeResponse update(Long id, CostTypeRequest request);

    void delete(Long id);
}
