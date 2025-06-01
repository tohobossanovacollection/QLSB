package model;

import java.time.LocalDateTime;

public class Transaction {
    private int id;
    private String type; // INCOME, EXPENSE
    private String category; // BOOKING, PRODUCT_SALE, SALARY, MAINTENANCE, ...
    private double amount;
    private String description;
    private int relatedId; // ID của invoice hoặc expense
    private int pitchId;

    public Transaction(int id, String type, String category, double amount, String description, 
                      int relatedId, int pitchId) {
        this.id = id;
        this.type = type;
        this.category = category;
        this.amount = amount;
        this.description = description;
        this.relatedId = relatedId;
        this.pitchId = pitchId;
    }

    // Getters và Setters
    public int getId() {
        return id;
    }

    public String getType() {
        return type;
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

    // public LocalDateTime getDate() {
    //     return date;
    // }

    // public void setDate(LocalDateTime date) {
    //     this.date = date;
    // }

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

    public int getpitchId() {
        return pitchId;
    }

    public void setpitchId(int pitchId) {
        this.pitchId = pitchId;
    }
}