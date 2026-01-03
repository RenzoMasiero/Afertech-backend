package com.facturacion.Afertech.service.impl;

import com.facturacion.Afertech.dto.PurchaseOrderRequest;
import com.facturacion.Afertech.dto.PurchaseOrderResponse;
import com.facturacion.Afertech.mapper.PurchaseOrderMapper;
import com.facturacion.Afertech.model.Client;
import com.facturacion.Afertech.model.Project;
import com.facturacion.Afertech.model.PurchaseOrder;
import com.facturacion.Afertech.repository.ClientRepository;
import com.facturacion.Afertech.repository.ProjectRepository;
import com.facturacion.Afertech.repository.PurchaseOrderRepository;
import com.facturacion.Afertech.service.PurchaseOrderService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class PurchaseOrderServiceImpl implements PurchaseOrderService {

    private final PurchaseOrderRepository repository;
    private final ClientRepository clientRepository;
    private final ProjectRepository projectRepository;
    private final PurchaseOrderMapper mapper;

    public PurchaseOrderServiceImpl(
            PurchaseOrderRepository repository,
            ClientRepository clientRepository,
            ProjectRepository projectRepository,
            PurchaseOrderMapper mapper
    ) {
        this.repository = repository;
        this.clientRepository = clientRepository;
        this.projectRepository = projectRepository;
        this.mapper = mapper;
    }

    @Override
    public Page<PurchaseOrderResponse> findAll(Pageable pageable) {
        return repository.findAll(pageable).map(mapper::toResponse);
    }

    @Override
    public PurchaseOrderResponse findById(Long id) {
        PurchaseOrder po = repository.findById(id)
                .orElseThrow(() -> new RuntimeException("Purchase order not found"));
        return mapper.toResponse(po);
    }

    @Override
    public PurchaseOrderResponse create(PurchaseOrderRequest request) {

        Authentication auth = SecurityContextHolder.getContext().getAuthentication();

        Client client = clientRepository.findById(request.getClientId())
                .orElseThrow(() -> new RuntimeException("Client not found"));

        Project project = projectRepository.findById(request.getProjectId())
                .orElseThrow(() -> new RuntimeException("Project not found"));

        PurchaseOrder po = mapper.toEntity(request);
        po.setClient(client);
        po.setProject(project);
        po.setLoadedBy(auth.getName());

        return mapper.toResponse(repository.save(po));
    }

    @Override
    public PurchaseOrderResponse update(Long id, PurchaseOrderRequest request) {

        PurchaseOrder existing = repository.findById(id)
                .orElseThrow(() -> new RuntimeException("Purchase order not found"));

        Client client = clientRepository.findById(request.getClientId())
                .orElseThrow(() -> new RuntimeException("Client not found"));

        Project project = projectRepository.findById(request.getProjectId())
                .orElseThrow(() -> new RuntimeException("Project not found"));

        PurchaseOrder updated = mapper.toEntity(request);
        updated.setId(existing.getId());
        updated.setClient(client);
        updated.setProject(project);

        return mapper.toResponse(repository.save(updated));
    }

    @Override
    public void delete(Long id) {

        PurchaseOrder po = repository.findById(id)
                .orElseThrow(() -> new RuntimeException("Purchase order not found"));

        Authentication auth = SecurityContextHolder.getContext().getAuthentication();

        po.setDeletedAt(LocalDateTime.now());
        po.setDeletedBy(auth.getName());

        repository.save(po);
    }
}
