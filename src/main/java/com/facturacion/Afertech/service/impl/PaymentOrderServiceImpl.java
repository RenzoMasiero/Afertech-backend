package com.facturacion.Afertech.service.impl;

import com.facturacion.Afertech.dto.PaymentOrderRequest;
import com.facturacion.Afertech.dto.PaymentOrderResponse;
import com.facturacion.Afertech.mapper.PaymentOrderMapper;
import com.facturacion.Afertech.model.PaymentOrder;
import com.facturacion.Afertech.repository.PaymentOrderRepository;
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
    private final PaymentOrderMapper mapper;

    public PaymentOrderServiceImpl(
            PaymentOrderRepository repository,
            PaymentOrderMapper mapper
    ) {
        this.repository = repository;
        this.mapper = mapper;
    }

    @Override
    public Page<PaymentOrderResponse> findAll(Pageable pageable) {
        return repository.findAll(pageable)
                .map(mapper::toResponse);
    }

    @Override
    public PaymentOrderResponse findById(Long id) {
        PaymentOrder paymentOrder = repository.findById(id)
                .orElseThrow(() -> new RuntimeException("Payment order not found"));
        return mapper.toResponse(paymentOrder);
    }

    @Override
    public PaymentOrderResponse create(PaymentOrderRequest request) {

        Authentication auth = SecurityContextHolder
                .getContext()
                .getAuthentication();

        PaymentOrder paymentOrder = mapper.toEntity(request);

        // dato funcional: quién cargó la orden de pago
        paymentOrder.setLoadedBy(auth.getName());

        return mapper.toResponse(repository.save(paymentOrder));
    }

    @Override
    public PaymentOrderResponse update(Long id, PaymentOrderRequest request) {
        PaymentOrder paymentOrder = repository.findById(id)
                .orElseThrow(() -> new RuntimeException("Payment order not found"));

        paymentOrder.setCompany(request.getCompany());
        paymentOrder.setPaymentOrderNumber(request.getPaymentOrderNumber());
        paymentOrder.setIssueDate(request.getIssueDate());
        paymentOrder.setProjectNumber(request.getProjectNumber());
        paymentOrder.setTotalWithoutTax(request.getTotalWithoutTax());
        paymentOrder.setTotalWithTax(request.getTotalWithTax());
        paymentOrder.setConcept(request.getConcept());
        paymentOrder.setInvoiceNumber(request.getInvoiceNumber());
        paymentOrder.setPurchaseOrderNumber(request.getPurchaseOrderNumber());
        paymentOrder.setWithholdings(request.getWithholdings());

        return mapper.toResponse(repository.save(paymentOrder));
    }

    @Override
    public void delete(Long id) {

        PaymentOrder paymentOrder = repository.findById(id)
                .orElseThrow(() -> new RuntimeException("Payment order not found"));

        Authentication auth = SecurityContextHolder
                .getContext()
                .getAuthentication();

        paymentOrder.setDeletedAt(LocalDateTime.now());
        paymentOrder.setDeletedBy(auth.getName());

        repository.save(paymentOrder);
    }
}
