CREATE TABLE IF NOT EXISTS WALLETS (
    ID SERIAL PRIMARY KEY, FULL_NAME VARCHAR(100), CPF BIGINT, EMAIL VARCHAR(100), 
    "PASSWORD" VARCHAR(100), "TYPE" INT, BALANCE DECIMAL(10, 2), "VERSION" BIGINT
);

CREATE UNIQUE INDEX IF NOT EXISTS cpf_idx ON WALLETS (CPF);

CREATE UNIQUE INDEX IF NOT EXISTS email_idx ON WALLETS (EMAIL);

CREATE TABLE IF NOT EXISTS TRANSACTIONS (
    ID SERIAL PRIMARY KEY, PAYER INT, PAYEE INT, "VALUE" DECIMAL(10, 2), 
    CREATED_AT TIMESTAMP, FOREIGN KEY (PAYER) REFERENCES WALLETS (ID), 
    FOREIGN KEY (PAYEE) REFERENCES WALLETS (ID)
);