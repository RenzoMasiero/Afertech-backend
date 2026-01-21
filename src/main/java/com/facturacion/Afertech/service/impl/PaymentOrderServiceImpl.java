package com.facturacion.Afertech.service.impl;

import com.facturacion.Afertech.dto.PaymentOrderRequest;
import com.facturacion.Afertech.dto.PaymentOrderResponse;
import com.facturacion.Afertech.mapper.PaymentOrderMapper;
import com.facturacion.Afertech.model.*;
import com.facturacion.Afertech.repository.*;
import com.facturacion.Afertech.service.PaymentOrderService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
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

        PaymentOrder po = new PaymentOrder();

        po.setClient(
                clientRepository.findById(request.getClientId())
                        .orElseThrow(() -> new RuntimeException("Client not found"))
        );
        po.setProject(
                projectRepository.findById(request.getProjectId())
                        .orElseThrow(() -> new RuntimeException("Project not found"))
        );

        if (request.getInvoiceId() != null) {
            po.setInvoice(
                    invoiceRepository.findById(request.getInvoiceId())
                            .orElseThrow(() -> new RuntimeException("Invoice not found"))
            );
        }

        if (request.getPurchaseOrderId() != null) {
            po.setPurchaseOrder(
                    purchaseOrderRepository.findById(request.getPurchaseOrderId())
                            .orElseThrow(() -> new RuntimeException("PurchaseOrder not found"))
            );
        }

        // ==========================
        // Regla executed / executionDate
        // ==========================
        applyExecutionRule(po, request);

        po.setPaymentOrderNumber(request.getPaymentOrderNumber());
        po.setIssueDate(request.getIssueDate());
        po.setTotalWithoutTax(request.getTotalWithoutTax());
        po.setTotalWithTax(request.getTotalWithTax());
        po.setConcept(request.getConcept());
        po.setWithholdings(request.getWithholdings());

        po.setLoadedBy(
                SecurityContextHolder.getContext()
                        .getAuthentication()
                        .getName()
        );

        return mapper.toResponse(repository.save(po));
    }

    @Override
    public PaymentOrderResponse update(Long id, PaymentOrderRequest request) {

        PaymentOrder po = repository.findById(id)
                .orElseThrow(() -> new RuntimeException("Payment order not found"));

        po.setClient(
                clientRepository.findById(request.getClientId())
                        .orElseThrow(() -> new RuntimeException("Client not found"))
        );
        po.setProject(
                projectRepository.findById(request.getProjectId())
                        .orElseThrow(() -> new RuntimeException("Project not found"))
        );

        if (request.getInvoiceId() != null) {
            po.setInvoice(
                    invoiceRepository.findById(request.getInvoiceId())
                            .orElseThrow(() -> new RuntimeException("Invoice not found"))
            );
        } else {
            po.setInvoice(null);
        }

        if (request.getPurchaseOrderId() != null) {
            po.setPurchaseOrder(
                    purchaseOrderRepository.findById(request.getPurchaseOrderId())
                            .orElseThrow(() -> new RuntimeException("PurchaseOrder not found"))
            );
        } else {
            po.setPurchaseOrder(null);
        }

        // ==========================
        // Regla executed / executionDate
        // ==========================
        applyExecutionRule(po, request);

        po.setPaymentOrderNumber(request.getPaymentOrderNumber());
        po.setIssueDate(request.getIssueDate());
        po.setTotalWithoutTax(request.getTotalWithoutTax());
        po.setTotalWithTax(request.getTotalWithTax());
        po.setConcept(request.getConcept());
        po.setWithholdings(request.getWithholdings());

        return mapper.toResponse(repository.save(po));
    }

    @Override
    public void delete(Long id) {

        PaymentOrder po = repository.findById(id)
                .orElseThrow(() -> new RuntimeException("Payment order not found"));

        // 🔒 Regla de negocio
        if (po.getInvoice() != null) {
            throw new IllegalStateException(
                    "Cannot delete payment order linked to an invoice"
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

    // ==========================
    // Regla de dominio centralizada
    // ==========================
    private void applyExecutionRule(PaymentOrder po, PaymentOrderRequest request) {

        if (request.getExecuted() == null) {
            throw new RuntimeException("Executed flag is required");
        }

        if (request.getExecuted()) {
            if (request.getExecutionDate() == null) {
                throw new RuntimeException(
                        "Execution date is required when payment order is executed"
                );
            }
            po.setExecuted(true);
            po.setExecutionDate(request.getExecutionDate());
        } else {
            po.setExecuted(false);
            po.setExecutionDate(null);
        }
    }
}
