package com.facturacion.Afertech.repository;

import com.facturacion.Afertech.model.ReminderRecipient;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ReminderRecipientRepository extends JpaRepository<ReminderRecipient, Long> {

    boolean existsByEmailAndDeletedAtIsNull(String email);

    List<ReminderRecipient> findAllByActiveTrueAndDeletedAtIsNull();
}
