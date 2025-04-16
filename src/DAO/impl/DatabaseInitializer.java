package DAO.impl;

import java.sql.Connection;
import java.sql.Statement;

public class DatabaseInitializer {

    public static void initializeDatabase() {
        try (Connection connection = DatabaseConnector.connect("QuanLySB");
             Statement statement = connection.createStatement()) {

            // 1. Create branches table
            String createBranchesTable = """
                CREATE TABLE IF NOT EXISTS branches (
                    id INT PRIMARY KEY AUTO_INCREMENT,
                    name VARCHAR(255) NOT NULL,
                    address VARCHAR(255),
                    phone VARCHAR(20),
                    manager_name VARCHAR(255),
                    active BOOLEAN DEFAULT TRUE
                ) ENGINE=InnoDB;
            """;
            statement.executeUpdate(createBranchesTable);

            // 2. Create users table
            String createUsersTable = """
                CREATE TABLE IF NOT EXISTS users (
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
            """;
            statement.executeUpdate(createUsersTable);

            // 3. Create pitches table
            String createPitchesTable = """
                CREATE TABLE IF NOT EXISTS pitches (
                    id INT PRIMARY KEY AUTO_INCREMENT,
                    name VARCHAR(255) NOT NULL,
                    type VARCHAR(20) COMMENT '5 người, 7 người, 11 người',
                    price_per_hour DOUBLE NOT NULL,
                    description TEXT,
                    branch_id INT,
                    active BOOLEAN DEFAULT TRUE,
                    FOREIGN KEY (branch_id) REFERENCES branches(id)
                ) ENGINE=InnoDB;
            """;
            statement.executeUpdate(createPitchesTable);

            // 4. Create customers table
            String createCustomersTable = """
                CREATE TABLE IF NOT EXISTS customers (
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
            """;
            statement.executeUpdate(createCustomersTable);

            // 5. Create bookings table
            String createBookingsTable = """
                CREATE TABLE IF NOT EXISTS bookings (
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
            """;
            statement.executeUpdate(createBookingsTable);

            // 6. Create monthly_bookings table
            String createMonthlyBookingsTable = """
                    
                    
                CREATE TABLE IF NOT EXISTS monthly_bookings (
                    id INT PRIMARY KEY AUTO_INCREMENT,
                    customer_id INT,
                    pitch_id INT,
                    start_date DATE NOT NULL,
                    end_date DATE NOT NULL,
                    start_time TIME NOT NULL,
                    end_time TIME NOT NULL,
                    days_of_week VARCHAR(100) ,
                    sessions_per_month INT NOT NULL,
                    price_per_session DOUBLE NOT NULL,
                    total_amount DOUBLE NOT NULL,
                    discount DOUBLE DEFAULT 0,
                    final_amount DOUBLE NOT NULL,
                    status VARCHAR(20),
                    note TEXT,
                    FOREIGN KEY (customer_id) REFERENCES customers(id),
                    FOREIGN KEY (pitch_id) REFERENCES pitches(id)
                ) ENGINE=InnoDB;
            """;
            statement.executeUpdate(createMonthlyBookingsTable);

            // 7. Create products table
            String createProductsTable = """
                CREATE TABLE IF NOT EXISTS products (
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
            """;
            statement.executeUpdate(createProductsTable);

            // 8. Create invoices table
            String createInvoicesTable = """
                CREATE TABLE IF NOT EXISTS invoices (
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
            """;
            statement.executeUpdate(createInvoicesTable);

            // 9. Create transactions table
            String createTransactionsTable = """
                CREATE TABLE IF NOT EXISTS transactions (
                    id INT PRIMARY KEY AUTO_INCREMENT,
                    type VARCHAR(20) COMMENT 'INCOME, EXPENSE',
                    category VARCHAR(50) COMMENT 'BOOKING, PRODUCT_SALE, SALARY, MAINTENANCE, ...',
                    amount DOUBLE NOT NULL,
                    date DATETIME DEFAULT CURRENT_TIMESTAMP,
                    description TEXT,
                    related_id INT COMMENT 'ID của invoice hoặc expense',
                    branch_id INT,
                    FOREIGN KEY (branch_id) REFERENCES branches(id)
                ) ENGINE=InnoDB;
            """;
            statement.executeUpdate(createTransactionsTable);

            System.out.println("Database and tables created successfully!");
            // 10. Create invoice_items table
            String createInvoiceItemsTable = """
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
            """;
            statement.executeUpdate(createInvoiceItemsTable);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void startDB()  {
        try (Connection connection = DatabaseConnector.connect();
             Statement statement = connection.createStatement()){
                String createDB = "CREATE DATABASE IF NOT EXISTS QuanLySB CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;";
                statement.executeUpdate(createDB);
                String usingDB ="USE QuanLySB;";
                statement.executeUpdate(usingDB);
             }
             catch(Exception e) {
                e.printStackTrace();
             }
    }
    public static void main(String[] args) {
        startDB();
        initializeDatabase();
    }
}
