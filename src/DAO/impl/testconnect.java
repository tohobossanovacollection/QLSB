package DAO.impl;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
public class testconnect {
    public static void main(String[] args) {
        // Test the connection to the database
        try(Connection connect = DatabaseConnector.connect("test")){
            if(connect != null)
            System.out.println("Connected to the database successfully!");
            else System.out.println("Failed to connect to the database!");
            DatabaseConnector.createDatabase("phantom");
                
                DatabaseConnector.dropDatabase("phantom");
                System.out.println("Database dropped successfully!");
        }catch (SQLException e) {
            System.out.println("Failed to connect to the database: " + e.getMessage());
        }
        
    }
}
