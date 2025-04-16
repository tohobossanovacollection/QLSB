/*-- Create the database
CREATE DATABASE IF NOT EXISTS QuanLySB CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;

-- Use the database
USE QuanLySB;

-- 1. Create branches table (needs to come before users table due to foreign key)
CREATE TABLE branches (
    id INT PRIMARY KEY AUTO_INCREMENT,
    name VARCHAR(255) NOT NULL,
    address VARCHAR(255),
    phone VARCHAR(20),
    manager_name VARCHAR(255),
    active BOOLEAN DEFAULT TRUE
) ENGINE=InnoDB;

-- 2. Create users table
CREATE TABLE users (
    id INT PRIMARY KEY AUTO_INCREMENT,
    username VARCHAR(50) NOT NULL UNIQUE,
    password VARCHAR(255) NOT NULL,
    full_name VARCHAR(255),
    email VARCHAR(255),
    phone VARCHAR(20),
    role VARCHAR(20) NOT NULL COMMENT 'ADMIN, MANAGER, STAFF',
    branch_id INT,
    active BOOLEAN DEFAULT TRUE,
    last_login DATETIME,
    FOREIGN KEY (branch_id) REFERENCES branches(id)
) ENGINE=InnoDB;

-- 3. Create pitches table
CREATE TABLE pitches (
    id INT PRIMARY KEY AUTO_INCREMENT,
    name VARCHAR(255) NOT NULL,
    type VARCHAR(20) COMMENT '5 người, 7 người, 11 người',
    price_per_hour DOUBLE NOT NULL,
    description TEXT,
    branch_id INT,
    active BOOLEAN DEFAULT TRUE,
    FOREIGN KEY (branch_id) REFERENCES branches(id)
) ENGINE=InnoDB;

-- 4. Create customers table
CREATE TABLE customers (
    id INT PRIMARY KEY AUTO_INCREMENT,
    name VARCHAR(255) NOT NULL,
    phone VARCHAR(20),
    email VARCHAR(255),
    address VARCHAR(255),
    customer_type VARCHAR(20) COMMENT 'REGULAR, VIP, TEAM',
    total_spent DOUBLE DEFAULT 0,
    debt DOUBLE DEFAULT 0,
    created_at DATETIME DEFAULT CURRENT_TIMESTAMP
) ENGINE=InnoDB;

-- 5. Create bookings table
CREATE TABLE bookings (
    id INT PRIMARY KEY AUTO_INCREMENT,
    pitch_id INT,
    customer_id INT,
    start_time DATETIME NOT NULL,
    end_time DATETIME NOT NULL,
    total_price DOUBLE NOT NULL,
    status VARCHAR(20) COMMENT 'PENDING, CONFIRMED, CANCELLED, COMPLETED',
    is_periodic BOOLEAN DEFAULT FALSE,
    note TEXT,
    created_at DATETIME DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (pitch_id) REFERENCES pitches(id),
    FOREIGN KEY (customer_id) REFERENCES customers(id)
) ENGINE=InnoDB;

-- 6. Create monthly_bookings table
CREATE TABLE monthly_bookings (
    id INT PRIMARY KEY AUTO_INCREMENT,
    customer_id INT,
    pitch_id INT,
    start_date DATE NOT NULL,
    end_date DATE NOT NULL,
    start_time TIME NOT NULL,
    end_time TIME NOT NULL,
    days_of_week VARCHAR(100) COMMENT 'e.g., "MONDAY,WEDNESDAY,FRIDAY"',
    sessions_per_month INT NOT NULL,
    price_per_session DOUBLE NOT NULL,
    total_amount DOUBLE NOT NULL,
    discount DOUBLE DEFAULT 0,
    final_amount DOUBLE NOT NULL,
    status VARCHAR(20) COMMENT 'ACTIVE, INACTIVE, COMPLETED',
    note TEXT,
    FOREIGN KEY (customer_id) REFERENCES customers(id),
    FOREIGN KEY (pitch_id) REFERENCES pitches(id)
) ENGINE=InnoDB;

-- 7. Create products table
CREATE TABLE products (
    id INT PRIMARY KEY AUTO_INCREMENT,
    name VARCHAR(255) NOT NULL,
    category VARCHAR(100),
    buy_price DOUBLE NOT NULL,
    sell_price DOUBLE NOT NULL,
    current_stock INT DEFAULT 0,
    min_stock_level INT DEFAULT 5,
    unit VARCHAR(20) COMMENT 'cái, chai, lon, ...',
    description TEXT,
    active BOOLEAN DEFAULT TRUE
) ENGINE=InnoDB;

-- 8. Create invoices table
CREATE TABLE invoices (
    id INT PRIMARY KEY AUTO_INCREMENT,
    customer_id INT,
    pitch_id INT,
    created_at DATETIME DEFAULT CURRENT_TIMESTAMP,
    type VARCHAR(20) COMMENT 'BOOKING, PRODUCT',
    subtotal DOUBLE NOT NULL,
    discount DOUBLE DEFAULT 0,
    total DOUBLE NOT NULL,
    paid DOUBLE DEFAULT 0,
    debt DOUBLE DEFAULT 0,
    status VARCHAR(20) COMMENT 'PAID, PARTIAL, UNPAID',
    note TEXT,
    FOREIGN KEY (customer_id) REFERENCES customers(id),
    FOREIGN KEY (pitch_id) REFERENCES pitches(id)
) ENGINE=InnoDB;

-- 9. Create invoice_items table
CREATE TABLE invoice_items (
    id INT PRIMARY KEY AUTO_INCREMENT,
    invoice_id INT,
    item_type VARCHAR(20) COMMENT 'BOOKING, PRODUCT',
    item_id INT COMMENT 'ID của booking hoặc product',
    description TEXT,
    unit_price DOUBLE NOT NULL,
    quantity INT NOT NULL,
    total DOUBLE NOT NULL,
    FOREIGN KEY (invoice_id) REFERENCES invoices(id)
) ENGINE=InnoDB;

-- 10. Create transactions table
CREATE TABLE transactions (
    id INT PRIMARY KEY AUTO_INCREMENT,
    type VARCHAR(20) COMMENT 'INCOME, EXPENSE',
    category VARCHAR(50) COMMENT 'BOOKING, PRODUCT_SALE, SALARY, MAINTENANCE, ...',
    amount DOUBLE NOT NULL,
    date DATETIME DEFAULT CURRENT_TIMESTAMP,
    description TEXT,
    related_id INT COMMENT 'ID của invoice hoặc expense',
    branch_id INT,
    FOREIGN KEY (branch_id) REFERENCES branches(id)
) ENGINE=InnoDB;*/