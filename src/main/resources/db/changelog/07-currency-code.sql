ALTER TABLE cards ADD COLUMN currency_code VARCHAR(3);

UPDATE cards SET currency_code = 'RUB' WHERE payment_system = 'MIR';
UPDATE cards SET currency_code = 'USD' WHERE payment_system = 'VISA';
UPDATE cards SET currency_code = 'EUR' WHERE payment_system = 'MASTERCARD';

ALTER TABLE cards ALTER COLUMN currency_code SET NOT NULL;