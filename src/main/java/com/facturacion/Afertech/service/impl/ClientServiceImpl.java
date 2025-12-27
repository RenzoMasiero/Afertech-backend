package com.facturacion.Afertech.service.impl;

import com.facturacion.Afertech.dto.ClientRequest;
import com.facturacion.Afertech.dto.ClientResponse;
import com.facturacion.Afertech.mapper.ClientMapper;
import com.facturacion.Afertech.model.Client;
import com.facturacion.Afertech.repository.ClientRepository;
import com.facturacion.Afertech.service.ClientService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ClientServiceImpl implements ClientService {

    private final ClientRepository repository;
    private final ClientMapper mapper;

    public ClientServiceImpl(ClientRepository repository, ClientMapper mapper) {
        this.repository = repository;
        this.mapper = mapper;
    }

    @Override
    public List<ClientResponse> findAll() {
        return repository.findAll()
                .stream()
                .map(mapper::toResponse)
                .toList();
    }

    @Override
    public ClientResponse findById(Long id) {
        Client client = repository.findById(id)
                .orElseThrow(() -> new RuntimeException("Client not found"));
        return mapper.toResponse(client);
    }

    @Override
    public ClientResponse create(ClientRequest request) {

        if (repository.existsByTaxId(request.getTaxId())) {
            throw new RuntimeException("Client with this taxId already exists");
        }

        Client client = mapper.toEntity(request);
        return mapper.toResponse(repository.save(client));
    }

    @Override
    public ClientResponse update(Long id, ClientRequest request) {

        Client client = repository.findById(id)
                .orElseThrow(() -> new RuntimeException("Client not found"));

        if (!client.getTaxId().equals(request.getTaxId())
                && repository.existsByTaxId(request.getTaxId())) {
            throw new RuntimeException("Client with this taxId already exists");
        }

        client.setName(request.getName());
        client.setTaxId(request.getTaxId());
        client.setActive(request.getActive());

        return mapper.toResponse(repository.save(client));
    }

    @Override
    public void deactivate(Long id) {
        Client client = repository.findById(id)
                .orElseThrow(() -> new RuntimeException("Client not found"));

        client.setActive(false);
        repository.save(client);
    }
}
