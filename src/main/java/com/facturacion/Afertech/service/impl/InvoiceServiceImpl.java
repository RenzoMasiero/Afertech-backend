package com.facturacion.Afertech.service.impl;

import com.facturacion.Afertech.dto.InvoiceRequest;
import com.facturacion.Afertech.dto.InvoiceResponse;
import com.facturacion.Afertech.mapper.InvoiceMapper;
import com.facturacion.Afertech.model.*;
import com.facturacion.Afertech.repository.*;
import com.facturacion.Afertech.service.ExchangeRateService;
import com.facturacion.Afertech.service.InvoiceService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDateTime;

@Service
public class InvoiceServiceImpl implements InvoiceService {

    private final InvoiceRepository invoiceRepository;
    private final ClientRepository clientRepository;
    private final ProjectRepository projectRepository;
    private final PurchaseOrderRepository purchaseOrderRepository;
    private final PaymentOrderRepository paymentOrderRepository;
    private final ExchangeRateRepository exchangeRateRepository;
    private final ExchangeRateService exchangeRateService;
    private final InvoiceMapper mapper;

    public InvoiceServiceImpl(
            InvoiceRepository invoiceRepository,
            ClientRepository clientRepository,
            ProjectRepository projectRepository,
            PurchaseOrderRepository purchaseOrderRepository,
            PaymentOrderRepository paymentOrderRepository,
            ExchangeRateRepository exchangeRateRepository,
            ExchangeRateService exchangeRateService,
            InvoiceMapper mapper
    ) {
        this.invoiceRepository = invoiceRepository;
        this.clientRepository = clientRepository;
        this.projectRepository = projectRepository;
        this.purchaseOrderRepository = purchaseOrderRepository;
        this.paymentOrderRepository = paymentOrderRepository;
        this.exchangeRateRepository = exchangeRateRepository;
        this.exchangeRateService = exchangeRateService;
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

        invoice.setCurrencyOriginal(request.getCurrencyOriginal());

        applyMonetaryLogic(invoice);

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

        invoice.setCurrencyOriginal(request.getCurrencyOriginal());

        applyMonetaryLogic(invoice);

        return mapper.toResponse(invoiceRepository.save(invoice));
    }

    private void applyMonetaryLogic(Invoice invoice) {

        if (invoice.getCurrencyOriginal() == Currency.USD) {

            invoice.setExchangeRateUsed(BigDecimal.ONE);

            invoice.setTotalWithoutTaxUsd(
                    invoice.getTotalWithoutTax().setScale(2, RoundingMode.HALF_UP)
            );

            invoice.setTotalWithTaxUsd(
                    invoice.getTotalWithTax().setScale(2, RoundingMode.HALF_UP)
            );

        } else {

            ExchangeRate rate = exchangeRateService.getByDate(invoice.getIssueDate());

            BigDecimal exchangeRate = rate.getUsdArsRate();

            invoice.setExchangeRateUsed(exchangeRate);

            invoice.setTotalWithoutTaxUsd(
                    invoice.getTotalWithoutTax()
                            .divide(exchangeRate, 2, RoundingMode.HALF_UP)
            );

            invoice.setTotalWithTaxUsd(
                    invoice.getTotalWithTax()
                            .divide(exchangeRate, 2, RoundingMode.HALF_UP)
            );
        }
    }

    @Override
    public void delete(Long id) {

        Invoice invoice = invoiceRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Invoice not found"));

        if (paymentOrderRepository.existsByInvoiceIdAndDeletedAtIsNull(id)) {
            throw new IllegalStateException(
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

    @Override
    public int backfillMissingMonetaryData() {
        int updatedCount = 0;

        for (Invoice invoice : invoiceRepository.findAll()) {
            boolean missingMonetaryData =
                    invoice.getExchangeRateUsed() == null
                            || invoice.getTotalWithoutTaxUsd() == null
                            || invoice.getTotalWithTaxUsd() == null;

            if (!missingMonetaryData) {
                continue;
            }

            applyMonetaryLogicForBackfill(invoice);
            invoiceRepository.save(invoice);
            updatedCount++;
        }

        return updatedCount;
    }

    private void applyMonetaryLogicForBackfill(Invoice invoice) {
        try {
            applyMonetaryLogic(invoice);
            return;
        } catch (IllegalStateException ex) {
            // fallback para históricos fuera de tolerancia
        }

        if (invoice.getCurrencyOriginal() == Currency.USD) {
            invoice.setExchangeRateUsed(BigDecimal.ONE);
            invoice.setTotalWithoutTaxUsd(
                    invoice.getTotalWithoutTax().setScale(2, RoundingMode.HALF_UP)
            );
            invoice.setTotalWithTaxUsd(
                    invoice.getTotalWithTax().setScale(2, RoundingMode.HALF_UP)
            );
            return;
        }

        ExchangeRate fallbackRate = exchangeRateRepository
                .findTopByDateLessThanEqualAndDeletedAtIsNullOrderByDateDesc(invoice.getIssueDate())
                .orElseThrow(() ->
                        new RuntimeException("No exchange rate available before or equal to date: " + invoice.getIssueDate())
                );

        if (fallbackRate.getUsdArsRate() == null) {
            throw new IllegalStateException(
                    "Exchange rate is marked as unavailable for date: " + fallbackRate.getDate()
            );
        }

        BigDecimal exchangeRate = fallbackRate.getUsdArsRate();
        invoice.setExchangeRateUsed(exchangeRate);
        invoice.setTotalWithoutTaxUsd(
                invoice.getTotalWithoutTax()
                        .divide(exchangeRate, 2, RoundingMode.HALF_UP)
        );
        invoice.setTotalWithTaxUsd(
                invoice.getTotalWithTax()
                        .divide(exchangeRate, 2, RoundingMode.HALF_UP)
        );
    }
}
