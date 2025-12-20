package com.facturacion.Afertech.controller;

import com.facturacion.Afertech.dto.CostTypeRequest;
import com.facturacion.Afertech.dto.CostTypeResponse;
import com.facturacion.Afertech.service.CostTypeService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/cost-types")
public class CostTypeController {

    private final CostTypeService service;

    public CostTypeController(CostTypeService service) {
        this.service = service;
    }

    @GetMapping
    public ResponseEntity<List<CostTypeResponse>> findAll() {
        return ResponseEntity.ok(service.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<CostTypeResponse> findById(@PathVariable Long id) {
        return ResponseEntity.ok(service.findById(id));
    }

    @PostMapping
    public ResponseEntity<CostTypeResponse> create(@RequestBody CostTypeRequest request) {
        return ResponseEntity.ok(service.create(request));
    }

    @PutMapping("/{id}")
    public ResponseEntity<CostTypeResponse> update(
            @PathVariable Long id,
            @RequestBody CostTypeRequest request
    ) {
        return ResponseEntity.ok(service.update(id, request));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        service.delete(id);
        return ResponseEntity.noContent().build();
    }
}
