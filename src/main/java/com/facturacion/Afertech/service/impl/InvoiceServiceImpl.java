package com.facturacion.Afertech.service.impl;

import com.facturacion.Afertech.dto.InvoiceRequest;
import com.facturacion.Afertech.dto.InvoiceResponse;
import com.facturacion.Afertech.mapper.InvoiceMapper;
import com.facturacion.Afertech.model.Invoice;
import com.facturacion.Afertech.repository.InvoiceRepository;
import com.facturacion.Afertech.service.InvoiceService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class InvoiceServiceImpl implements InvoiceService {

    private final InvoiceRepository repository;

    public InvoiceServiceImpl(InvoiceRepository repository) {
        this.repository = repository;
    }

    @Override
    public List<InvoiceResponse> findAll() {
        return repository.findAll()
                .stream()
                .map(InvoiceMapper::toResponse)
                .toList();
    }

    @Override
    public InvoiceResponse findById(Long id) {
        Invoice invoice = repository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Invoice not found"));
        return InvoiceMapper.toResponse(invoice);
    }

    @Override
    public InvoiceResponse create(InvoiceRequest request) {
        Invoice invoice = InvoiceMapper.toEntity(request);
        return InvoiceMapper.toResponse(repository.save(invoice));
    }

    @Override
    public InvoiceResponse update(Long id, InvoiceRequest request) {
        Invoice invoice = repository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Invoice not found"));

        invoice.setNumber(request.getNumber());
        invoice.setCustomerName(request.getCustomerName());
        invoice.setDate(request.getDate());
        invoice.setTotal(request.getTotal());

        return InvoiceMapper.toResponse(repository.save(invoice));
    }

    @Override
    public void delete(Long id) {
        repository.deleteById(id);
    }
}
