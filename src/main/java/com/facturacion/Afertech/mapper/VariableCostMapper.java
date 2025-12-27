package com.facturacion.Afertech.mapper;

import com.facturacion.Afertech.dto.VariableCostResponse;
import com.facturacion.Afertech.model.VariableCost;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface VariableCostMapper {

    @Mapping(source = "costType.id", target = "costTypeId")
    @Mapping(source = "costType.name", target = "costTypeName")
    VariableCostResponse toResponse(VariableCost variableCost);
}
