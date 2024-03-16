CREATE TABLE if not exists accounts
(
    id             SERIAL PRIMARY KEY,
    balance        DECIMAL(10, 2) CHECK (balance > 0.0),
    initial_balance DECIMAL(10, 2) CHECK (initial_balance > 0.0)
    );

CREATE TABLE if not exists users
(
    id         SERIAL PRIMARY KEY,
    login      VARCHAR(20) UNIQUE NOT NULL,
    email      VARCHAR(50) UNIQUE,
    phone      VARCHAR(11) UNIQUE,
    first_name  VARCHAR(50) NOT NULL,
    middle_name VARCHAR(50) NOT NULL,
    last_name   VARCHAR(50) NOT NULL,
    birth_date  DATE NOT NULL,
    password   VARCHAR(120) NOT NULL,
    account_id  BIGINT,
    FOREIGN KEY (account_id) REFERENCES accounts (id)
    );
