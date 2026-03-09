-- ===============================
-- Add monetary model to fixed_costs
-- ===============================

-- 1) Add columns as NULLABLE first (safe for existing rows)

ALTER TABLE fixed_costs
ADD COLUMN currency_original VARCHAR(10);

ALTER TABLE fixed_costs
ADD COLUMN exchange_rate_used DECIMAL(15,2);

ALTER TABLE fixed_costs
ADD COLUMN amount_usd DECIMAL(15,2);


-- 2) Backfill existing rows safely
-- IMPORTANT:
-- We DO NOT invent historical rates.
-- We assume existing data is legacy ARS without USD conversion.
-- Exchange rate left NULL intentionally.

UPDATE fixed_costs
SET currency_original = 'ARS'
WHERE currency_original IS NULL;


-- 3) Enforce NOT NULL only on currency_original
-- (exchange_rate_used and amount_usd remain nullable for legacy rows)

ALTER TABLE fixed_costs
ALTER COLUMN currency_original SET NOT NULL;