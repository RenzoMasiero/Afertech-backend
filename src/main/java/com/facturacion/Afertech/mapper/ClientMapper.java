package com.facturacion.Afertech.mapper;

import com.facturacion.Afertech.dto.ClientRequest;
import com.facturacion.Afertech.dto.ClientResponse;
import com.facturacion.Afertech.model.Client;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface ClientMapper {

    Client toEntity(ClientRequest request);

    ClientResponse toResponse(Client client);
}
