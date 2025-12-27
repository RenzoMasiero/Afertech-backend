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
    private final CostTypeMapper mapper;

    public CostTypeServiceImpl(CostTypeRepository repository, CostTypeMapper mapper) {
        this.repository = repository;
        this.mapper = mapper;
    }

    @Override
    public List<CostTypeResponse> findAll() {
        return repository.findAll()
                .stream()
                .map(mapper::toResponse)
                .toList();
    }

    @Override
    public CostTypeResponse findById(Long id) {
        CostType costType = repository.findById(id)
                .orElseThrow(() -> new RuntimeException("Cost type not found"));
        return mapper.toResponse(costType);
    }

    @Override
    public CostTypeResponse create(CostTypeRequest request) {
        CostType costType = mapper.toEntity(request);
        return mapper.toResponse(repository.save(costType));
    }

    @Override
    public CostTypeResponse update(Long id, CostTypeRequest request) {
        CostType costType = repository.findById(id)
                .orElseThrow(() -> new RuntimeException("Cost type not found"));

        costType.setName(request.getName());
        return mapper.toResponse(repository.save(costType));
    }

    @Override
    public void delete(Long id) {
        CostType costType = repository.findById(id)
                .orElseThrow(() -> new RuntimeException("Cost type not found"));
        repository.delete(costType);
    }
}

