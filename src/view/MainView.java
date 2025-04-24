package view;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.Scanner;
import utils.DateTimeUtils; // Removed as the class is not found

public class MainView {
    private Scanner scanner;
    private DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm");
    
    public MainView() {
        scanner = new Scanner(System.in);
    }
    
    public String getUsername() {
        System.out.print("Username: ");
        return scanner.nextLine();
    }
    
    public String getPassword() {
        System.out.print("Password: ");
        return scanner.nextLine();
    }
    
    public void displayMessage(String message) {
        System.out.println("=== " + message + " ===");
    }
    
    public int displayMainMenu() {
        System.out.println("\n===== FOOTBALL PITCH MANAGEMENT SYSTEM =====");
        System.out.println("1. Pitch Management");
        System.out.println("2. Booking Management");
        System.out.println("3. Customer Management");
        System.out.println("0. Exit");
        System.out.print("Please select an option: ");
        
        try {
            return Integer.parseInt(scanner.nextLine());
        } catch (NumberFormatException e) {
            return -1; // Invalid input
        }
    }
    
    public int displayPitchMenu() {
        System.out.println("\n===== PITCH MANAGEMENT =====");
        System.out.println("1. View All Pitches");
        System.out.println("2. Add New Pitch");
        System.out.println("3. Update Pitch");
        System.out.println("4. Delete Pitch");
        System.out.println("5. View Available Pitches");
        System.out.println("0. Return to Main Menu");
        System.out.print("Please select an option: ");
        
        try {
            return Integer.parseInt(scanner.nextLine());
        } catch (NumberFormatException e) {
            return -1; // Invalid input
        }
    }
    
    public int displayBookingMenu() {
        System.out.println("\n===== BOOKING MANAGEMENT =====");
        System.out.println("1. Create New Booking");
        System.out.println("2. View All Bookings");
        System.out.println("3. Search Bookings by Date");
        System.out.println("4. Search Bookings by Customer");
        System.out.println("5. Cancel Booking");
        System.out.println("0. Return to Main Menu");
        System.out.print("Please select an option: ");
        
        try {
            return Integer.parseInt(scanner.nextLine());
        } catch (NumberFormatException e) {
            return -1; // Invalid input
        }
    }
    
    public int displayCustomerMenu() {
        System.out.println("\n===== CUSTOMER MANAGEMENT =====");
        System.out.println("1. View All Customers");
        System.out.println("2. Add New Customer");
        System.out.println("3. Update Customer");
        System.out.println("4. Search Customer by Phone");
        System.out.println("0. Return to Main Menu");
        System.out.print("Please select an option: ");
        
        try {
            return Integer.parseInt(scanner.nextLine());
        } catch (NumberFormatException e) {
            return -1; // Invalid input
        }
    }
    
    public LocalDateTime getDateForSearch() {
        LocalDateTime date = null;
        boolean validInput = false;
        
        while (!validInput) {
            System.out.print("Enter date (dd/MM/yyyy HH:mm): ");
            String dateStr = scanner.nextLine();
            
            try {
                date = LocalDateTime.parse(dateStr, dateFormatter);
                validInput = true;
            } catch (DateTimeParseException e) {
                System.out.println("Invalid date format. Please use format: dd/MM/yyyy HH:mm");
            }
        }
        
        return date;
    }
    
    public void close() {
        if (scanner != null) {
            scanner.close();
        }
    }
}  