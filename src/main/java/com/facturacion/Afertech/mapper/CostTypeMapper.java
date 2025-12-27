package com.facturacion.Afertech.mapper;

import com.facturacion.Afertech.dto.CostTypeRequest;
import com.facturacion.Afertech.dto.CostTypeResponse;
import com.facturacion.Afertech.model.CostType;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface CostTypeMapper {

    CostType toEntity(CostTypeRequest request);

    CostTypeResponse toResponse(CostType costType);
}
