package com.facturacion.Afertech.mapper;

import com.facturacion.Afertech.dto.VariableCostTypeRequest;
import com.facturacion.Afertech.dto.VariableCostTypeResponse;
import com.facturacion.Afertech.model.VariableCostType;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;

@Mapper(
        componentModel = "spring",
        unmappedTargetPolicy = ReportingPolicy.ERROR
)
public interface VariableCostTypeMapper {

    // Request → Entity
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "createdBy", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    @Mapping(target = "updatedBy", ignore = true)
    @Mapping(target = "deletedAt", ignore = true)
    @Mapping(target = "deletedBy", ignore = true)
    @Mapping(target = "loadedAt", ignore = true)
    @Mapping(target = "loadedBy", ignore = true)
    VariableCostType toEntity(VariableCostTypeRequest request);

    // Entity → Response
    @Mapping(target = "loadedAt", source = "loadedAt")
    @Mapping(target = "loadedBy", source = "loadedBy")
    VariableCostTypeResponse toResponse(VariableCostType type);
}
