package com.facturacion.Afertech.mapper;

import com.facturacion.Afertech.dto.ClientRequest;
import com.facturacion.Afertech.dto.ClientResponse;
import com.facturacion.Afertech.model.Client;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;

@Mapper(
        componentModel = "spring",
        unmappedTargetPolicy = ReportingPolicy.ERROR
)
public interface ClientMapper {

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
    Client toEntity(ClientRequest request);

    // Entity → Response
    @Mapping(target = "loadedAt", source = "loadedAt")
    @Mapping(target = "loadedBy", source = "loadedBy")
    ClientResponse toResponse(Client client);
}
