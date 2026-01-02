package com.facturacion.Afertech.controller;

import com.facturacion.Afertech.dto.CostTypeRequest;
import com.facturacion.Afertech.dto.CostTypeResponse;
import com.facturacion.Afertech.dto.PageResponse;
import com.facturacion.Afertech.service.CostTypeService;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/cost-types")
public class CostTypeController {

    private final CostTypeService service;

    public CostTypeController(CostTypeService service) {
        this.service = service;
    }

    @GetMapping
    public ResponseEntity<PageResponse<CostTypeResponse>> findAll(Pageable pageable) {

        Page<CostTypeResponse> page = service.findAll(pageable);

        PageResponse<CostTypeResponse> response = new PageResponse<>(
                page.getContent(),
                page.getNumber(),
                page.getSize(),
                page.getTotalElements(),
                page.getTotalPages()
        );

        return ResponseEntity.ok(response);
    }

    @GetMapping("/{id}")
    public ResponseEntity<CostTypeResponse> findById(@PathVariable Long id) {
        return ResponseEntity.ok(service.findById(id));
    }

    @PostMapping
    public ResponseEntity<CostTypeResponse> create(
            @Valid @RequestBody CostTypeRequest request
    ) {
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(service.create(request));
    }

    @PutMapping("/{id}")
    public ResponseEntity<CostTypeResponse> update(
            @PathVariable Long id,
            @Valid @RequestBody CostTypeRequest request
    ) {
        return ResponseEntity.ok(service.update(id, request));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        service.delete(id);
        return ResponseEntity.noContent().build();
    }
}
