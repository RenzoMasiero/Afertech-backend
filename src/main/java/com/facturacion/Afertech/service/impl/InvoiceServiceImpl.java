package com.facturacion.Afertech.service.impl;

import com.facturacion.Afertech.dto.InvoiceRequest;
import com.facturacion.Afertech.dto.InvoiceResponse;
import com.facturacion.Afertech.mapper.InvoiceMapper;
import com.facturacion.Afertech.model.*;
import com.facturacion.Afertech.repository.*;
import com.facturacion.Afertech.service.InvoiceService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
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
        return invoiceRepository.findAll(pageable)
                .map(mapper::toResponse);
    }

    @Override
    public InvoiceResponse findById(Long id) {
        Invoice invoice = invoiceRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Invoice not found"));
        return mapper.toResponse(invoice);
    }

    @Override
    public InvoiceResponse create(InvoiceRequest request) {

        Invoice invoice = new Invoice();

        invoice.setClient(
                clientRepository.findById(request.getClientId())
                        .orElseThrow(() -> new RuntimeException("Client not found"))
        );
        invoice.setProject(
                projectRepository.findById(request.getProjectId())
                        .orElseThrow(() -> new RuntimeException("Project not found"))
        );
        invoice.setPurchaseOrder(
                purchaseOrderRepository.findById(request.getPurchaseOrderId())
                        .orElseThrow(() -> new RuntimeException("PurchaseOrder not found"))
        );

        if (request.getPaymentOrderId() != null) {
            invoice.setPaymentOrder(
                    paymentOrderRepository.findById(request.getPaymentOrderId())
                            .orElseThrow(() -> new RuntimeException("PaymentOrder not found"))
            );
        }

        invoice.setInvoiceNumber(request.getInvoiceNumber());
        invoice.setIssueDate(request.getIssueDate());
        invoice.setDescription(request.getDescription());
        invoice.setTotalWithoutTax(request.getTotalWithoutTax());
        invoice.setTotalWithTax(request.getTotalWithTax());
        invoice.setDeferredPaymentDays(request.getDeferredPaymentDays());
        invoice.setPurchaseOrderPercentage(request.getPurchaseOrderPercentage());

        invoice.setLoadedBy(
                SecurityContextHolder.getContext()
                        .getAuthentication()
                        .getName()
        );

        return mapper.toResponse(invoiceRepository.save(invoice));
    }

    @Override
    public InvoiceResponse update(Long id, InvoiceRequest request) {

        Invoice invoice = invoiceRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Invoice not found"));

        invoice.setClient(
                clientRepository.findById(request.getClientId())
                        .orElseThrow(() -> new RuntimeException("Client not found"))
        );
        invoice.setProject(
                projectRepository.findById(request.getProjectId())
                        .orElseThrow(() -> new RuntimeException("Project not found"))
        );
        invoice.setPurchaseOrder(
                purchaseOrderRepository.findById(request.getPurchaseOrderId())
                        .orElseThrow(() -> new RuntimeException("PurchaseOrder not found"))
        );

        if (request.getPaymentOrderId() != null) {
            invoice.setPaymentOrder(
                    paymentOrderRepository.findById(request.getPaymentOrderId())
                            .orElseThrow(() -> new RuntimeException("PaymentOrder not found"))
            );
        } else {
            invoice.setPaymentOrder(null);
        }

        invoice.setInvoiceNumber(request.getInvoiceNumber());
        invoice.setIssueDate(request.getIssueDate());
        invoice.setDescription(request.getDescription());
        invoice.setTotalWithoutTax(request.getTotalWithoutTax());
        invoice.setTotalWithTax(request.getTotalWithTax());
        invoice.setDeferredPaymentDays(request.getDeferredPaymentDays());
        invoice.setPurchaseOrderPercentage(request.getPurchaseOrderPercentage());

        return mapper.toResponse(invoiceRepository.save(invoice));
    }

    @Override
    public void delete(Long id) {

        Invoice invoice = invoiceRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Invoice not found"));

        // 🔒 Regla de dependencia
        if (paymentOrderRepository.existsByInvoiceIdAndDeletedAtIsNull(id)) {
            throw new RuntimeException(
                    "Cannot delete invoice with existing payment order"
            );
        }

        invoice.setDeletedAt(LocalDateTime.now());
        invoice.setDeletedBy(
                SecurityContextHolder.getContext()
                        .getAuthentication()
                        .getName()
        );

        invoiceRepository.save(invoice);
    }
}
