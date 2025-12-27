package com.facturacion.Afertech.service.impl;

import com.facturacion.Afertech.dto.FixedCostRequest;
import com.facturacion.Afertech.dto.FixedCostResponse;
import com.facturacion.Afertech.mapper.FixedCostMapper;
import com.facturacion.Afertech.model.CostType;
import com.facturacion.Afertech.model.FixedCost;
import com.facturacion.Afertech.repository.CostTypeRepository;
import com.facturacion.Afertech.repository.FixedCostRepository;
import com.facturacion.Afertech.service.FixedCostService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class FixedCostServiceImpl implements FixedCostService {

    private final FixedCostRepository repository;
    private final CostTypeRepository costTypeRepository;
    private final FixedCostMapper mapper;

    public FixedCostServiceImpl(
            FixedCostRepository repository,
            CostTypeRepository costTypeRepository,
            FixedCostMapper mapper
    ) {
        this.repository = repository;
        this.costTypeRepository = costTypeRepository;
        this.mapper = mapper;
    }

    @Override
    public List<FixedCostResponse> findAll() {
        return repository.findAll()
                .stream()
                .map(mapper::toResponse)
                .toList();
    }

    @Override
    public FixedCostResponse findById(Long id) {
        FixedCost fixedCost = repository.findById(id)
                .orElseThrow(() -> new RuntimeException("Fixed cost not found"));
        return mapper.toResponse(fixedCost);
    }

    @Override
    public FixedCostResponse create(FixedCostRequest request) {

        CostType costType = costTypeRepository.findById(request.getCostTypeId())
                .orElseThrow(() -> new RuntimeException("Cost type not found"));

        FixedCost fixedCost = new FixedCost();
        fixedCost.setCostType(costType);
        fixedCost.setAmount(request.getAmount());
        fixedCost.setAllocationMonth(request.getAllocationMonth());
        fixedCost.setPaymentDate(request.getPaymentDate());
        fixedCost.setDescription(request.getDescription());

        return mapper.toResponse(repository.save(fixedCost));
    }

    @Override
    public FixedCostResponse update(Long id, FixedCostRequest request) {

        FixedCost fixedCost = repository.findById(id)
                .orElseThrow(() -> new RuntimeException("Fixed cost not found"));

        CostType costType = costTypeRepository.findById(request.getCostTypeId())
                .orElseThrow(() -> new RuntimeException("Cost type not found"));

        fixedCost.setCostType(costType);
        fixedCost.setAmount(request.getAmount());
        fixedCost.setAllocationMonth(request.getAllocationMonth());
        fixedCost.setPaymentDate(request.getPaymentDate());
        fixedCost.setDescription(request.getDescription());

        return mapper.toResponse(repository.save(fixedCost));
    }

    @Override
    public void delete(Long id) {
        FixedCost fixedCost = repository.findById(id)
                .orElseThrow(() -> new RuntimeException("Fixed cost not found"));
        repository.delete(fixedCost);
    }
}
