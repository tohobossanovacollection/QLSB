package DAO.impl;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public final class DatabaseConnector {
    private static String URL = "jdbc:mysql://localhost:3306/MySQL";
    private static final String USER = "root";
    private static final String PASSWORD = "1234";

    public static Connection connect() {
        try {
            return DriverManager.getConnection(URL, USER, PASSWORD);
        } catch (SQLException e) {
            throw new RuntimeException("Error connecting to the database", e);
        }
    }
    public static void createDatabase(String dbName) {
        try (Connection connection = DriverManager.getConnection(URL, USER, PASSWORD);
             Statement statement = connection.createStatement()) {
            String createDatabaseQuery = "CREATE DATABASE IF NOT EXISTS " + dbName;
            statement.executeUpdate(createDatabaseQuery);
            System.out.println(dbName + " created successfully!");
        } catch (SQLException e) {
            throw new RuntimeException("Error creating the database", e);
        }
    }
    public static Connection connect(String name)
    {
        try {
            name=String.format("jdbc:mysql://localhost:3306/%s", name);
            return DriverManager.getConnection(name, USER, PASSWORD);
        } catch (SQLException e) {
            throw new RuntimeException("Error connecting to the database", e);
        }
    }
    public static void dropDatabase(String dbName) {
        try (Connection connection = DriverManager.getConnection(URL, USER, PASSWORD);
             Statement statement = connection.createStatement()) {
            String dropDatabaseQuery = "DROP DATABASE " + dbName;
            statement.executeUpdate(dropDatabaseQuery);
            System.out.println(dbName + " dropped successfully!");
        } catch (SQLException e) {
            throw new RuntimeException("Error dropping the database", e);
        }
    }
}
