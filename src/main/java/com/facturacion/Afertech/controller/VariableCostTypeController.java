package com.facturacion.Afertech.controller;

import com.facturacion.Afertech.dto.PageResponse;
import com.facturacion.Afertech.dto.VariableCostTypeRequest;
import com.facturacion.Afertech.dto.VariableCostTypeResponse;
import com.facturacion.Afertech.service.VariableCostTypeService;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/variable-cost-types")
public class VariableCostTypeController {

    private final VariableCostTypeService service;

    public VariableCostTypeController(VariableCostTypeService service) {
        this.service = service;
    }

    @GetMapping
    public ResponseEntity<PageResponse<VariableCostTypeResponse>> findAll(Pageable pageable) {

        Page<VariableCostTypeResponse> page = service.findAll(pageable);

        PageResponse<VariableCostTypeResponse> response = new PageResponse<>(
                page.getContent(),
                page.getNumber(),
                page.getSize(),
                page.getTotalElements(),
                page.getTotalPages()
        );

        return ResponseEntity.ok(response);
    }

    @GetMapping("/{id}")
    public ResponseEntity<VariableCostTypeResponse> findById(@PathVariable Long id) {
        return ResponseEntity.ok(service.findById(id));
    }

    @PostMapping
    public ResponseEntity<VariableCostTypeResponse> create(
            @Valid @RequestBody VariableCostTypeRequest request
    ) {
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(service.create(request));
    }

    @PutMapping("/{id}")
    public ResponseEntity<VariableCostTypeResponse> update(
            @PathVariable Long id,
            @Valid @RequestBody VariableCostTypeRequest request
    ) {
        return ResponseEntity.ok(service.update(id, request));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        service.delete(id);
        return ResponseEntity.noContent().build();
    }
}
