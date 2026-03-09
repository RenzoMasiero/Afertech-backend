package com.facturacion.Afertech.mapper;

import com.facturacion.Afertech.dto.PaymentOrderRequest;
import com.facturacion.Afertech.dto.PaymentOrderResponse;
import com.facturacion.Afertech.model.PaymentOrder;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;

@Mapper(
        componentModel = "spring",
        unmappedTargetPolicy = ReportingPolicy.ERROR
)
public interface PaymentOrderMapper {

    // ==========================
    // Request → Entity
    // ==========================
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "client", ignore = true)
    @Mapping(target = "project", ignore = true)
    @Mapping(target = "invoice", ignore = true)
    @Mapping(target = "purchaseOrder", ignore = true)

    // 💵 campos calculados por Service
    @Mapping(target = "exchangeRateUsed", ignore = true)
    @Mapping(target = "totalWithoutTaxUsd", ignore = true)
    @Mapping(target = "totalWithTaxUsd", ignore = true)

    // auditoría técnica
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "createdBy", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    @Mapping(target = "updatedBy", ignore = true)
    @Mapping(target = "deletedAt", ignore = true)
    @Mapping(target = "deletedBy", ignore = true)

    // datos funcionales
    @Mapping(target = "loadedAt", ignore = true)
    @Mapping(target = "loadedBy", ignore = true)

    PaymentOrder toEntity(PaymentOrderRequest request);

    // ==========================
    // Entity → Response
    // ==========================
    @Mapping(target = "clientId", source = "client.id")
    @Mapping(target = "clientName", source = "client.name")

    @Mapping(target = "projectId", source = "project.id")
    @Mapping(target = "projectName", source = "project.name")

    @Mapping(target = "invoiceId", source = "invoice.id")
    @Mapping(target = "invoiceNumber", source = "invoice.invoiceNumber")

    @Mapping(target = "purchaseOrderId", source = "purchaseOrder.id")
    @Mapping(target = "purchaseOrderNumber", source = "purchaseOrder.purchaseOrderNumber")

    // estado de ejecución
    @Mapping(target = "executed", source = "executed")
    @Mapping(target = "executionDate", source = "executionDate")

    // 💵 modelo monetario
    @Mapping(target = "currencyOriginal", source = "currencyOriginal")
    @Mapping(target = "exchangeRateUsed", source = "exchangeRateUsed")
    @Mapping(target = "totalWithoutTaxUsd", source = "totalWithoutTaxUsd")
    @Mapping(target = "totalWithTaxUsd", source = "totalWithTaxUsd")

    PaymentOrderResponse toResponse(PaymentOrder paymentOrder);
}