package com.facturacion.Afertech.service.impl;

import com.facturacion.Afertech.dto.PaymentOrderRequest;
import com.facturacion.Afertech.dto.PaymentOrderResponse;
import com.facturacion.Afertech.mapper.PaymentOrderMapper;
import com.facturacion.Afertech.model.PaymentOrder;
import com.facturacion.Afertech.repository.PaymentOrderRepository;
import com.facturacion.Afertech.service.PaymentOrderService;
import org.springframework.stereotype.Service;

import java.util.List;

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
    public List<PaymentOrderResponse> findAll() {
        return repository.findAll()
                .stream()
                .map(mapper::toResponse)
                .toList();
    }

    @Override
    public PaymentOrderResponse findById(Long id) {
        PaymentOrder paymentOrder = repository.findById(id)
                .orElseThrow(() -> new RuntimeException("Payment order not found"));
        return mapper.toResponse(paymentOrder);
    }

    @Override
    public PaymentOrderResponse create(PaymentOrderRequest request) {
        PaymentOrder paymentOrder = mapper.toEntity(request);
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
        repository.delete(paymentOrder);
    }
}
