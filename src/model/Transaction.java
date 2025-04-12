package model;

import java.time.LocalDateTime;

public class Transaction {
    
    private String type; // INCOME, EXPENSE
    private String category; // BOOKING, PRODUCT_SALE, SALARY, MAINTENANCE, ...
    private double amount;
    private LocalDateTime date;
    private String description;
    private int relatedId; // ID của invoice hoặc expense
    private int branchId;

    public Transaction(double amount, 
                      int relatedId, int branchId) {
        
        this.type = "INCOME"; 
        this.amount = amount;
        this.date = LocalDateTime.now();
        this.description = "lOREM IPSUM";
        this.relatedId = relatedId;
        this.branchId = branchId;
    }

    // Getters và Setters
    public int getId() {
        return relatedId;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public LocalDateTime getDate() {
        return date;
    }

    public void setDate(LocalDateTime date) {
        this.date = date;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getRelatedId() {
        return relatedId;
    }

    public void setRelatedId(int relatedId) {
        this.relatedId = relatedId;
    }

    public int getBranchId() {
        return branchId;
    }

    public void setBranchId(int branchId) {
        this.branchId = branchId;
    }
}