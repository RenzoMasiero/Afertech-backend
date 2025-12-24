package com.facturacion.Afertech.mapper;

import com.facturacion.Afertech.dto.InvoiceRequest;
import com.facturacion.Afertech.dto.InvoiceResponse;
import com.facturacion.Afertech.model.Invoice;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;

@Mapper(
        componentModel = "spring",
        unmappedTargetPolicy = ReportingPolicy.ERROR
)
public interface InvoiceMapper {

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    Invoice toEntity(InvoiceRequest request);

    @Mapping(target = "id", source = "id")
    @Mapping(target = "company", source = "company")
    @Mapping(target = "invoiceNumber", source = "invoiceNumber")
    @Mapping(target = "issueDate", source = "issueDate")
    @Mapping(target = "createdAt", source = "createdAt")
    @Mapping(target = "description", source = "description")
    @Mapping(target = "totalWithoutTax", source = "totalWithoutTax")
    @Mapping(target = "totalWithTax", source = "totalWithTax")
    @Mapping(target = "deferredPaymentDays", source = "deferredPaymentDays")
    @Mapping(target = "projectNumber", source = "projectNumber")
    @Mapping(target = "purchaseOrder", source = "purchaseOrder")
    @Mapping(target = "purchaseOrderPercentage", source = "purchaseOrderPercentage")
    @Mapping(target = "paymentOrder", source = "paymentOrder")
    InvoiceResponse toResponse(Invoice invoice);
}
