package com.facturacion.Afertech.controller;

import com.facturacion.Afertech.dto.PageResponse;
import com.facturacion.Afertech.dto.VariableCostRequest;
import com.facturacion.Afertech.dto.VariableCostResponse;
import com.facturacion.Afertech.service.VariableCostService;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/variable-costs")
public class VariableCostController {

    private final VariableCostService service;

    public VariableCostController(VariableCostService service) {
        this.service = service;
    }

    @GetMapping
    public ResponseEntity<PageResponse<VariableCostResponse>> findAll(Pageable pageable) {

        Page<VariableCostResponse> page = service.findAll(pageable);

        PageResponse<VariableCostResponse> response = new PageResponse<>(
                page.getContent(),
                page.getNumber(),
                page.getSize(),
                page.getTotalElements(),
                page.getTotalPages()
        );

        return ResponseEntity.ok(response);
    }

    @GetMapping("/{id}")
    public ResponseEntity<VariableCostResponse> findById(@PathVariable Long id) {
        return ResponseEntity.ok(service.findById(id));
    }

    @PostMapping
    public ResponseEntity<VariableCostResponse> create(
            @Valid @RequestBody VariableCostRequest request
    ) {
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(service.create(request));
    }

    @PutMapping("/{id}")
    public ResponseEntity<VariableCostResponse> update(
            @PathVariable Long id,
            @Valid @RequestBody VariableCostRequest request
    ) {
        return ResponseEntity.ok(service.update(id, request));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        service.delete(id);
        return ResponseEntity.noContent().build();
    }
}
