DROP DATABASE IF EXISTS bank_db;

CREATE DATABASE bank_db 
CHARACTER SET = utf8mb4 
COLLATE = utf8mb4_unicode_ci;

USE bank_db;

CREATE TABLE users (
  user_id VARCHAR(50) PRIMARY KEY,
  password VARCHAR(50) NOT NULL, 
  name VARCHAR(20) NOT NULL,
  account_number INT NOT NULL,
  is_active BOOLEAN DEFAULT TRUE
) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;

CREATE TABLE accounts (
  account_number INT PRIMARY KEY,
  balance INT DEFAULT 100000,
  user_id VARCHAR(50),
  FOREIGN KEY (user_id) REFERENCES users(user_id)
) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;

CREATE TABLE transactions (
  id INT AUTO_INCREMENT PRIMARY KEY,  
  sender_account INT,
  receiver_account INT,
  amount INT,
  transaction_time DATETIME DEFAULT CURRENT_TIMESTAMP,
  balance_after INT,
  FOREIGN KEY (sender_account) REFERENCES accounts(account_number),
  FOREIGN KEY (receiver_account) REFERENCES accounts(account_number)
) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;

INSERT INTO users (user_id, password, name, account_number)
VALUES (0, 0, 'admin', 0);

INSERT INTO accounts 
VALUES (0, 1000000000, 0);