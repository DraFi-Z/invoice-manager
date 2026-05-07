CREATE TABLE invoices (
                          id              BIGSERIAL PRIMARY KEY,
                          invoice_number  VARCHAR(50)    NOT NULL UNIQUE,
                          customer_id     BIGINT         NOT NULL,
                          created_by      BIGINT         NOT NULL,
                          status          VARCHAR(50)    NOT NULL DEFAULT 'DRAFT',
                          issue_date      DATE           NOT NULL,
                          due_date        DATE           NOT NULL,
                          notes           TEXT,
                          total_amount    NUMERIC(15, 2) NOT NULL DEFAULT 0.00,
                          created_at      TIMESTAMP      NOT NULL DEFAULT NOW(),
                          updated_at      TIMESTAMP      NOT NULL DEFAULT NOW(),

                          CONSTRAINT fk_invoice_customer
                              FOREIGN KEY (customer_id)
                                  REFERENCES customers(id),

                          CONSTRAINT fk_invoice_created_by
                              FOREIGN KEY (created_by)
                                  REFERENCES users(id),

                          CONSTRAINT chk_due_date
                              CHECK (due_date >= issue_date),

                          CONSTRAINT chk_status
                              CHECK (status IN ('DRAFT', 'PENDING', 'SENT', 'PAID', 'CANCELLED'))
);