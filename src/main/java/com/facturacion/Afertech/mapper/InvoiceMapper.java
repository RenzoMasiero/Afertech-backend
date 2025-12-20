package com.facturacion.Afertech.mapper;

import com.facturacion.Afertech.dto.InvoiceRequest;
import com.facturacion.Afertech.dto.InvoiceResponse;
import com.facturacion.Afertech.model.Invoice;

public class InvoiceMapper {

    public static Invoice toEntity(InvoiceRequest request) {
        Invoice invoice = new Invoice();
        invoice.setNumber(request.getNumber());
        invoice.setCustomerName(request.getCustomerName());
        invoice.setDate(request.getDate());
        invoice.setTotal(request.getTotal());
        return invoice;
    }

    public static InvoiceResponse toResponse(Invoice invoice) {
        InvoiceResponse response = new InvoiceResponse();
        response.setId(invoice.getId());
        response.setNumber(invoice.getNumber());
        response.setCustomerName(invoice.getCustomerName());
        response.setDate(invoice.getDate());
        response.setTotal(invoice.getTotal());
        return response;
    }
}
