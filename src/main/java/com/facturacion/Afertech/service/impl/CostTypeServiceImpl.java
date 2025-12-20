package com.facturacion.Afertech.service.impl;

import com.facturacion.Afertech.dto.CostTypeRequest;
import com.facturacion.Afertech.dto.CostTypeResponse;
import com.facturacion.Afertech.mapper.CostTypeMapper;
import com.facturacion.Afertech.model.CostType;
import com.facturacion.Afertech.repository.CostTypeRepository;
import com.facturacion.Afertech.service.CostTypeService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CostTypeServiceImpl implements CostTypeService {

    private final CostTypeRepository repository;

    public CostTypeServiceImpl(CostTypeRepository repository) {
        this.repository = repository;
    }

    @Override
    public List<CostTypeResponse> findAll() {
        return repository.findAll()
                .stream()
                .map(CostTypeMapper::toResponse)
                .toList();
    }

    @Override
    public CostTypeResponse findById(Long id) {
        CostType type = repository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("CostType not found"));
        return CostTypeMapper.toResponse(type);
    }

    @Override
    public CostTypeResponse create(CostTypeRequest request) {
        CostType type = CostTypeMapper.toEntity(request);
        return CostTypeMapper.toResponse(repository.save(type));
    }

    @Override
    public CostTypeResponse update(Long id, CostTypeRequest request) {
        CostType type = repository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("CostType not found"));

        type.setName(request.getName());
        return CostTypeMapper.toResponse(repository.save(type));
    }

    @Override
    public void delete(Long id) {
        repository.deleteById(id);
    }
}
