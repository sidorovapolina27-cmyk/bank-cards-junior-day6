-- Тестовые данные (сид) для локального контура.

INSERT INTO clients (id, full_name, phone_number, email, created_at) VALUES
(1001, 'Иванов Иван Иванович',        '+7 913 123-45-67', 'ivanov@mail.ru',  TIMESTAMP '2024-01-15 09:30:00'),
(1002, 'Петрова Мария Сергеевна',     '+7 921 555-10-20', 'petrova@mail.ru', TIMESTAMP '2024-02-20 14:05:00'),
(1003, 'Сидоров Алексей Петрович',    '+7 903 777-30-40', 'sidorov@mail.ru', TIMESTAMP '2024-03-08 18:45:00');

INSERT INTO cards (id, client_id, pan, card_holder_name, card_type, payment_system, status, valid_from, valid_to, product_name, created_at) VALUES
(2001, 1001, '2200000000001001', 'IVAN IVANOV',    'DEBIT',  'MIR',        'ACTIVE',  DATE '2024-03-01', DATE '2029-03-01', 'Карта Классическая',  TIMESTAMP '2024-03-01 10:00:00'),
(2002, 1001, '4111111111111002', 'IVAN IVANOV',    'CREDIT', 'VISA',       'BLOCKED', DATE '2023-11-10', DATE '2028-11-10', 'Карта Кредитная',     TIMESTAMP '2023-11-10 12:30:00'),
(2003, 1001, '5555555555551003', 'IVAN IVANOV',    'DEBIT',  'MASTERCARD', 'CLOSED',  DATE '2020-05-01', DATE '2025-05-01', 'Карта Зарплатная',    TIMESTAMP '2020-05-01 08:15:00'),
(2004, 1002, '2202000000001004', 'MARIA PETROVA',  'DEBIT',  'MIR',        'ACTIVE',  DATE '2025-01-20', DATE '2030-01-20', 'Карта Дети',          TIMESTAMP '2025-01-20 16:40:00'),
(2005, 1002, '5105555555551005', 'MARIA PETROVA',  'CREDIT', 'MASTERCARD', 'NEW',     DATE '2026-01-10', DATE '2031-01-10', 'Карта Премиальная',   TIMESTAMP '2026-01-10 11:05:00'),
(2006, 1003, '4222222222221006', 'ALEXEY SIDOROV', 'DEBIT',  'VISA',       'ACTIVE',  DATE '2022-07-15', DATE '2027-07-15', 'Карта Классическая',  TIMESTAMP '2022-07-15 09:00:00'),
(2007, 1003, '2204000000001007', 'ALEXEY SIDOROV', 'CREDIT', 'MIR',        'EXPIRED', DATE '2020-02-01', DATE '2025-02-01', 'Карта Кредитная',     TIMESTAMP '2020-02-01 13:20:00'),
(2008, 1003, '5299999999991008', 'ALEXEY SIDOROV', 'DEBIT',  'MASTERCARD', 'ACTIVE',  DATE '2024-09-30', DATE '2029-09-30', 'Карта Цифровая',      TIMESTAMP '2024-09-30 17:55:00');

SELECT setval('clients_id_seq', 1003);
SELECT setval('cards_id_seq', 2008);
