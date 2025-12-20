package com.facturacion.Afertech.controller;

import com.facturacion.Afertech.dto.FixedCostRequest;
import com.facturacion.Afertech.dto.FixedCostResponse;
import com.facturacion.Afertech.service.FixedCostService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/fixed-costs")
public class FixedCostController {

    private final FixedCostService service;

    public FixedCostController(FixedCostService service) {
        this.service = service;
    }

    @GetMapping
    public ResponseEntity<List<FixedCostResponse>> findAll() {
        return ResponseEntity.ok(service.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<FixedCostResponse> findById(@PathVariable Long id) {
        return ResponseEntity.ok(service.findById(id));
    }

    @PostMapping
    public ResponseEntity<FixedCostResponse> create(@RequestBody FixedCostRequest request) {
        return ResponseEntity.ok(service.create(request));
    }

    @PutMapping("/{id}")
    public ResponseEntity<FixedCostResponse> update(
            @PathVariable Long id,
            @RequestBody FixedCostRequest request
    ) {
        return ResponseEntity.ok(service.update(id, request));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        service.delete(id);
        return ResponseEntity.noContent().build();
    }
}
