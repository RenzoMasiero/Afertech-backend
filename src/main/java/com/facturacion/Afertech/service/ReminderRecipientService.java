package com.facturacion.Afertech.service;

import com.facturacion.Afertech.dto.ReminderRecipientRequest;
import com.facturacion.Afertech.dto.ReminderRecipientResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface ReminderRecipientService {

    Page<ReminderRecipientResponse> findAll(Pageable pageable);

    ReminderRecipientResponse findById(Long id);

    ReminderRecipientResponse create(ReminderRecipientRequest request);

    ReminderRecipientResponse update(Long id, ReminderRecipientRequest request);

    void delete(Long id);
}
