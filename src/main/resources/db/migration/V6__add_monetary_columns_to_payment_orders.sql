ALTER TABLE payment_orders
    ADD COLUMN currency_original VARCHAR(10) NOT NULL DEFAULT 'ARS';

ALTER TABLE payment_orders
    ADD COLUMN exchange_rate_used DECIMAL(15,2);

ALTER TABLE payment_orders
    ADD COLUMN total_without_tax_usd DECIMAL(15,2);

ALTER TABLE payment_orders
    ADD COLUMN total_with_tax_usd DECIMAL(15,2);