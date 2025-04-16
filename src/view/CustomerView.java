package view;

import java.util.List;
import java.util.Scanner;
import model.Customer;

public class CustomerView {
    private Scanner scanner;
    
    public CustomerView() {
        this.scanner = new Scanner(System.in);
    }
    
    public Customer getCustomerData() {
        Customer customer = new Customer();
        
        System.out.println("\n===== ADD NEW CUSTOMER =====");
        
        System.out.print("Enter customer name: ");
        customer.setName(scanner.nextLine());
        
        System.out.print("Enter phone number: ");
        customer.setPhone(scanner.nextLine());
        
        System.out.print("Enter email (optional): ");
        String email = scanner.nextLine();
        if (!email.isEmpty()) {
            customer.setEmail(email);
        }
        
        System.out.println("Customer type:");
        System.out.println("1. Regular");
        System.out.println("2. VIP");
        System.out.println("3. Corporate");
        
        int typeChoice = 0;
        while (typeChoice < 1 || typeChoice > 3) {
            System.out.print("Select customer type (1-3): ");
            try {
                typeChoice = Integer.parseInt(scanner.nextLine());
            } catch (NumberFormatException e) {
                System.out.println("Invalid input. Please enter a number.");
            }
        }
        
        switch (typeChoice) {
            case 1:
                customer.setCustomerType("REGULAR");
                break;
            case 2:
                customer.setCustomerType("VIP");
                break;
            case 3:
                customer.setCustomerType("CORPORATE");
                break;
        }
        
        
        
        return customer;
    }
    
    public void displayCustomerList(List<Customer> customers) {
        if (customers.isEmpty()) {
            System.out.println("\nNo customers found.");
            return;
        }
        
        System.out.println("\n===== CUSTOMER LIST =====");
        System.out.printf("%-5s | %-20s | %-15s | %-25s | %-10s\n", 
                "ID", "Name", "Phone", "Email", "Type");
        System.out.println("---------------------------------------------------------------------------------");
        
        for (Customer customer : customers) {
            System.out.printf("%-5d | %-20s | %-15s | %-25s | %-10s\n",
                    customer.getId(),
                    customer.getName(),
                    customer.getPhone(),
                    customer.getEmail() != null ? customer.getEmail() : "",
                    customer.getCustomerType());
        }
    }
    
    public void displayCustomerDetails(Customer customer) {
        System.out.println("\n===== CUSTOMER DETAILS =====");
        System.out.println("ID: " + customer.getId());
        System.out.println("Name: " + customer.getName());
        System.out.println("Phone: " + customer.getPhone());
        System.out.println("Email: " + (customer.getEmail() != null ? customer.getEmail() : "N/A"));
        System.out.println("Type: " + customer.getCustomerType());
        //System.out.println("Notes: " + (customer.getNotes() != null ? customer.getNotes() : "N/A"));
    }
    
    public void displayCustomerCreationSuccess(Customer customer) {
        System.out.println("\n===== CUSTOMER CREATED SUCCESSFULLY =====");
        System.out.println("ID: " + customer.getId());
        System.out.println("Name: " + customer.getName());
        System.out.println("Phone: " + customer.getPhone());
    }
    
    public int getCustomerIdForUpdate() {
        System.out.print("Enter customer ID to update: ");
        try {
            return Integer.parseInt(scanner.nextLine());
        } catch (NumberFormatException e) {
            System.out.println("Invalid ID format. Please enter a number.");
            return getCustomerIdForUpdate();
        }
    }
    
    public Customer getUpdatedCustomerData(Customer existingCustomer) {
        System.out.println("\n===== UPDATE CUSTOMER =====");
        System.out.println("(Press Enter to keep current values)");
        
        System.out.print("Enter customer name [" + existingCustomer.getName() + "]: ");
        String name = scanner.nextLine();
        if (!name.isEmpty()) {
            existingCustomer.setName(name);
        }
        
        System.out.print("Enter phone number [" + existingCustomer.getPhone() + "]: ");
        String phone = scanner.nextLine();
        if (!phone.isEmpty()) {
            existingCustomer.setPhone(phone);
        }
        
        System.out.print("Enter email [" + (existingCustomer.getEmail() != null ? existingCustomer.getEmail() : "") + "]: ");
        String email = scanner.nextLine();
        if (!email.isEmpty()) {
            existingCustomer.setEmail(email);
        }
        
        System.out.println("Customer type:");
        System.out.println("1. Regular");
        System.out.println("2. VIP");
        System.out.println("3. Corporate");
        System.out.println("Current type: " + existingCustomer.getCustomerType());
        
        System.out.print("Select customer type (1-3) or press Enter to keep current: ");
        String typeChoice = scanner.nextLine();
        if (!typeChoice.isEmpty()) {
            try {
                int choice = Integer.parseInt(typeChoice);
                if (choice >= 1 && choice <= 3) {
                    switch (choice) {
                        case 1:
                            existingCustomer.setCustomerType("REGULAR");
                            break;
                        case 2:
                            existingCustomer.setCustomerType("VIP");
                            break;
                        case 3:
                            existingCustomer.setCustomerType("CORPORATE");
                            break;
                    }
                }
            } catch (NumberFormatException e) {
                // If invalid, keep current value
            }
        }
        /* 
        System.out.print("Enter notes [" + (existingCustomer.getNotes() != null ? existingCustomer.getNotes() : "") + "]: ");
        String notes = scanner.nextLine();
        if (!notes.isEmpty()) {
            existingCustomer.setNotes(notes);
        }
        */
        return existingCustomer;
    }
    
    public void displayCustomerUpdateSuccess(Customer customer) {
        System.out.println("\n===== CUSTOMER UPDATED SUCCESSFULLY =====");
        System.out.println("ID: " + customer.getId());
        System.out.println("Name: " + customer.getName());
        System.out.println("Phone: " + customer.getPhone());
        System.out.println("Type: " + customer.getCustomerType());
    }
    
    public String getPhoneForSearch() {
        System.out.print("Enter phone number to search: ");
        return scanner.nextLine();
    }
    
    public void displayCustomerNotFound() {
        System.out.println("\nNo customer found with the given phone number.");
    }
    
    public Customer selectCustomer(List<Customer> customers) {
        if (customers.isEmpty()) {
            System.out.println("\nNo customers available.");
            return null;
        }
        
        displayCustomerList(customers);
        
        int customerChoice = -1;
        while (customerChoice < 0 || customerChoice >= customers.size()) {
            System.out.print("Select customer ID: ");
            try {
                customerChoice = Integer.parseInt(scanner.nextLine());
                
                // Find customer with matching ID
                for (Customer customer : customers) {
                    if (customer.getId() == customerChoice) {
                        return customer;
                    }
                }
                
                System.out.println("Invalid customer ID. Please try again.");
                customerChoice = -1;
            } catch (NumberFormatException e) {
                System.out.println("Invalid input. Please enter a number.");
            }
        }
        
        return null;
    }
    
    public void displayError(String errorMessage) {
        System.out.println("\nERROR: " + errorMessage);
    }
}