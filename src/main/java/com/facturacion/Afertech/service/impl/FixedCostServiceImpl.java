package com.facturacion.Afertech.service.impl;

import com.facturacion.Afertech.dto.FixedCostRequest;
import com.facturacion.Afertech.dto.FixedCostResponse;
import com.facturacion.Afertech.mapper.FixedCostMapper;
import com.facturacion.Afertech.model.*;
import com.facturacion.Afertech.repository.CostTypeRepository;
import com.facturacion.Afertech.repository.EmployeeRepository;
import com.facturacion.Afertech.repository.FixedCostRepository;
import com.facturacion.Afertech.service.ExchangeRateService;
import com.facturacion.Afertech.service.FixedCostService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDateTime;
import java.time.YearMonth;
import java.time.format.DateTimeParseException;

@Service
public class FixedCostServiceImpl implements FixedCostService {

    private final FixedCostRepository repository;
    private final CostTypeRepository costTypeRepository;
    private final EmployeeRepository employeeRepository;
    private final ExchangeRateService exchangeRateService;
    private final FixedCostMapper mapper;

    public FixedCostServiceImpl(
            FixedCostRepository repository,
            CostTypeRepository costTypeRepository,
            EmployeeRepository employeeRepository,
            ExchangeRateService exchangeRateService,
            FixedCostMapper mapper
    ) {
        this.repository = repository;
        this.costTypeRepository = costTypeRepository;
        this.employeeRepository = employeeRepository;
        this.exchangeRateService = exchangeRateService;
        this.mapper = mapper;
    }

    @Override
    public Page<FixedCostResponse> findAll(Pageable pageable) {
        return repository.findAll(pageable)
                .map(mapper::toResponse);
    }

    @Override
    public FixedCostResponse findById(Long id) {
        FixedCost fixedCost = repository.findById(id)
                .orElseThrow(() -> new RuntimeException("Fixed cost not found"));
        return mapper.toResponse(fixedCost);
    }

    @Override
    public FixedCostResponse create(FixedCostRequest request) {

        FixedCost fixedCost = new FixedCost();

        CostType costType = costTypeRepository.findById(request.getCostTypeId())
                .orElseThrow(() -> new RuntimeException("Cost type not found"));

        validateAllocationMonth(request.getAllocationMonth());

        fixedCost.setCostType(costType);
        fixedCost.setAmount(request.getAmount());
        fixedCost.setCurrencyOriginal(request.getCurrencyOriginal());
        fixedCost.setAllocationMonth(request.getAllocationMonth());
        fixedCost.setPaymentDate(request.getPaymentDate());
        fixedCost.setDescription(request.getDescription());

        // 🔒 Regla SUELDO
        if ("SALARY".equalsIgnoreCase(costType.getName())
                || "SUELDO".equalsIgnoreCase(costType.getName())) {

            if (request.getEmployeeId() == null) {
                throw new RuntimeException("Employee is required for salary fixed cost");
            }

            Employee employee = employeeRepository.findById(request.getEmployeeId())
                    .orElseThrow(() -> new RuntimeException("Employee not found"));

            fixedCost.setEmployee(employee);
        }

        applyMonetaryLogic(fixedCost);

        fixedCost.setLoadedBy(
                SecurityContextHolder.getContext()
                        .getAuthentication()
                        .getName()
        );

        return mapper.toResponse(repository.save(fixedCost));
    }

    @Override
    public FixedCostResponse update(Long id, FixedCostRequest request) {

        FixedCost fixedCost = repository.findById(id)
                .orElseThrow(() -> new RuntimeException("Fixed cost not found"));

        CostType newCostType = costTypeRepository.findById(request.getCostTypeId())
                .orElseThrow(() -> new RuntimeException("Cost type not found"));

        boolean wasSalary =
                fixedCost.getCostType() != null &&
                        ("SALARY".equalsIgnoreCase(fixedCost.getCostType().getName())
                                || "SUELDO".equalsIgnoreCase(fixedCost.getCostType().getName()));

        boolean isSalary =
                "SALARY".equalsIgnoreCase(newCostType.getName())
                        || "SUELDO".equalsIgnoreCase(newCostType.getName());

        if (wasSalary && !isSalary) {
            throw new RuntimeException(
                    "Cannot change cost type from SALARY to a non-salary type"
            );
        }

        validateAllocationMonth(request.getAllocationMonth());

        fixedCost.setCostType(newCostType);
        fixedCost.setAmount(request.getAmount());
        fixedCost.setCurrencyOriginal(request.getCurrencyOriginal());
        fixedCost.setAllocationMonth(request.getAllocationMonth());
        fixedCost.setPaymentDate(request.getPaymentDate());
        fixedCost.setDescription(request.getDescription());

        if (isSalary) {
            if (request.getEmployeeId() == null) {
                throw new RuntimeException("Employee is required for salary fixed cost");
            }

            Employee employee = employeeRepository.findById(request.getEmployeeId())
                    .orElseThrow(() -> new RuntimeException("Employee not found"));

            fixedCost.setEmployee(employee);
        }

        applyMonetaryLogic(fixedCost);

        return mapper.toResponse(repository.save(fixedCost));
    }

    @Override
    public void delete(Long id) {

        FixedCost fixedCost = repository.findById(id)
                .orElseThrow(() -> new RuntimeException("Fixed cost not found"));

        fixedCost.setDeletedAt(LocalDateTime.now());
        fixedCost.setDeletedBy(
                SecurityContextHolder.getContext()
                        .getAuthentication()
                        .getName()
        );

        repository.save(fixedCost);
    }

    private void applyMonetaryLogic(FixedCost fixedCost) {

        if (fixedCost.getCurrencyOriginal() == Currency.USD) {

            fixedCost.setExchangeRateUsed(BigDecimal.ONE);

            fixedCost.setAmountUsd(
                    fixedCost.getAmount()
                            .setScale(2, RoundingMode.HALF_UP)
            );

        } else {

            ExchangeRate rate =
                    exchangeRateService.getByDate(fixedCost.getPaymentDate());

            BigDecimal exchangeRate = rate.getUsdArsRate();

            fixedCost.setExchangeRateUsed(exchangeRate);

            fixedCost.setAmountUsd(
                    fixedCost.getAmount()
                            .divide(exchangeRate, 2, RoundingMode.HALF_UP)
            );
        }
    }

    private void validateAllocationMonth(String allocationMonth) {
        if (allocationMonth == null) {
            throw new RuntimeException("Allocation month is required");
        }

        try {
            YearMonth.parse(allocationMonth);
        } catch (DateTimeParseException ex) {
            throw new RuntimeException(
                    "Invalid allocationMonth format. Expected YYYY-MM"
            );
        }
    }
}