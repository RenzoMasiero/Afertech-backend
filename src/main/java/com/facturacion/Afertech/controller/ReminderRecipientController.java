package com.facturacion.Afertech.controller;

import com.facturacion.Afertech.dto.PageResponse;
import com.facturacion.Afertech.dto.ReminderRecipientRequest;
import com.facturacion.Afertech.dto.ReminderRecipientResponse;
import com.facturacion.Afertech.service.ReminderRecipientService;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/reminder-recipients")
public class ReminderRecipientController {

    private final ReminderRecipientService service;

    public ReminderRecipientController(ReminderRecipientService service) {
        this.service = service;
    }

    @GetMapping
    public ResponseEntity<PageResponse<ReminderRecipientResponse>> findAll(Pageable pageable) {

        Page<ReminderRecipientResponse> page = service.findAll(pageable);

        PageResponse<ReminderRecipientResponse> response = new PageResponse<>(
                page.getContent(),
                page.getNumber(),
                page.getSize(),
                page.getTotalElements(),
                page.getTotalPages()
        );

        return ResponseEntity.ok(response);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ReminderRecipientResponse> findById(@PathVariable Long id) {
        return ResponseEntity.ok(service.findById(id));
    }

    @PostMapping
    public ResponseEntity<ReminderRecipientResponse> create(
            @Valid @RequestBody ReminderRecipientRequest request
    ) {
        return ResponseEntity.ok(service.create(request));
    }

    @PutMapping("/{id}")
    public ResponseEntity<ReminderRecipientResponse> update(
            @PathVariable Long id,
            @Valid @RequestBody ReminderRecipientRequest request
    ) {
        return ResponseEntity.ok(service.update(id, request));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        service.delete(id);
        return ResponseEntity.noContent().build();
    }
}
