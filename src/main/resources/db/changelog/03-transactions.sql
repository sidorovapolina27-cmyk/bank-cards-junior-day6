-- История операций по картам.

CREATE TABLE transactions (
    id            BIGSERIAL      PRIMARY KEY,
    card_id       BIGINT         NOT NULL REFERENCES cards (id),
    amount        NUMERIC(12, 2) NOT NULL,
    currency_code VARCHAR(3)     NOT NULL,
    tx_type       VARCHAR(10)    NOT NULL,
    description   VARCHAR(200)   NOT NULL,
    occurred_at   TIMESTAMP      NOT NULL
);

CREATE INDEX idx_transactions_card_id ON transactions (card_id);
