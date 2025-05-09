CREATE DATABASE IF NOT EXISTS bank_db;
USE bank_db;

-- User login table
CREATE TABLE IF NOT EXISTS users (
    user_id INT AUTO_INCREMENT PRIMARY KEY,
    username VARCHAR(50) UNIQUE NOT NULL,
    password VARCHAR(255) NOT NULL,
    role ENUM('admin', 'user') DEFAULT 'user'
);

-- Bank accounts linked to users
CREATE TABLE IF NOT EXISTS accounts (
    account_no VARCHAR(20) PRIMARY KEY,
    user_id INT,
    holder_name VARCHAR(100),
    balance DOUBLE DEFAULT 0.0,
    FOREIGN KEY (user_id) REFERENCES users(user_id)
);

-- Transactions history
CREATE TABLE IF NOT EXISTS transactions (
    id INT AUTO_INCREMENT PRIMARY KEY,
    account_no VARCHAR(20),
    type ENUM('deposit', 'withdraw') NOT NULL,
    amount DOUBLE NOT NULL,
    date TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (account_no) REFERENCES accounts(account_no)
);