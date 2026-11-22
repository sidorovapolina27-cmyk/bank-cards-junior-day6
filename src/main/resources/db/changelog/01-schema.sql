-- Схема сервиса отображения карт.

CREATE TABLE clients (
    id           BIGSERIAL    PRIMARY KEY,
    full_name    VARCHAR(200) NOT NULL,
    phone_number VARCHAR(30)  NOT NULL,
    email        VARCHAR(120) NOT NULL,
    created_at   TIMESTAMP    NOT NULL
);

CREATE TABLE cards (
    id               BIGSERIAL    PRIMARY KEY,
    client_id        BIGINT       NOT NULL REFERENCES clients (id),
    pan              VARCHAR(16)  NOT NULL,
    card_holder_name VARCHAR(100) NOT NULL,
    card_type        VARCHAR(20)  NOT NULL,
    payment_system   VARCHAR(20)  NOT NULL,
    status           VARCHAR(20)  NOT NULL,
    valid_from       DATE         NOT NULL,
    valid_to         DATE         NOT NULL,
    product_name     VARCHAR(100),
    created_at       TIMESTAMP    NOT NULL
);
