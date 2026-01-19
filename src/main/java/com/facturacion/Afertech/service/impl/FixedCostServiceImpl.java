package com.facturacion.Afertech.service.impl;

import com.facturacion.Afertech.dto.FixedCostRequest;
import com.facturacion.Afertech.dto.FixedCostResponse;
import com.facturacion.Afertech.mapper.FixedCostMapper;
import com.facturacion.Afertech.model.CostType;
import com.facturacion.Afertech.model.Employee;
import com.facturacion.Afertech.model.FixedCost;
import com.facturacion.Afertech.repository.CostTypeRepository;
import com.facturacion.Afertech.repository.EmployeeRepository;
import com.facturacion.Afertech.repository.FixedCostRepository;
import com.facturacion.Afertech.service.FixedCostService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.YearMonth;
import java.time.format.DateTimeParseException;

@Service
public class FixedCostServiceImpl implements FixedCostService {

    private final FixedCostRepository repository;
    private final CostTypeRepository costTypeRepository;
    private final EmployeeRepository employeeRepository;
    private final FixedCostMapper mapper;

    public FixedCostServiceImpl(
            FixedCostRepository repository,
            CostTypeRepository costTypeRepository,
            EmployeeRepository employeeRepository,
            FixedCostMapper mapper
    ) {
        this.repository = repository;
        this.costTypeRepository = costTypeRepository;
        this.employeeRepository = employeeRepository;
        this.mapper = mapper;
    }

    @Override
    public Page<FixedCostResponse> findAll(Pageable pageable) {
        return repository.findAll(pageable)
                .map(mapper::toResponse);
    }

    @Override
    public FixedCostResponse findById(Long id) {
        FixedCost fixedCost = repository.findById(id)
                .orElseThrow(() -> new RuntimeException("Fixed cost not found"));
        return mapper.toResponse(fixedCost);
    }

    @Override
    public FixedCostResponse create(FixedCostRequest request) {

        Authentication auth = SecurityContextHolder.getContext().getAuthentication();

        CostType costType = costTypeRepository.findById(request.getCostTypeId())
                .orElseThrow(() -> new RuntimeException("Cost type not found"));

        validateAllocationMonth(request.getAllocationMonth());

        FixedCost fixedCost = new FixedCost();
        fixedCost.setCostType(costType);
        fixedCost.setAmount(request.getAmount());
        fixedCost.setAllocationMonth(request.getAllocationMonth());
        fixedCost.setPaymentDate(request.getPaymentDate());
        fixedCost.setDescription(request.getDescription());

        // 🔒 Regla SUELDO (create)
        if ("SALARY".equalsIgnoreCase(costType.getName())
                || "SUELDO".equalsIgnoreCase(costType.getName())) {

            if (request.getEmployeeId() == null) {
                throw new RuntimeException("Employee is required for salary fixed cost");
            }

            Employee employee = employeeRepository.findById(request.getEmployeeId())
                    .orElseThrow(() -> new RuntimeException("Employee not found"));

            fixedCost.setEmployee(employee);
        }

        fixedCost.setLoadedBy(auth.getName());

        return mapper.toResponse(repository.save(fixedCost));
    }

    @Override
    public FixedCostResponse update(Long id, FixedCostRequest request) {

        FixedCost fixedCost = repository.findById(id)
                .orElseThrow(() -> new RuntimeException("Fixed cost not found"));

        CostType newCostType = costTypeRepository.findById(request.getCostTypeId())
                .orElseThrow(() -> new RuntimeException("Cost type not found"));

        boolean wasSalary =
                fixedCost.getCostType() != null &&
                        ("SALARY".equalsIgnoreCase(fixedCost.getCostType().getName())
                                || "SUELDO".equalsIgnoreCase(fixedCost.getCostType().getName()));

        boolean isSalary =
                "SALARY".equalsIgnoreCase(newCostType.getName())
                        || "SUELDO".equalsIgnoreCase(newCostType.getName());

        // 🔒 Regla B: no se puede pasar de SUELDO a otro tipo
        if (wasSalary && !isSalary) {
            throw new RuntimeException(
                    "Cannot change cost type from SALARY to a non-salary type"
            );
        }

        validateAllocationMonth(request.getAllocationMonth());

        fixedCost.setCostType(newCostType);
        fixedCost.setAmount(request.getAmount());
        fixedCost.setAllocationMonth(request.getAllocationMonth());
        fixedCost.setPaymentDate(request.getPaymentDate());
        fixedCost.setDescription(request.getDescription());

        // 🔒 Regla SUELDO (update)
        if (isSalary) {
            if (request.getEmployeeId() == null) {
                throw new RuntimeException("Employee is required for salary fixed cost");
            }

            Employee employee = employeeRepository.findById(request.getEmployeeId())
                    .orElseThrow(() -> new RuntimeException("Employee not found"));

            fixedCost.setEmployee(employee);
        }

        return mapper.toResponse(repository.save(fixedCost));
    }

    @Override
    public void delete(Long id) {

        FixedCost fixedCost = repository.findById(id)
                .orElseThrow(() -> new RuntimeException("Fixed cost not found"));

        Authentication auth = SecurityContextHolder.getContext().getAuthentication();

        fixedCost.setDeletedAt(LocalDateTime.now());
        fixedCost.setDeletedBy(auth.getName());

        repository.save(fixedCost);
    }

    // ==========================
    // Validación de dominio
    // ==========================
    private void validateAllocationMonth(String allocationMonth) {
        if (allocationMonth == null) {
            throw new RuntimeException("Allocation month is required");
        }

        try {
            YearMonth.parse(allocationMonth); // espera YYYY-MM
        } catch (DateTimeParseException ex) {
            throw new RuntimeException(
                    "Invalid allocationMonth format. Expected YYYY-MM"
            );
        }
    }
}
