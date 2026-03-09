package com.facturacion.Afertech.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.Where;

import java.time.LocalDateTime;

@Getter
@Setter
@Entity
@Table(
        name = "reminder_recipients",
        uniqueConstraints = {
                @UniqueConstraint(columnNames = "email")
        }
)
@Where(clause = "deleted_at IS NULL")
public class ReminderRecipient extends BaseAuditableEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String email;

    @Column(nullable = false)
    private Boolean active = true;

    @Column(nullable = false, updatable = false)
    private LocalDateTime loadedAt;

    @Column(nullable = false, updatable = false)
    private String loadedBy;

    @PrePersist
    protected void onPrePersist() {
        this.loadedAt = LocalDateTime.now();
    }
}
