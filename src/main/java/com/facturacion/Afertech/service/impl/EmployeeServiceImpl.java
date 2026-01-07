package com.facturacion.Afertech.service.impl;

import com.facturacion.Afertech.dto.EmployeeRequest;
import com.facturacion.Afertech.dto.EmployeeResponse;
import com.facturacion.Afertech.mapper.EmployeeMapper;
import com.facturacion.Afertech.model.Employee;
import com.facturacion.Afertech.repository.EmployeeRepository;
import com.facturacion.Afertech.repository.FixedCostRepository;
import com.facturacion.Afertech.service.EmployeeService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class EmployeeServiceImpl implements EmployeeService {

    private final EmployeeRepository repository;
    private final FixedCostRepository fixedCostRepository;
    private final EmployeeMapper mapper;

    public EmployeeServiceImpl(
            EmployeeRepository repository,
            FixedCostRepository fixedCostRepository,
            EmployeeMapper mapper
    ) {
        this.repository = repository;
        this.fixedCostRepository = fixedCostRepository;
        this.mapper = mapper;
    }

    @Override
    public Page<EmployeeResponse> findAll(Pageable pageable) {
        return repository.findAll(pageable)
                .map(mapper::toResponse);
    }

    @Override
    public EmployeeResponse findById(Long id) {
        Employee employee = repository.findById(id)
                .orElseThrow(() -> new RuntimeException("Employee not found"));
        return mapper.toResponse(employee);
    }

    @Override
    public EmployeeResponse create(EmployeeRequest request) {

        if (repository.existsByDocumentNumber(request.getDocumentNumber())) {
            throw new RuntimeException("Employee with this document already exists");
        }

        Employee employee = mapper.toEntity(request);
        employee.setLoadedBy(
                SecurityContextHolder.getContext()
                        .getAuthentication()
                        .getName()
        );

        return mapper.toResponse(repository.save(employee));
    }

    @Override
    public EmployeeResponse update(Long id, EmployeeRequest request) {

        Employee employee = repository.findById(id)
                .orElseThrow(() -> new RuntimeException("Employee not found"));

        if (!employee.getDocumentNumber().equals(request.getDocumentNumber())
                && repository.existsByDocumentNumber(request.getDocumentNumber())) {
            throw new RuntimeException("Employee with this document already exists");
        }

        employee.setFirstName(request.getFirstName());
        employee.setLastName(request.getLastName());
        employee.setDocumentNumber(request.getDocumentNumber());
        employee.setHireDate(request.getHireDate());
        employee.setTerminationDate(request.getTerminationDate());

        return mapper.toResponse(repository.save(employee));
    }

    @Override
    public void delete(Long id) {

        Employee employee = repository.findById(id)
                .orElseThrow(() -> new RuntimeException("Employee not found"));

        // 🔒 Regla SUELDO
        if (fixedCostRepository
                .existsByEmployeeIdAndCostType_NameIgnoreCaseAndDeletedAtIsNull(id, "SUELDO")) {
            throw new IllegalStateException(
                    "Cannot delete employee with salary fixed costs"
            );
        }

        employee.setDeletedAt(LocalDateTime.now());
        employee.setDeletedBy(
                SecurityContextHolder.getContext()
                        .getAuthentication()
                        .getName()
        );

        repository.save(employee);
    }
}
