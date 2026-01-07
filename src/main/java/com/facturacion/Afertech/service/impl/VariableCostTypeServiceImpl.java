package com.facturacion.Afertech.service.impl;

import com.facturacion.Afertech.dto.VariableCostTypeRequest;
import com.facturacion.Afertech.dto.VariableCostTypeResponse;
import com.facturacion.Afertech.mapper.VariableCostTypeMapper;
import com.facturacion.Afertech.model.VariableCostType;
import com.facturacion.Afertech.repository.VariableCostRepository;
import com.facturacion.Afertech.repository.VariableCostTypeRepository;
import com.facturacion.Afertech.service.VariableCostTypeService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class VariableCostTypeServiceImpl implements VariableCostTypeService {

    private final VariableCostTypeRepository repository;
    private final VariableCostRepository variableCostRepository;
    private final VariableCostTypeMapper mapper;

    public VariableCostTypeServiceImpl(
            VariableCostTypeRepository repository,
            VariableCostRepository variableCostRepository,
            VariableCostTypeMapper mapper
    ) {
        this.repository = repository;
        this.variableCostRepository = variableCostRepository;
        this.mapper = mapper;
    }

    @Override
    public Page<VariableCostTypeResponse> findAll(Pageable pageable) {
        return repository.findAll(pageable)
                .map(mapper::toResponse);
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
        type.setLoadedBy(
                SecurityContextHolder.getContext()
                        .getAuthentication()
                        .getName()
        );

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

        // 🔒 Regla de negocio
        if (variableCostRepository.existsByCostTypeIdAndDeletedAtIsNull(id)) {
            throw new IllegalStateException(
                    "Cannot delete variable cost type with existing variable costs"
            );
        }

        type.setDeletedAt(LocalDateTime.now());
        type.setDeletedBy(
                SecurityContextHolder.getContext()
                        .getAuthentication()
                        .getName()
        );

        repository.save(type);
    }
}
