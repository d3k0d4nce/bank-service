-- Создание аккаунтов
INSERT INTO accounts (balance, initial_balance)
VALUES (1000.00, 1000.00);
INSERT INTO accounts (balance, initial_balance)
VALUES (500.00, 500.00);

-- Создание пользователей
INSERT INTO users (login, email, phone, first_name, middle_name, last_name, birth_date, password, account_id)
VALUES ('user1', 'user1@example.com', '12345678901', 'John', 'Doe', 'Smith', '1990-05-15', 'password1', 1);

INSERT INTO users (login, email, phone, first_name, middle_name, last_name, birth_date, password, account_id)
VALUES ('user2', 'user2@example.com', '23456789012', 'Jane', 'Lee', 'Taylor', '1985-08-20', 'password2', 2);
