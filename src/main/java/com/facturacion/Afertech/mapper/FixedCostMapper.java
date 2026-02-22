package com.facturacion.Afertech.mapper;

import com.facturacion.Afertech.dto.FixedCostResponse;
import com.facturacion.Afertech.model.FixedCost;
import org.mapstruct.BeanMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;

@Mapper(
        componentModel = "spring",
        unmappedTargetPolicy = ReportingPolicy.ERROR
)
public interface FixedCostMapper {

    @BeanMapping(ignoreByDefault = true)

    // Identidad
    @Mapping(source = "id", target = "id")

    // CostType
    @Mapping(source = "costType.id", target = "costTypeId")
    @Mapping(source = "costType.name", target = "costTypeName")

    // Employee
    @Mapping(source = "employee.id", target = "employeeId")
    @Mapping(
            expression = "java(fixedCost.getEmployee() != null ? fixedCost.getEmployee().getFirstName() + \" \" + fixedCost.getEmployee().getLastName() : null)",
            target = "employeeName"
    )

    // Monto original
    @Mapping(source = "amount", target = "amount")

    // 💵 MODELO MONETARIO (OBLIGATORIO)
    @Mapping(source = "currencyOriginal", target = "currencyOriginal")
    @Mapping(source = "exchangeRateUsed", target = "exchangeRateUsed")
    @Mapping(source = "amountUsd", target = "amountUsd")

    // Dominio funcional
    @Mapping(source = "allocationMonth", target = "allocationMonth")
    @Mapping(source = "paymentDate", target = "paymentDate")
    @Mapping(source = "description", target = "description")

    // Datos funcionales
    @Mapping(source = "loadedAt", target = "loadedAt")
    @Mapping(source = "loadedBy", target = "loadedBy")

    FixedCostResponse toResponse(FixedCost fixedCost);
}