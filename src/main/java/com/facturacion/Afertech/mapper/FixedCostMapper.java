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
    @Mapping(source = "id", target = "id")
    @Mapping(source = "costType.id", target = "costTypeId")
    @Mapping(source = "costType.name", target = "costTypeName")
    @Mapping(source = "amount", target = "amount")
    @Mapping(source = "allocationMonth", target = "allocationMonth")
    @Mapping(source = "paymentDate", target = "paymentDate")
    @Mapping(source = "description", target = "description")
    FixedCostResponse toResponse(FixedCost fixedCost);
}
