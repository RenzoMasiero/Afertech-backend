package com.facturacion.Afertech.service.impl;

import com.facturacion.Afertech.dto.ReminderRecipientRequest;
import com.facturacion.Afertech.dto.ReminderRecipientResponse;
import com.facturacion.Afertech.mapper.ReminderRecipientMapper;
import com.facturacion.Afertech.model.ReminderRecipient;
import com.facturacion.Afertech.repository.ReminderRecipientRepository;
import com.facturacion.Afertech.service.ReminderRecipientService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class ReminderRecipientServiceImpl implements ReminderRecipientService {

    private final ReminderRecipientRepository repository;
    private final ReminderRecipientMapper mapper;

    public ReminderRecipientServiceImpl(
            ReminderRecipientRepository repository,
            ReminderRecipientMapper mapper
    ) {
        this.repository = repository;
        this.mapper = mapper;
    }

    @Override
    public Page<ReminderRecipientResponse> findAll(Pageable pageable) {
        return repository.findAll(pageable)
                .map(mapper::toResponse);
    }

    @Override
    public ReminderRecipientResponse findById(Long id) {
        ReminderRecipient entity = repository.findById(id)
                .orElseThrow(() -> new RuntimeException("ReminderRecipient not found"));
        return mapper.toResponse(entity);
    }

    @Override
    public ReminderRecipientResponse create(ReminderRecipientRequest request) {

        if (repository.existsByEmailAndDeletedAtIsNull(request.getEmail())) {
            throw new IllegalStateException("Email already exists");
        }

        ReminderRecipient entity = mapper.toEntity(request);

        entity.setLoadedBy(
                SecurityContextHolder.getContext()
                        .getAuthentication()
                        .getName()
        );

        if (entity.getActive() == null) {
            entity.setActive(true);
        }

        return mapper.toResponse(repository.save(entity));
    }

    @Override
    public ReminderRecipientResponse update(Long id, ReminderRecipientRequest request) {

        ReminderRecipient entity = repository.findById(id)
                .orElseThrow(() -> new RuntimeException("ReminderRecipient not found"));

        if (!entity.getEmail().equals(request.getEmail())
                && repository.existsByEmailAndDeletedAtIsNull(request.getEmail())) {
            throw new IllegalStateException("Email already exists");
        }

        entity.setEmail(request.getEmail());

        if (request.getActive() != null) {
            entity.setActive(request.getActive());
        }

        return mapper.toResponse(repository.save(entity));
    }

    @Override
    public void delete(Long id) {

        ReminderRecipient entity = repository.findById(id)
                .orElseThrow(() -> new RuntimeException("ReminderRecipient not found"));

        entity.setDeletedAt(LocalDateTime.now());
        entity.setDeletedBy(
                SecurityContextHolder.getContext()
                        .getAuthentication()
                        .getName()
        );

        repository.save(entity);
    }
}
