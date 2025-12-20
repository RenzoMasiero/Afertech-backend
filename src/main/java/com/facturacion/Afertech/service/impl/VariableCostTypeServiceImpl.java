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

    public VariableCostTypeServiceImpl(VariableCostTypeRepository repository) {
        this.repository = repository;
    }

    @Override
    public List<VariableCostTypeResponse> findAll() {
        return repository.findAll()
                .stream()
                .map(VariableCostTypeMapper::toResponse)
                .toList();
    }

    @Override
    public VariableCostTypeResponse findById(Long id) {
        VariableCostType type = repository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("VariableCostType not found"));
        return VariableCostTypeMapper.toResponse(type);
    }

    @Override
    public VariableCostTypeResponse create(VariableCostTypeRequest request) {
        VariableCostType type = VariableCostTypeMapper.toEntity(request);
        return VariableCostTypeMapper.toResponse(repository.save(type));
    }

    @Override
    public VariableCostTypeResponse update(Long id, VariableCostTypeRequest request) {
        VariableCostType type = repository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("VariableCostType not found"));

        type.setName(request.getName());
        return VariableCostTypeMapper.toResponse(repository.save(type));
    }

    @Override
    public void delete(Long id) {
        repository.deleteById(id);
    }
}
