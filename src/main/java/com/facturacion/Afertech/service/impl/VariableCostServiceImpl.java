package com.facturacion.Afertech.service.impl;

import com.facturacion.Afertech.dto.VariableCostRequest;
import com.facturacion.Afertech.dto.VariableCostResponse;
import com.facturacion.Afertech.mapper.VariableCostMapper;
import com.facturacion.Afertech.model.VariableCost;
import com.facturacion.Afertech.model.VariableCostType;
import com.facturacion.Afertech.repository.VariableCostRepository;
import com.facturacion.Afertech.repository.VariableCostTypeRepository;
import com.facturacion.Afertech.service.VariableCostService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class VariableCostServiceImpl implements VariableCostService {

    private final VariableCostRepository repository;
    private final VariableCostTypeRepository typeRepository;
    private final VariableCostMapper mapper;

    public VariableCostServiceImpl(
            VariableCostRepository repository,
            VariableCostTypeRepository typeRepository,
            VariableCostMapper mapper
    ) {
        this.repository = repository;
        this.typeRepository = typeRepository;
        this.mapper = mapper;
    }

    @Override
    public List<VariableCostResponse> findAll() {
        return repository.findAll()
                .stream()
                .map(mapper::toResponse)
                .toList();
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

        VariableCost cost = new VariableCost();
        cost.setCostType(type);
        cost.setAmount(request.getAmount());
        cost.setAllocationMonth(request.getAllocationMonth());
        cost.setPaymentDate(request.getPaymentDate());
        cost.setBusinessName(request.getBusinessName());
        cost.setDescription(request.getDescription());
        cost.setProjectNumber(request.getProjectNumber());

        return mapper.toResponse(repository.save(cost));
    }

    @Override
    public VariableCostResponse update(Long id, VariableCostRequest request) {

        VariableCost cost = repository.findById(id)
                .orElseThrow(() -> new RuntimeException("Variable cost not found"));

        VariableCostType type = typeRepository.findById(request.getCostTypeId())
                .orElseThrow(() -> new RuntimeException("Variable cost type not found"));

        cost.setCostType(type);
        cost.setAmount(request.getAmount());
        cost.setAllocationMonth(request.getAllocationMonth());
        cost.setPaymentDate(request.getPaymentDate());
        cost.setBusinessName(request.getBusinessName());
        cost.setDescription(request.getDescription());
        cost.setProjectNumber(request.getProjectNumber());

        return mapper.toResponse(repository.save(cost));
    }

    @Override
    public void delete(Long id) {
        VariableCost cost = repository.findById(id)
                .orElseThrow(() -> new RuntimeException("Variable cost not found"));
        repository.delete(cost);
    }
}
