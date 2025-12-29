package com.facturacion.Afertech.service.impl;

import com.facturacion.Afertech.dto.InvoiceRequest;
import com.facturacion.Afertech.dto.InvoiceResponse;
import com.facturacion.Afertech.mapper.InvoiceMapper;
import com.facturacion.Afertech.model.Invoice;
import com.facturacion.Afertech.repository.InvoiceRepository;
import com.facturacion.Afertech.service.InvoiceService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class InvoiceServiceImpl implements InvoiceService {

    private static final Logger log = LoggerFactory.getLogger(InvoiceServiceImpl.class);

    private final InvoiceRepository repository;
    private final InvoiceMapper mapper;

    public InvoiceServiceImpl(InvoiceRepository repository, InvoiceMapper mapper) {
        this.repository = repository;
        this.mapper = mapper;
    }

    @Override
    public Page<InvoiceResponse> findAll(Pageable pageable) {
        return repository.findAll(pageable)
                .map(mapper::toResponse);
    }

    @Override
    public InvoiceResponse findById(Long id) {
        Invoice invoice = repository.findById(id)
                .orElseThrow(() -> new RuntimeException("Invoice not found"));
        return mapper.toResponse(invoice);
    }

    @Override
    public InvoiceResponse create(InvoiceRequest request) {

        Authentication auth = SecurityContextHolder
                .getContext()
                .getAuthentication();

        log.info(
                "AUDIT-TEST | name={} | principal={} | authorities={}",
                auth.getName(),
                auth.getPrincipal().getClass().getSimpleName(),
                auth.getAuthorities()
        );

        Invoice invoice = mapper.toEntity(request);
        return mapper.toResponse(repository.save(invoice));
    }

    @Override
    public InvoiceResponse update(Long id, InvoiceRequest request) {
        Invoice invoice = repository.findById(id)
                .orElseThrow(() -> new RuntimeException("Invoice not found"));

        Invoice updated = mapper.toEntity(request);
        updated.setId(invoice.getId());

        return mapper.toResponse(repository.save(updated));
    }

    @Override
    public void delete(Long id) {

        Invoice invoice = repository.findById(id)
                .orElseThrow(() -> new RuntimeException("Invoice not found"));

        Authentication auth = SecurityContextHolder
                .getContext()
                .getAuthentication();

        invoice.setDeletedAt(LocalDateTime.now());
        invoice.setDeletedBy(auth.getName());

        repository.save(invoice);
    }
}
