-- ==========================================
-- V9__add_monetary_model_to_variable_cost.sql
-- Add monetary model (USD base) to variable_costs
-- ==========================================

ALTER TABLE variable_costs
ADD COLUMN currency_original VARCHAR(10);

ALTER TABLE variable_costs
ADD COLUMN exchange_rate_used DECIMAL(15,2);

ALTER TABLE variable_costs
ADD COLUMN amount_usd DECIMAL(15,2);

UPDATE variable_costs
SET currency_original = 'ARS'
WHERE currency_original IS NULL;

ALTER TABLE variable_costs
ALTER COLUMN currency_original SET NOT NULL;