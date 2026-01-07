package com.facturacion.Afertech.service.impl;

import com.facturacion.Afertech.dto.ProjectRequest;
import com.facturacion.Afertech.dto.ProjectResponse;
import com.facturacion.Afertech.mapper.ProjectMapper;
import com.facturacion.Afertech.model.Project;
import com.facturacion.Afertech.repository.ProjectRepository;
import com.facturacion.Afertech.repository.PurchaseOrderRepository;
import com.facturacion.Afertech.repository.InvoiceRepository;
import com.facturacion.Afertech.repository.PaymentOrderRepository;
import com.facturacion.Afertech.repository.VariableCostRepository;
import com.facturacion.Afertech.service.ProjectService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class ProjectServiceImpl implements ProjectService {

    private final ProjectRepository repository;
    private final PurchaseOrderRepository purchaseOrderRepository;
    private final InvoiceRepository invoiceRepository;
    private final PaymentOrderRepository paymentOrderRepository;
    private final VariableCostRepository variableCostRepository;
    private final ProjectMapper mapper;

    public ProjectServiceImpl(
            ProjectRepository repository,
            PurchaseOrderRepository purchaseOrderRepository,
            InvoiceRepository invoiceRepository,
            PaymentOrderRepository paymentOrderRepository,
            VariableCostRepository variableCostRepository,
            ProjectMapper mapper
    ) {
        this.repository = repository;
        this.purchaseOrderRepository = purchaseOrderRepository;
        this.invoiceRepository = invoiceRepository;
        this.paymentOrderRepository = paymentOrderRepository;
        this.variableCostRepository = variableCostRepository;
        this.mapper = mapper;
    }

    @Override
    public Page<ProjectResponse> findAll(Pageable pageable) {
        return repository.findAll(pageable)
                .map(mapper::toResponse);
    }

    @Override
    public ProjectResponse findById(Long id) {
        Project project = repository.findById(id)
                .orElseThrow(() -> new RuntimeException("Project not found"));
        return mapper.toResponse(project);
    }

    @Override
    public ProjectResponse create(ProjectRequest request) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();

        Project project = mapper.toEntity(request);
        project.setLoadedBy(auth.getName());

        return mapper.toResponse(repository.save(project));
    }

    @Override
    public ProjectResponse update(Long id, ProjectRequest request) {
        Project project = repository.findById(id)
                .orElseThrow(() -> new RuntimeException("Project not found"));

        project.setName(request.getName());
        project.setDescription(request.getDescription());

        return mapper.toResponse(repository.save(project));
    }

    @Override
    public void delete(Long id) {

        Project project = repository.findById(id)
                .orElseThrow(() -> new RuntimeException("Project not found"));

        // 🔒 Reglas de negocio — dependencias
        if (purchaseOrderRepository.existsByProjectIdAndDeletedAtIsNull(id)) {
            throw new IllegalStateException("Cannot delete project with existing purchase orders");
        }

        if (invoiceRepository.existsByProjectIdAndDeletedAtIsNull(id)) {
            throw new IllegalStateException("Cannot delete project with existing invoices");
        }

        if (paymentOrderRepository.existsByProjectIdAndDeletedAtIsNull(id)) {
            throw new IllegalStateException("Cannot delete project with existing payment orders");
        }

        if (variableCostRepository.existsByProjectIdAndDeletedAtIsNull(id)) {
            throw new IllegalStateException("Cannot delete project with existing variable costs");
        }

        Authentication auth = SecurityContextHolder.getContext().getAuthentication();

        project.setDeletedAt(LocalDateTime.now());
        project.setDeletedBy(auth.getName());

        repository.save(project);
    }
}
