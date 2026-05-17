ALTER TABLE invoices
    ADD COLUMN IF NOT EXISTS currency_original VARCHAR(10) NOT NULL DEFAULT 'USD';

ALTER TABLE invoices
    ADD COLUMN IF NOT EXISTS exchange_rate_used DECIMAL(15,2);

ALTER TABLE invoices
    ADD COLUMN IF NOT EXISTS total_without_tax_usd DECIMAL(15,2);

ALTER TABLE invoices
    ADD COLUMN IF NOT EXISTS total_with_tax_usd DECIMAL(15,2);