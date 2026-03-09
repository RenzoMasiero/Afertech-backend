package com.facturacion.Afertech.mapper;

import com.facturacion.Afertech.dto.VariableCostResponse;
import com.facturacion.Afertech.model.VariableCost;
import org.mapstruct.BeanMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;

@Mapper(
        componentModel = "spring",
        unmappedTargetPolicy = ReportingPolicy.ERROR
)
public interface VariableCostMapper {

    @BeanMapping(ignoreByDefault = true)

    @Mapping(source = "id", target = "id")

    @Mapping(source = "costType.id", target = "costTypeId")
    @Mapping(source = "costType.name", target = "costTypeName")

    @Mapping(source = "amount", target = "amount")

    // 💵 MODELO MONETARIO
    @Mapping(source = "currencyOriginal", target = "currencyOriginal")
    @Mapping(source = "exchangeRateUsed", target = "exchangeRateUsed")
    @Mapping(source = "amountUsd", target = "amountUsd")

    @Mapping(source = "allocationMonth", target = "allocationMonth")
    @Mapping(source = "paymentDate", target = "paymentDate")

    @Mapping(source = "supplier.id", target = "supplierId")
    @Mapping(source = "supplier.name", target = "supplierName")

    @Mapping(source = "project.id", target = "projectId")
    @Mapping(source = "project.name", target = "projectName")

    @Mapping(source = "description", target = "description")

    @Mapping(source = "loadedAt", target = "loadedAt")
    @Mapping(source = "loadedBy", target = "loadedBy")

    VariableCostResponse toResponse(VariableCost variableCost);
}