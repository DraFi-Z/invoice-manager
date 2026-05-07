CREATE TABLE invoice_line_items (
                                    id            BIGSERIAL      PRIMARY KEY,
                                    invoice_id    BIGINT         NOT NULL,
                                    description   VARCHAR(255)   NOT NULL,
                                    quantity      NUMERIC(10, 2) NOT NULL,
                                    unit_price    NUMERIC(15, 2) NOT NULL,
                                    total_price   NUMERIC(15, 2) NOT NULL,
                                    created_at    TIMESTAMP      NOT NULL DEFAULT NOW(),
                                    updated_at    TIMESTAMP      NOT NULL DEFAULT NOW(),

                                    CONSTRAINT fk_line_item_invoice
                                        FOREIGN KEY (invoice_id)
                                            REFERENCES invoices(id)
                                            ON DELETE CASCADE,

                                    CONSTRAINT chk_quantity
                                        CHECK (quantity > 0),

                                    CONSTRAINT chk_unit_price
                                        CHECK (unit_price >= 0),

                                    CONSTRAINT chk_total_price
                                        CHECK (total_price >= 0)
);