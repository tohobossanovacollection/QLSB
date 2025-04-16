package view;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.List;
import java.util.Scanner;
import model.Booking;
import model.Customer;
import model.Pitch;
import service.CustomerService;
import service.PitchService;

public class BookingView {
    private Scanner scanner;
    private DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm");
    private PitchService pitchService;
    private CustomerService customerService;
    private Pitch pitch;
    
    public BookingView(PitchService pitchService, CustomerService customerService) {
        this.scanner = new Scanner(System.in);
        this.pitchService = pitchService;
        this.customerService = customerService;
    }
    
    public Booking getBookingData() {
        Booking booking = new Booking();
        
        // Select Pitch
        List<Pitch> pitches = pitchService.getAllPitches();
        System.out.println("\n===== AVAILABLE PITCHES =====");
        for (int i = 0; i < pitches.size(); i++) {
            Pitch pitch = pitches.get(i);
            System.out.println((i + 1) + ". " + pitch.getName() + " - " + pitch.getType() + " - " + pitch.getPricePerHour() + "/hour");
        }
        
        int pitchChoice = -1;
        while (pitchChoice < 0 || pitchChoice >= pitches.size()) {
            System.out.print("Select pitch (1-" + pitches.size() + "): ");
            try {
                pitchChoice = Integer.parseInt(scanner.nextLine()) - 1;
            } catch (NumberFormatException e) {
                System.out.println("Invalid input. Please enter a number.");
            }
        }
        booking.setPitchId(pitches.get(pitchChoice).getId());
        System.out.println();
        
        // Select Customer
        List<Customer> customers = customerService.getAllCustomers();
        System.out.println("\n===== CUSTOMERS =====");
        for (int i = 0; i < customers.size(); i++) {
            Customer customer = customers.get(i);
            System.out.println((i + 1) + ". " + customer.getName() + " - " + customer.getPhone());
        }
        System.out.println((customers.size() + 1) + ". Create new customer");
        
        int customerChoice = -1;
        while (customerChoice < 0 || customerChoice > customers.size()) {
            System.out.print("Select customer (1-" + (customers.size() + 1) + "): ");
            try {
                customerChoice = Integer.parseInt(scanner.nextLine()) - 1;
            } catch (NumberFormatException e) {
                System.out.println("Invalid input. Please enter a number.");
            }
        }
        
        // If last option, create new customer
        if (customerChoice == customers.size()) {
            CustomerView customerView = new CustomerView();
            Customer newCustomer = customerView.getCustomerData();
            customerService.addCustomer(newCustomer);
            booking.setCustomerId(newCustomer.getId());
        } else {
            //booking.setCustomer(customers.get(customerChoice));
        }
        
        // Set booking time
        boolean validStartTime = false;
        while (!validStartTime) {
            System.out.print("Enter start time (dd/MM/yyyy HH:mm): ");
            String startTimeStr = scanner.nextLine();
            
            try {
                LocalDateTime startTime = LocalDateTime.parse(startTimeStr, dateFormatter);
                booking.setStartTime(startTime);
                validStartTime = true;
            } catch (DateTimeParseException e) {
                System.out.println("Invalid date format. Please use format: dd/MM/yyyy HH:mm");
            }
        }
        
        boolean validEndTime = false;
        while (!validEndTime) {
            System.out.print("Enter end time (dd/MM/yyyy HH:mm): ");
            String endTimeStr = scanner.nextLine();
            
            try {
                LocalDateTime endTime = LocalDateTime.parse(endTimeStr, dateFormatter);
                
                if (endTime.isAfter(booking.getStartTime())) {
                    booking.setEndTime(endTime);
                    validEndTime = true;
                } else {
                    System.out.println("End time must be after start time.");
                }
            } catch (DateTimeParseException e) {
                System.out.println("Invalid date format. Please use format: dd/MM/yyyy HH:mm");
            }
        }
        
        // Set status
        booking.setStatus("CONFIRMED");
        
        return booking;
    }
    
    public void displayBookingSuccess(Booking booking) {
        System.out.println("\n===== BOOKING CREATED SUCCESSFULLY =====");
        System.out.println("Booking ID: " + booking.getId());
        System.out.println("Pitch: " + pitchService.getPitchById(booking.getPitchId()).getName());
        System.out.println("Customer: " + customerService.getCustomerById(booking.getCustomerId()).getName());
        System.out.println("Start Time: " + booking.getStartTime().format(dateFormatter));
        System.out.println("End Time: " + booking.getEndTime().format(dateFormatter));
        System.out.println("Status: " + booking.getStatus());
    }
    
    public void displayBookingUnavailable() {
        System.out.println("\nERROR: The selected pitch is not available for the specified time period.");
    }
    
    public void displayError(String errorMessage) {
        System.out.println("\nERROR: " + errorMessage);
    }
    
    public void displayBookingList(List<Booking> bookings) {
        if (bookings.isEmpty()) {
            System.out.println("\nNo bookings found.");
            return;
        }
        
        System.out.println("\n===== BOOKING LIST =====");
        System.out.printf("%-5s | %-15s | %-20s | %-20s | %-20s | %-10s\n", 
                "ID", "Pitch", "Customer", "Start Time", "End Time", "Status");
        System.out.println("---------------------------------------------------------------------------------------------------------------");
        
        //Pitch t = ;
        for (Booking booking : bookings) {
            System.out.printf("%-5d | %-15s | %-20s | %-20s | %-20s | %-10s\n",
                    booking.getId(),
                    pitchService.getPitchById(booking.getPitchId()).getName(),
                    customerService.getCustomerById(booking.getCustomerId()).getName(),
                    booking.getStartTime().format(dateFormatter),
                    booking.getEndTime().format(dateFormatter),
                    booking.getStatus());
        }
    }
    
    public int getBookingIdForCancellation() {
        System.out.print("Enter booking ID to cancel: ");
        try {
            return Integer.parseInt(scanner.nextLine());
        } catch (NumberFormatException e) {
            System.out.println("Invalid ID format. Please enter a number.");
            return getBookingIdForCancellation();
        }
    }
    
    public void displayCancellationSuccess() {
        System.out.println("\nBooking has been successfully canceled.");
    }
}