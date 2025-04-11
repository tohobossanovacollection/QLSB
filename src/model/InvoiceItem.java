package model;

public class InvoiceItem {
    private int id;// invoice id
    
    private String itemType; // BOOKING, PRODUCT
    private int itemId; // ID của booking hoặc product
    private String description;
    private double unitPrice;
    private int quantity;
    private double total;

    public InvoiceItem(int invoiceId, String itemType, int itemId, String description, 
                     double unitPrice, int quantity) {
        
        
        this.id = invoiceId;
        this.itemType = itemType;
        this.itemId = itemId;
        this.description = description;
        this.unitPrice = unitPrice;
        this.quantity = quantity;
        this.total = unitPrice * quantity;
    }

    // Getters và Setters
    public int getId() {
        return id;
    }

    public String getItemType() {
        return itemType;
    }

    public int getItemId() {
        return itemId;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public double getUnitPrice() {
        return unitPrice;
    }

    public void setUnitPrice(double unitPrice) {
        this.unitPrice = unitPrice;
        this.total = unitPrice * quantity;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
        this.total = unitPrice * quantity;
    }

    public double getTotal() {
        return total;
    }
}