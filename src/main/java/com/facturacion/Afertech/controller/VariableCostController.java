package com.facturacion.Afertech.controller;

import com.facturacion.Afertech.dto.VariableCostRequest;
import com.facturacion.Afertech.dto.VariableCostResponse;
import com.facturacion.Afertech.service.VariableCostService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/variable-costs")
public class VariableCostController {

    private final VariableCostService service;

    public VariableCostController(VariableCostService service) {
        this.service = service;
    }

    @GetMapping
    public ResponseEntity<List<VariableCostResponse>> findAll() {
        return ResponseEntity.ok(service.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<VariableCostResponse> findById(@PathVariable Long id) {
        return ResponseEntity.ok(service.findById(id));
    }

    @PostMapping
    public ResponseEntity<VariableCostResponse> create(@RequestBody VariableCostRequest request) {
        return ResponseEntity.ok(service.create(request));
    }

    @PutMapping("/{id}")
    public ResponseEntity<VariableCostResponse> update(
            @PathVariable Long id,
            @RequestBody VariableCostRequest request
    ) {
        return ResponseEntity.ok(service.update(id, request));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        service.delete(id);
        return ResponseEntity.noContent().build();
    }
}
