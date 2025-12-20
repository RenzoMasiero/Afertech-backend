package com.facturacion.Afertech.service;

import com.facturacion.Afertech.dto.InvoiceRequest;
import com.facturacion.Afertech.dto.InvoiceResponse;

import java.util.List;

public interface InvoiceService {

    List<InvoiceResponse> findAll();

    InvoiceResponse findById(Long id);

    InvoiceResponse create(InvoiceRequest request);

    InvoiceResponse update(Long id, InvoiceRequest request);

    void delete(Long id);
}
