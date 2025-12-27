package com.facturacion.Afertech.mapper;

import com.facturacion.Afertech.dto.FixedCostResponse;
import com.facturacion.Afertech.model.FixedCost;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface FixedCostMapper {

    @Mapping(source = "costType.id", target = "costTypeId")
    @Mapping(source = "costType.name", target = "costTypeName")
    FixedCostResponse toResponse(FixedCost fixedCost);
}
