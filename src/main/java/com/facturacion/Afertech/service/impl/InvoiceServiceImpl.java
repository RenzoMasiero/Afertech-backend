package com.facturacion.Afertech.service.impl;

import com.facturacion.Afertech.dto.InvoiceRequest;
import com.facturacion.Afertech.dto.InvoiceResponse;
import com.facturacion.Afertech.mapper.InvoiceMapper;
import com.facturacion.Afertech.model.*;
import com.facturacion.Afertech.repository.*;
import com.facturacion.Afertech.service.InvoiceService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class InvoiceServiceImpl implements InvoiceService {

    private final InvoiceRepository invoiceRepository;
    private final ClientRepository clientRepository;
    private final ProjectRepository projectRepository;
    private final PurchaseOrderRepository purchaseOrderRepository;
    private final PaymentOrderRepository paymentOrderRepository;
    private final InvoiceMapper mapper;

    public InvoiceServiceImpl(
            InvoiceRepository invoiceRepository,
            ClientRepository clientRepository,
            ProjectRepository projectRepository,
            PurchaseOrderRepository purchaseOrderRepository,
            PaymentOrderRepository paymentOrderRepository,
            InvoiceMapper mapper
    ) {
        this.invoiceRepository = invoiceRepository;
        this.clientRepository = clientRepository;
        this.projectRepository = projectRepository;
        this.purchaseOrderRepository = purchaseOrderRepository;
        this.paymentOrderRepository = paymentOrderRepository;
        this.mapper = mapper;
    }

    @Override
    public Page<InvoiceResponse> findAll(Pageable pageable) {
        return invoiceRepository.findAll(pageable).map(mapper::toResponse);
    }

    @Override
    public InvoiceResponse findById(Long id) {
        Invoice invoice = invoiceRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Invoice not found"));
        return mapper.toResponse(invoice);
    }

    @Override
    public InvoiceResponse create(InvoiceRequest request) {

        Authentication auth = SecurityContextHolder.getContext().getAuthentication();

        Client client = clientRepository.findById(request.getClientId())
                .orElseThrow(() -> new RuntimeException("Client not found"));

        Project project = projectRepository.findById(request.getProjectId())
                .orElseThrow(() -> new RuntimeException("Project not found"));

        PurchaseOrder po = purchaseOrderRepository.findById(request.getPurchaseOrderId())
                .orElseThrow(() -> new RuntimeException("PurchaseOrder not found"));

        PaymentOrder paymentOrder = null;
        if (request.getPaymentOrderId() != null) {
            paymentOrder = paymentOrderRepository.findById(request.getPaymentOrderId())
                    .orElseThrow(() -> new RuntimeException("PaymentOrder not found"));
        }

        Invoice invoice = mapper.toEntity(request);
        invoice.setClient(client);
        invoice.setProject(project);
        invoice.setPurchaseOrder(po);
        invoice.setPaymentOrder(paymentOrder);
        invoice.setLoadedBy(auth.getName());

        return mapper.toResponse(invoiceRepository.save(invoice));
    }

    @Override
    public InvoiceResponse update(Long id, InvoiceRequest request) {

        Invoice existing = invoiceRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Invoice not found"));

        Client client = clientRepository.findById(request.getClientId())
                .orElseThrow(() -> new RuntimeException("Client not found"));

        Project project = projectRepository.findById(request.getProjectId())
                .orElseThrow(() -> new RuntimeException("Project not found"));

        PurchaseOrder po = purchaseOrderRepository.findById(request.getPurchaseOrderId())
                .orElseThrow(() -> new RuntimeException("PurchaseOrder not found"));

        PaymentOrder paymentOrder = null;
        if (request.getPaymentOrderId() != null) {
            paymentOrder = paymentOrderRepository.findById(request.getPaymentOrderId())
                    .orElseThrow(() -> new RuntimeException("PaymentOrder not found"));
        }

        Invoice updated = mapper.toEntity(request);
        updated.setId(existing.getId());
        updated.setClient(client);
        updated.setProject(project);
        updated.setPurchaseOrder(po);
        updated.setPaymentOrder(paymentOrder);

        return mapper.toResponse(invoiceRepository.save(updated));
    }

    @Override
    public void delete(Long id) {

        Invoice invoice = invoiceRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Invoice not found"));

        Authentication auth = SecurityContextHolder.getContext().getAuthentication();

        invoice.setDeletedAt(LocalDateTime.now());
        invoice.setDeletedBy(auth.getName());

        invoiceRepository.save(invoice);
    }
}
