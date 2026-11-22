CREATE TABLE card_limits (
                             id         BIGSERIAL      PRIMARY KEY,
                             card_id    BIGINT         NOT NULL REFERENCES cards (id),
                             limit_type VARCHAR(10)    NOT NULL,
                             amount     NUMERIC(12, 2) NOT NULL,
                             CONSTRAINT uq_card_limits_card_type UNIQUE (card_id, limit_type)
);

CREATE INDEX idx_card_limits_card_id ON card_limits (card_id);
