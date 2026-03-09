-- ==========================================
-- V10__create_reminder_recipients.sql
-- Create reminder_recipients table
-- ==========================================

CREATE TABLE reminder_recipients (

    id BIGSERIAL PRIMARY KEY,

    email VARCHAR(255) NOT NULL UNIQUE,

    active BOOLEAN NOT NULL DEFAULT TRUE,

    -- functional data
    loaded_at TIMESTAMP NOT NULL,
    loaded_by VARCHAR(255) NOT NULL,

    -- technical audit (BaseAuditableEntity)
    created_at TIMESTAMP NOT NULL,
    created_by VARCHAR(255) NOT NULL,
    updated_at TIMESTAMP NOT NULL,
    updated_by VARCHAR(255) NOT NULL,
    deleted_at TIMESTAMP,
    deleted_by VARCHAR(255)
);