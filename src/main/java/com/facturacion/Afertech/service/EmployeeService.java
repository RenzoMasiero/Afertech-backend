package com.facturacion.Afertech.service;

import com.facturacion.Afertech.dto.EmployeeRequest;
import com.facturacion.Afertech.dto.EmployeeResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface EmployeeService {

    Page<EmployeeResponse> findAll(Pageable pageable);

    EmployeeResponse findById(Long id);

    EmployeeResponse create(EmployeeRequest request);

    EmployeeResponse update(Long id, EmployeeRequest request);

    void deactivate(Long id);
}
