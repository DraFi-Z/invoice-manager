CREATE TABLE customers (
                           id            BIGSERIAL PRIMARY KEY,
                           name          VARCHAR(255) NOT NULL,
                           email         VARCHAR(255) NOT NULL UNIQUE,
                           phone         VARCHAR(50),
                           address_line1 VARCHAR(255),
                           address_line2 VARCHAR(255),
                           city          VARCHAR(100),
                           country       VARCHAR(100),
                           created_at    TIMESTAMP NOT NULL DEFAULT NOW(),
                           updated_at    TIMESTAMP NOT NULL DEFAULT NOW()
);