package com.facturacion.Afertech.service.impl;

import com.facturacion.Afertech.dto.VariableCostRequest;
import com.facturacion.Afertech.dto.VariableCostResponse;
import com.facturacion.Afertech.mapper.VariableCostMapper;
import com.facturacion.Afertech.model.Project;
import com.facturacion.Afertech.model.Supplier;
import com.facturacion.Afertech.model.VariableCost;
import com.facturacion.Afertech.model.VariableCostType;
import com.facturacion.Afertech.repository.ProjectRepository;
import com.facturacion.Afertech.repository.SupplierRepository;
import com.facturacion.Afertech.repository.VariableCostRepository;
import com.facturacion.Afertech.repository.VariableCostTypeRepository;
import com.facturacion.Afertech.service.VariableCostService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.YearMonth;
import java.time.format.DateTimeParseException;

@Service
public class VariableCostServiceImpl implements VariableCostService {

    private final VariableCostRepository repository;
    private final VariableCostTypeRepository typeRepository;
    private final SupplierRepository supplierRepository;
    private final ProjectRepository projectRepository;
    private final VariableCostMapper mapper;

    public VariableCostServiceImpl(
            VariableCostRepository repository,
            VariableCostTypeRepository typeRepository,
            SupplierRepository supplierRepository,
            ProjectRepository projectRepository,
            VariableCostMapper mapper
    ) {
        this.repository = repository;
        this.typeRepository = typeRepository;
        this.supplierRepository = supplierRepository;
        this.projectRepository = projectRepository;
        this.mapper = mapper;
    }

    @Override
    public Page<VariableCostResponse> findAll(Pageable pageable) {
        return repository.findAll(pageable).map(mapper::toResponse);
    }

    @Override
    public VariableCostResponse findById(Long id) {
        VariableCost cost = repository.findById(id)
                .orElseThrow(() -> new RuntimeException("Variable cost not found"));
        return mapper.toResponse(cost);
    }

    @Override
    public VariableCostResponse create(VariableCostRequest request) {

        VariableCostType type = typeRepository.findById(request.getCostTypeId())
                .orElseThrow(() -> new RuntimeException("Variable cost type not found"));

        Supplier supplier = supplierRepository.findById(request.getSupplierId())
                .orElseThrow(() -> new RuntimeException("Supplier not found"));

        Project project = null;
        if (request.getProjectId() != null) {
            project = projectRepository.findById(request.getProjectId())
                    .orElseThrow(() -> new RuntimeException("Project not found"));
        }

        validateAllocationMonth(request.getAllocationMonth());

        Authentication auth = SecurityContextHolder.getContext().getAuthentication();

        VariableCost cost = new VariableCost();
        cost.setCostType(type);
        cost.setAmount(request.getAmount());
        cost.setAllocationMonth(request.getAllocationMonth());
        cost.setPaymentDate(request.getPaymentDate());
        cost.setSupplier(supplier);
        cost.setProject(project);
        cost.setDescription(request.getDescription());
        cost.setLoadedBy(auth.getName());

        return mapper.toResponse(repository.save(cost));
    }

    @Override
    public VariableCostResponse update(Long id, VariableCostRequest request) {

        VariableCost cost = repository.findById(id)
                .orElseThrow(() -> new RuntimeException("Variable cost not found"));

        VariableCostType type = typeRepository.findById(request.getCostTypeId())
                .orElseThrow(() -> new RuntimeException("Variable cost type not found"));

        Supplier supplier = supplierRepository.findById(request.getSupplierId())
                .orElseThrow(() -> new RuntimeException("Supplier not found"));

        Project project = null;
        if (request.getProjectId() != null) {
            project = projectRepository.findById(request.getProjectId())
                    .orElseThrow(() -> new RuntimeException("Project not found"));
        }

        validateAllocationMonth(request.getAllocationMonth());

        cost.setCostType(type);
        cost.setAmount(request.getAmount());
        cost.setAllocationMonth(request.getAllocationMonth());
        cost.setPaymentDate(request.getPaymentDate());
        cost.setSupplier(supplier);
        cost.setProject(project);
        cost.setDescription(request.getDescription());

        return mapper.toResponse(repository.save(cost));
    }

    @Override
    public void delete(Long id) {

        VariableCost cost = repository.findById(id)
                .orElseThrow(() -> new RuntimeException("Variable cost not found"));

        Authentication auth = SecurityContextHolder.getContext().getAuthentication();

        cost.setDeletedAt(LocalDateTime.now());
        cost.setDeletedBy(auth.getName());

        repository.save(cost);
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
