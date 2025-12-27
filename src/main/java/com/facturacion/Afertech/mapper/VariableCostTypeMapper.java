package com.facturacion.Afertech.mapper;

import com.facturacion.Afertech.dto.VariableCostTypeRequest;
import com.facturacion.Afertech.dto.VariableCostTypeResponse;
import com.facturacion.Afertech.model.VariableCostType;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface VariableCostTypeMapper {

    VariableCostType toEntity(VariableCostTypeRequest request);

    VariableCostTypeResponse toResponse(VariableCostType type);
}
