package com.facturacion.Afertech.service;

import com.facturacion.Afertech.dto.EmployeeRequest;
import com.facturacion.Afertech.dto.EmployeeResponse;

import java.util.List;

public interface EmployeeService {

    List<EmployeeResponse> findAll();

    EmployeeResponse findById(Long id);

    EmployeeResponse create(EmployeeRequest request);

    EmployeeResponse update(Long id, EmployeeRequest request);

    void deactivate(Long id);
}
