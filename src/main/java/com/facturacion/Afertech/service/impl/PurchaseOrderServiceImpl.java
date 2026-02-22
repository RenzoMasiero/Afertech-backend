package com.facturacion.Afertech.service.impl;

import com.facturacion.Afertech.dto.PurchaseOrderRequest;
import com.facturacion.Afertech.dto.PurchaseOrderResponse;
import com.facturacion.Afertech.mapper.PurchaseOrderMapper;
import com.facturacion.Afertech.model.*;
import com.facturacion.Afertech.repository.ClientRepository;
import com.facturacion.Afertech.repository.InvoiceRepository;
import com.facturacion.Afertech.repository.ProjectRepository;
import com.facturacion.Afertech.repository.PurchaseOrderRepository;
import com.facturacion.Afertech.service.ExchangeRateService;
import com.facturacion.Afertech.service.PurchaseOrderService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDateTime;

@Service
public class PurchaseOrderServiceImpl implements PurchaseOrderService {

    private final PurchaseOrderRepository repository;
    private final ClientRepository clientRepository;
    private final ProjectRepository projectRepository;
    private final InvoiceRepository invoiceRepository;
    private final ExchangeRateService exchangeRateService;
    private final PurchaseOrderMapper mapper;

    public PurchaseOrderServiceImpl(
            PurchaseOrderRepository repository,
            ClientRepository clientRepository,
            ProjectRepository projectRepository,
            InvoiceRepository invoiceRepository,
            ExchangeRateService exchangeRateService,
            PurchaseOrderMapper mapper
    ) {
        this.repository = repository;
        this.clientRepository = clientRepository;
        this.projectRepository = projectRepository;
        this.invoiceRepository = invoiceRepository;
        this.exchangeRateService = exchangeRateService;
        this.mapper = mapper;
    }

    @Override
    public Page<PurchaseOrderResponse> findAll(Pageable pageable) {
        return repository.findAll(pageable)
                .map(mapper::toResponse);
    }

    @Override
    public PurchaseOrderResponse findById(Long id) {
        PurchaseOrder po = repository.findById(id)
                .orElseThrow(() -> new RuntimeException("Purchase order not found"));
        return mapper.toResponse(po);
    }

    @Override
    public PurchaseOrderResponse create(PurchaseOrderRequest request) {

        Client client = clientRepository.findById(request.getClientId())
                .orElseThrow(() -> new RuntimeException("Client not found"));

        Project project = projectRepository.findById(request.getProjectId())
                .orElseThrow(() -> new RuntimeException("Project not found"));

        PurchaseOrder po = mapper.toEntity(request);

        po.setClient(client);
        po.setProject(project);
        po.setCurrencyOriginal(request.getCurrencyOriginal());

        applyMonetaryLogic(po);

        po.setLoadedBy(
                SecurityContextHolder.getContext()
                        .getAuthentication()
                        .getName()
        );

        return mapper.toResponse(repository.save(po));
    }

    @Override
    public PurchaseOrderResponse update(Long id, PurchaseOrderRequest request) {

        PurchaseOrder po = repository.findById(id)
                .orElseThrow(() -> new RuntimeException("Purchase order not found"));

        Client client = clientRepository.findById(request.getClientId())
                .orElseThrow(() -> new RuntimeException("Client not found"));

        Project project = projectRepository.findById(request.getProjectId())
                .orElseThrow(() -> new RuntimeException("Project not found"));

        po.setClient(client);
        po.setProject(project);
        po.setPurchaseOrderNumber(request.getPurchaseOrderNumber());
        po.setIssueDate(request.getIssueDate());
        po.setTotalWithoutTax(request.getTotalWithoutTax());
        po.setTotalWithTax(request.getTotalWithTax());
        po.setDescription(request.getDescription());
        po.setCurrencyOriginal(request.getCurrencyOriginal());

        applyMonetaryLogic(po);

        return mapper.toResponse(repository.save(po));
    }

    @Override
    public void delete(Long id) {

        PurchaseOrder po = repository.findById(id)
                .orElseThrow(() -> new RuntimeException("Purchase order not found"));

        if (invoiceRepository.existsByPurchaseOrderIdAndDeletedAtIsNull(id)) {
            throw new IllegalStateException(
                    "Cannot delete purchase order with existing invoices"
            );
        }

        po.setDeletedAt(LocalDateTime.now());
        po.setDeletedBy(
                SecurityContextHolder.getContext()
                        .getAuthentication()
                        .getName()
        );

        repository.save(po);
    }

    private void applyMonetaryLogic(PurchaseOrder po) {

        if (po.getCurrencyOriginal() == Currency.USD) {

            po.setExchangeRateUsed(BigDecimal.ONE);

            po.setTotalWithoutTaxUsd(
                    po.getTotalWithoutTax().setScale(2, RoundingMode.HALF_UP)
            );

            po.setTotalWithTaxUsd(
                    po.getTotalWithTax().setScale(2, RoundingMode.HALF_UP)
            );

        } else {

            ExchangeRate rate = exchangeRateService.getByDate(po.getIssueDate());

            BigDecimal exchangeRate = rate.getUsdArsRate();

            po.setExchangeRateUsed(exchangeRate);

            po.setTotalWithoutTaxUsd(
                    po.getTotalWithoutTax()
                            .divide(exchangeRate, 2, RoundingMode.HALF_UP)
            );

            po.setTotalWithTaxUsd(
                    po.getTotalWithTax()
                            .divide(exchangeRate, 2, RoundingMode.HALF_UP)
            );
        }
    }
}