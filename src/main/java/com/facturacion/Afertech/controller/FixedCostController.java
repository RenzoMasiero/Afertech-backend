package com.facturacion.Afertech.controller;

import com.facturacion.Afertech.dto.FixedCostRequest;
import com.facturacion.Afertech.dto.FixedCostResponse;
import com.facturacion.Afertech.dto.PageResponse;
import com.facturacion.Afertech.service.FixedCostService;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/fixed-costs")
public class FixedCostController {

    private final FixedCostService service;

    public FixedCostController(FixedCostService service) {
        this.service = service;
    }

    @GetMapping
    public ResponseEntity<PageResponse<FixedCostResponse>> findAll(Pageable pageable) {

        Page<FixedCostResponse> page = service.findAll(pageable);

        PageResponse<FixedCostResponse> response = new PageResponse<>(
                page.getContent(),
                page.getNumber(),
                page.getSize(),
                page.getTotalElements(),
                page.getTotalPages()
        );

        return ResponseEntity.ok(response);
    }

    @GetMapping("/{id}")
    public ResponseEntity<FixedCostResponse> findById(@PathVariable Long id) {
        return ResponseEntity.ok(service.findById(id));
    }

    @PostMapping
    public ResponseEntity<FixedCostResponse> create(
            @Valid @RequestBody FixedCostRequest request
    ) {
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(service.create(request));
    }

    @PutMapping("/{id}")
    public ResponseEntity<FixedCostResponse> update(
            @PathVariable Long id,
            @Valid @RequestBody FixedCostRequest request
    ) {
        return ResponseEntity.ok(service.update(id, request));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        service.delete(id);
        return ResponseEntity.noContent().build();
    }
}
