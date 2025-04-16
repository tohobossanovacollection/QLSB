package view;

import java.util.List;
import java.util.Scanner;
import model.Pitch;

public class PitchView {
    private Scanner scanner;
    
    public PitchView() {
        this.scanner = new Scanner(System.in);
    }
    
    public Pitch getPitchData() {
        Pitch pitch = new Pitch();
        
        System.out.println("\n===== ADD NEW PITCH =====");
        
        System.out.print("Enter pitch name: ");
        pitch.setName(scanner.nextLine());
        
        System.out.println("Available pitch types:");
        System.out.println("1. 5-a-side");
        System.out.println("2. 7-a-side");
        System.out.println("3. 11-a-side");
        
        int typeChoice = 0;
        while (typeChoice < 1 || typeChoice > 3) {
            System.out.print("Select pitch type (1-3): ");
            try {
                typeChoice = Integer.parseInt(scanner.nextLine());
            } catch (NumberFormatException e) {
                System.out.println("Invalid input. Please enter a number.");
            }
        }
        
        switch (typeChoice) {
            case 1:
                pitch.setType("5-a-side");
                break;
            case 2:
                pitch.setType("7-a-side");
                break;
            case 3:
                pitch.setType("11-a-side");
                break;
        }
        
        boolean validPrice = false;
        while (!validPrice) {
            System.out.print("Enter price per hour: ");
            try {
                double price = Double.parseDouble(scanner.nextLine());
                pitch.setPricePerHour(price);
                validPrice = true;
            } catch (NumberFormatException e) {
                System.out.println("Invalid price format. Please enter a number.");
            }
        }
        
        System.out.println("Pitch status:");
        System.out.println("1. Available");
        System.out.println("2. Under maintenance");
        
        int statusChoice = 0;
        while (statusChoice < 1 || statusChoice > 2) {
            System.out.print("Select status (1-2): ");
            try {
                statusChoice = Integer.parseInt(scanner.nextLine());
            } catch (NumberFormatException e) {
                System.out.println("Invalid input. Please enter a number.");
            }
        }
        
        pitch.setActive(statusChoice == 1 ? true : false);
        
        return pitch;
    }
    
    public void displayPitchList(List<Pitch> pitches) {
        if (pitches.isEmpty()) {
            System.out.println("\nNo pitches found.");
            return;
        }
        
        System.out.println("\n===== PITCH LIST =====");
        System.out.printf("%-5s | %-15s | %-10s | %-15s | %-15s\n", 
                "ID", "Name", "Type", "Price/Hour", "Status");
        System.out.println("----------------------------------------------------------");
        
        for (Pitch pitch : pitches) {
            System.out.printf("%-5d | %-15s | %-10s | %-15.2f | %-15s\n",
                    pitch.getId(),
                    pitch.getName(),
                    pitch.getType(),
                    pitch.getPricePerHour(),
                    pitch.isActive());
        }
    }
    
    public void displayPitchCreationSuccess(Pitch pitch) {
        System.out.println("\n===== PITCH CREATED SUCCESSFULLY =====");
        System.out.println("ID: " + pitch.getId());
        System.out.println("Name: " + pitch.getName());
        System.out.println("Type: " + pitch.getType());
        System.out.println("Price/Hour: " + pitch.getPricePerHour());
        System.out.println("Status: " + pitch.isActive());
    }
    
    public int getPitchIdForUpdate() {
        System.out.print("Enter pitch ID to update: ");
        try {
            return Integer.parseInt(scanner.nextLine());
        } catch (NumberFormatException e) {
            System.out.println("Invalid ID format. Please enter a number.");
            return getPitchIdForUpdate();
        }
    }
    
    public Pitch getUpdatedPitchData(Pitch existingPitch) {
        System.out.println("\n===== UPDATE PITCH =====");
        System.out.println("(Press Enter to keep current values)");
        
        System.out.print("Enter pitch name [" + existingPitch.getName() + "]: ");
        String name = scanner.nextLine();
        if (!name.isEmpty()) {
            existingPitch.setName(name);
        }
        
        System.out.println("Available pitch types:");
        System.out.println("1. 5-a-side");
        System.out.println("2. 7-a-side");
        System.out.println("3. 11-a-side");
        System.out.println("Current type: " + existingPitch.getType());
        
        System.out.print("Select pitch type (1-3) or press Enter to keep current: ");
        String typeChoice = scanner.nextLine();
        if (!typeChoice.isEmpty()) {
            try {
                int choice = Integer.parseInt(typeChoice);
                if (choice >= 1 && choice <= 3) {
                    switch (choice) {
                        case 1:
                            existingPitch.setType("5-a-side");
                            break;
                        case 2:
                            existingPitch.setType("7-a-side");
                            break;
                        case 3:
                            existingPitch.setType("11-a-side");
                            break;
                    }
                }
            } catch (NumberFormatException e) {
                // If invalid, keep current value
            }
        }
        
        System.out.print("Enter price per hour [" + existingPitch.getPricePerHour() + "]: ");
        String priceStr = scanner.nextLine();
        if (!priceStr.isEmpty()) {
            try {
                double price = Double.parseDouble(priceStr);
                existingPitch.setPricePerHour(price);
            } catch (NumberFormatException e) {
                // If invalid, keep current value
            }
        }
        
        System.out.println("Pitch status:");
        System.out.println("1. Available");
        System.out.println("2. Under maintenance");
        System.out.println("Current status: " + existingPitch.isActive());
        
        System.out.print("Select status (1-2) or press Enter to keep current: ");
        String statusChoice = scanner.nextLine();
        if (!statusChoice.isEmpty()) {
            try {
                int choice = Integer.parseInt(statusChoice);
                if (choice >= 1 && choice <= 2) {
                    existingPitch.setActive(choice == 1 ? true : false);
                }
            } catch (NumberFormatException e) {
                // If invalid, keep current value
            }
        }
        
        return existingPitch;
    }
    
    public void displayPitchUpdateSuccess(Pitch pitch) {
        System.out.println("\n===== PITCH UPDATED SUCCESSFULLY =====");
        System.out.println("ID: " + pitch.getId());
        System.out.println("Name: " + pitch.getName());
        System.out.println("Type: " + pitch.getType());
        System.out.println("Price/Hour: " + pitch.getPricePerHour());
        System.out.println("Status: " + pitch.isActive());
    }
    
    public int getPitchIdForDeletion() {
        System.out.print("Enter pitch ID to delete: ");
        try {
            return Integer.parseInt(scanner.nextLine());
        } catch (NumberFormatException e) {
            System.out.println("Invalid ID format. Please enter a number.");
            return getPitchIdForDeletion();
        }
    }
    
    public void displayPitchDeletionSuccess() {
        System.out.println("\nPitch has been successfully deleted.");
    }
    
    public void displayError(String errorMessage) {
        System.out.println("\nERROR: " + errorMessage);
    }
}