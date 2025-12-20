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

    public FixedCostServiceImpl(
            FixedCostRepository repository,
            CostTypeRepository costTypeRepository
    ) {
        this.repository = repository;
        this.costTypeRepository = costTypeRepository;
    }

    @Override
    public List<FixedCostResponse> findAll() {
        return repository.findAll()
                .stream()
                .map(FixedCostMapper::toResponse)
                .toList();
    }

    @Override
    public FixedCostResponse findById(Long id) {
        FixedCost fixedCost = repository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("FixedCost not found"));
        return FixedCostMapper.toResponse(fixedCost);
    }

    @Override
    public FixedCostResponse create(FixedCostRequest request) {
        CostType costType = costTypeRepository.findById(request.getCostTypeId())
                .orElseThrow(() -> new IllegalArgumentException("CostType not found"));

        FixedCost fixedCost = new FixedCost();
        fixedCost.setCostType(costType);
        fixedCost.setDescription(request.getDescription());
        fixedCost.setAmount(request.getAmount());
        fixedCost.setDate(request.getDate());

        return FixedCostMapper.toResponse(repository.save(fixedCost));
    }

    @Override
    public FixedCostResponse update(Long id, FixedCostRequest request) {
        FixedCost fixedCost = repository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("FixedCost not found"));

        CostType costType = costTypeRepository.findById(request.getCostTypeId())
                .orElseThrow(() -> new IllegalArgumentException("CostType not found"));

        fixedCost.setCostType(costType);
        fixedCost.setDescription(request.getDescription());
        fixedCost.setAmount(request.getAmount());
        fixedCost.setDate(request.getDate());

        return FixedCostMapper.toResponse(repository.save(fixedCost));
    }

    @Override
    public void delete(Long id) {
        repository.deleteById(id);
    }
}
