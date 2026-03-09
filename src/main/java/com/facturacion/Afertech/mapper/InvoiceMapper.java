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
    @Mapping(target = "client", ignore = true)
    @Mapping(target = "project", ignore = true)
    @Mapping(target = "purchaseOrder", ignore = true)
    @Mapping(target = "paymentOrder", ignore = true)

    @Mapping(target = "exchangeRateUsed", ignore = true)
    @Mapping(target = "totalWithoutTaxUsd", ignore = true)
    @Mapping(target = "totalWithTaxUsd", ignore = true)

    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "createdBy", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    @Mapping(target = "updatedBy", ignore = true)
    @Mapping(target = "deletedAt", ignore = true)
    @Mapping(target = "deletedBy", ignore = true)

    @Mapping(target = "loadedAt", ignore = true)
    @Mapping(target = "loadedBy", ignore = true)
    Invoice toEntity(InvoiceRequest request);

    @Mapping(target = "clientId", source = "client.id")
    @Mapping(target = "clientName", source = "client.name")

    @Mapping(target = "projectId", source = "project.id")
    @Mapping(target = "projectName", source = "project.name")

    @Mapping(target = "purchaseOrderId", source = "purchaseOrder.id")
    @Mapping(target = "purchaseOrderNumber", source = "purchaseOrder.purchaseOrderNumber")

    @Mapping(target = "paymentOrderId", source = "paymentOrder.id")
    @Mapping(target = "paymentOrderNumber", source = "paymentOrder.paymentOrderNumber")

    @Mapping(target = "currencyOriginal", source = "currencyOriginal")
    @Mapping(target = "exchangeRateUsed", source = "exchangeRateUsed")
    @Mapping(target = "totalWithoutTaxUsd", source = "totalWithoutTaxUsd")
    @Mapping(target = "totalWithTaxUsd", source = "totalWithTaxUsd")
    InvoiceResponse toResponse(Invoice invoice);
}