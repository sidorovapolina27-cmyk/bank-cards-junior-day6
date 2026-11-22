INSERT INTO card_limits (card_id, limit_type, amount)
SELECT c.id, 'DAILY', 100000.00
FROM cards c
WHERE NOT EXISTS (
    SELECT 1 FROM card_limits cl WHERE cl.card_id = c.id AND cl.limit_type = 'DAILY'
);