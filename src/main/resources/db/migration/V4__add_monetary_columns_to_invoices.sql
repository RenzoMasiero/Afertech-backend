ALTER TABLE invoices
    ADD COLUMN currency_original VARCHAR(10) NOT NULL DEFAULT 'USD';

ALTER TABLE invoices
    ADD COLUMN exchange_rate_used DECIMAL(15,2);

ALTER TABLE invoices
    ADD COLUMN total_without_tax_usd DECIMAL(15,2);

ALTER TABLE invoices
    ADD COLUMN total_with_tax_usd DECIMAL(15,2);