package com.facturacion.Afertech.service.impl;

import com.facturacion.Afertech.dto.VariableCostRequest;
import com.facturacion.Afertech.dto.VariableCostResponse;
import com.facturacion.Afertech.mapper.VariableCostMapper;
import com.facturacion.Afertech.model.Project;
import com.facturacion.Afertech.model.VariableCost;
import com.facturacion.Afertech.model.VariableCostType;
import com.facturacion.Afertech.repository.ProjectRepository;
import com.facturacion.Afertech.repository.VariableCostRepository;
import com.facturacion.Afertech.repository.VariableCostTypeRepository;
import com.facturacion.Afertech.service.VariableCostService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class VariableCostServiceImpl implements VariableCostService {

    private final VariableCostRepository repository;
    private final VariableCostTypeRepository costTypeRepository;
    private final ProjectRepository projectRepository;

    public VariableCostServiceImpl(
            VariableCostRepository repository,
            VariableCostTypeRepository costTypeRepository,
            ProjectRepository projectRepository
    ) {
        this.repository = repository;
        this.costTypeRepository = costTypeRepository;
        this.projectRepository = projectRepository;
    }

    @Override
    public List<VariableCostResponse> findAll() {
        return repository.findAll()
                .stream()
                .map(VariableCostMapper::toResponse)
                .toList();
    }

    @Override
    public VariableCostResponse findById(Long id) {
        VariableCost cost = repository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("VariableCost not found"));
        return VariableCostMapper.toResponse(cost);
    }

    @Override
    public VariableCostResponse create(VariableCostRequest request) {
        VariableCostType costType = costTypeRepository.findById(request.getCostTypeId())
                .orElseThrow(() -> new IllegalArgumentException("VariableCostType not found"));

        Project project = null;
        if (request.getProjectId() != null) {
            project = projectRepository.findById(request.getProjectId())
                    .orElseThrow(() -> new IllegalArgumentException("Project not found"));
        }

        VariableCost cost = new VariableCost();
        cost.setCostType(costType);
        cost.setDescription(request.getDescription());
        cost.setAmount(request.getAmount());
        cost.setDate(request.getDate());
        cost.setBusinessName(request.getBusinessName());
        cost.setProject(project);

        return VariableCostMapper.toResponse(repository.save(cost));
    }

    @Override
    public VariableCostResponse update(Long id, VariableCostRequest request) {
        VariableCost cost = repository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("VariableCost not found"));

        VariableCostType costType = costTypeRepository.findById(request.getCostTypeId())
                .orElseThrow(() -> new IllegalArgumentException("VariableCostType not found"));

        Project project = null;
        if (request.getProjectId() != null) {
            project = projectRepository.findById(request.getProjectId())
                    .orElseThrow(() -> new IllegalArgumentException("Project not found"));
        }

        cost.setCostType(costType);
        cost.setDescription(request.getDescription());
        cost.setAmount(request.getAmount());
        cost.setDate(request.getDate());
        cost.setBusinessName(request.getBusinessName());
        cost.setProject(project);

        return VariableCostMapper.toResponse(repository.save(cost));
    }

    @Override
    public void delete(Long id) {
        repository.deleteById(id);
    }
}
