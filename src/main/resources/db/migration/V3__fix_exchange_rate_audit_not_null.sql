ALTER TABLE exchange_rate
    ALTER COLUMN created_at SET NOT NULL;

ALTER TABLE exchange_rate
    ALTER COLUMN created_by SET NOT NULL;

ALTER TABLE exchange_rate
    ALTER COLUMN updated_at SET NOT NULL;

ALTER TABLE exchange_rate
    ALTER COLUMN updated_by SET NOT NULL;
