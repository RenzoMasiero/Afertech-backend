package com.facturacion.Afertech.service;

import com.facturacion.Afertech.dto.ClientRequest;
import com.facturacion.Afertech.dto.ClientResponse;

import java.util.List;

public interface ClientService {

    List<ClientResponse> findAll();

    ClientResponse findById(Long id);

    ClientResponse create(ClientRequest request);

    ClientResponse update(Long id, ClientRequest request);

    void deactivate(Long id);
}
