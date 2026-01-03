package com.facturacion.Afertech.service.impl;

import com.facturacion.Afertech.dto.PaymentOrderRequest;
import com.facturacion.Afertech.dto.PaymentOrderResponse;
import com.facturacion.Afertech.mapper.PaymentOrderMapper;
import com.facturacion.Afertech.model.*;
import com.facturacion.Afertech.repository.*;
import com.facturacion.Afertech.service.PaymentOrderService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class PaymentOrderServiceImpl implements PaymentOrderService {

    private final PaymentOrderRepository repository;
    private final ClientRepository clientRepository;
    private final ProjectRepository projectRepository;
    private final InvoiceRepository invoiceRepository;
    private final PurchaseOrderRepository purchaseOrderRepository;
    private final PaymentOrderMapper mapper;

    public PaymentOrderServiceImpl(
            PaymentOrderRepository repository,
            ClientRepository clientRepository,
            ProjectRepository projectRepository,
            InvoiceRepository invoiceRepository,
            PurchaseOrderRepository purchaseOrderRepository,
            PaymentOrderMapper mapper
    ) {
        this.repository = repository;
        this.clientRepository = clientRepository;
        this.projectRepository = projectRepository;
        this.invoiceRepository = invoiceRepository;
        this.purchaseOrderRepository = purchaseOrderRepository;
        this.mapper = mapper;
    }

    @Override
    public Page<PaymentOrderResponse> findAll(Pageable pageable) {
        return repository.findAll(pageable).map(mapper::toResponse);
    }

    @Override
    public PaymentOrderResponse findById(Long id) {
        PaymentOrder po = repository.findById(id)
                .orElseThrow(() -> new RuntimeException("Payment order not found"));
        return mapper.toResponse(po);
    }

    @Override
    public PaymentOrderResponse create(PaymentOrderRequest request) {

        Authentication auth = SecurityContextHolder.getContext().getAuthentication();

        Client client = clientRepository.findById(request.getClientId())
                .orElseThrow(() -> new RuntimeException("Client not found"));

        Project project = projectRepository.findById(request.getProjectId())
                .orElseThrow(() -> new RuntimeException("Project not found"));

        Invoice invoice = null;
        if (request.getInvoiceId() != null) {
            invoice = invoiceRepository.findById(request.getInvoiceId())
                    .orElseThrow(() -> new RuntimeException("Invoice not found"));
        }

        PurchaseOrder po = null;
        if (request.getPurchaseOrderId() != null) {
            po = purchaseOrderRepository.findById(request.getPurchaseOrderId())
                    .orElseThrow(() -> new RuntimeException("PurchaseOrder not found"));
        }

        PaymentOrder paymentOrder = mapper.toEntity(request);
        paymentOrder.setClient(client);
        paymentOrder.setProject(project);
        paymentOrder.setInvoice(invoice);
        paymentOrder.setPurchaseOrder(po);
        paymentOrder.setLoadedBy(auth.getName());

        return mapper.toResponse(repository.save(paymentOrder));
    }

    @Override
    public PaymentOrderResponse update(Long id, PaymentOrderRequest request) {

        PaymentOrder existing = repository.findById(id)
                .orElseThrow(() -> new RuntimeException("Payment order not found"));

        Client client = clientRepository.findById(request.getClientId())
                .orElseThrow(() -> new RuntimeException("Client not found"));

        Project project = projectRepository.findById(request.getProjectId())
                .orElseThrow(() -> new RuntimeException("Project not found"));

        Invoice invoice = null;
        if (request.getInvoiceId() != null) {
            invoice = invoiceRepository.findById(request.getInvoiceId())
                    .orElseThrow(() -> new RuntimeException("Invoice not found"));
        }

        PurchaseOrder po = null;
        if (request.getPurchaseOrderId() != null) {
            po = purchaseOrderRepository.findById(request.getPurchaseOrderId())
                    .orElseThrow(() -> new RuntimeException("PurchaseOrder not found"));
        }

        PaymentOrder updated = mapper.toEntity(request);
        updated.setId(existing.getId());
        updated.setClient(client);
        updated.setProject(project);
        updated.setInvoice(invoice);
        updated.setPurchaseOrder(po);

        return mapper.toResponse(repository.save(updated));
    }

    @Override
    public void delete(Long id) {

        PaymentOrder po = repository.findById(id)
                .orElseThrow(() -> new RuntimeException("Payment order not found"));

        Authentication auth = SecurityContextHolder.getContext().getAuthentication();

        po.setDeletedAt(LocalDateTime.now());
        po.setDeletedBy(auth.getName());

        repository.save(po);
    }
}
