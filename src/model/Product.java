package model;

public class Product {
    private int id;
    private String name;
    private String category;
    private double buyPrice;
    private double sellPrice;
    private int currentStock;
    private int minStockLevel;
    private String unit; // cái, chai, lon, ...
    private String description;
    private boolean active;

    public Product(int id, String name, String category, double buyPrice, double sellPrice, 
                 int currentStock, int minStockLevel, String unit, String description) {
        this.id = id;
        this.name = name;
        this.category = category;
        this.buyPrice = buyPrice;
        this.sellPrice = sellPrice;
        this.currentStock = currentStock;
        this.minStockLevel = minStockLevel;
        this.unit = unit;
        this.description = description;
        this.active = true;
    }

    public Product(){
        this.id = 0;
        this.name = "";
        this.category = "";
        this.buyPrice = 0.0;
        this.sellPrice = 0.0;
        this.currentStock = 0;
        this.minStockLevel = 0;
        this.unit = "";
        this.description = "";
        this.active = true;
    }

    // Getters và Setters
    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public double getBuyPrice() {
        return buyPrice;
    }

    public void setBuyPrice(double buyPrice) {
        this.buyPrice = buyPrice;
    }

    public double getSellPrice() {
        return sellPrice;
    }

    public void setSellPrice(double sellPrice) {
        this.sellPrice = sellPrice;
    }

    public int getCurrentStock() {
        return currentStock;
    }

    public void updateStock(int quantity) {
        this.currentStock += quantity;
    }

    public int getMinStockLevel() {
        return minStockLevel;
    }

    public void setMinStockLevel(int minStockLevel) {
        this.minStockLevel = minStockLevel;
    }

    public String getUnit() {
        return unit;
    }

    public void setUnit(String unit) {
        this.unit = unit;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }

    public boolean isLowStock() {
        return currentStock <= minStockLevel;
    }

    @Override
    public String toString() {
        return name + " (" + currentStock + " " + unit + ")";
    }
}