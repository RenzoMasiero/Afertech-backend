package com.facturacion.Afertech.service.impl;

import com.facturacion.Afertech.dto.VariableCostTypeRequest;
import com.facturacion.Afertech.dto.VariableCostTypeResponse;
import com.facturacion.Afertech.mapper.VariableCostTypeMapper;
import com.facturacion.Afertech.model.VariableCostType;
import com.facturacion.Afertech.repository.VariableCostTypeRepository;
import com.facturacion.Afertech.service.VariableCostTypeService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class VariableCostTypeServiceImpl implements VariableCostTypeService {

    private final VariableCostTypeRepository repository;
    private final VariableCostTypeMapper mapper;

    public VariableCostTypeServiceImpl(
            VariableCostTypeRepository repository,
            VariableCostTypeMapper mapper
    ) {
        this.repository = repository;
        this.mapper = mapper;
    }

    @Override
    public List<VariableCostTypeResponse> findAll() {
        return repository.findAll()
                .stream()
                .map(mapper::toResponse)
                .toList();
    }

    @Override
    public VariableCostTypeResponse findById(Long id) {
        VariableCostType type = repository.findById(id)
                .orElseThrow(() -> new RuntimeException("Variable cost type not found"));
        return mapper.toResponse(type);
    }

    @Override
    public VariableCostTypeResponse create(VariableCostTypeRequest request) {
        VariableCostType type = mapper.toEntity(request);
        return mapper.toResponse(repository.save(type));
    }

    @Override
    public VariableCostTypeResponse update(Long id, VariableCostTypeRequest request) {
        VariableCostType type = repository.findById(id)
                .orElseThrow(() -> new RuntimeException("Variable cost type not found"));

        type.setName(request.getName());
        return mapper.toResponse(repository.save(type));
    }

    @Override
    public void delete(Long id) {
        VariableCostType type = repository.findById(id)
                .orElseThrow(() -> new RuntimeException("Variable cost type not found"));
        repository.delete(type);
    }
}
