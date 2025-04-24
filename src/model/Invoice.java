package model;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class Invoice {
    private int id;
    private int customerId;
    private int PichId;
    //private int BranchId;
    private LocalDateTime createdAt;
    //private String type; // BOOKING, PRODUCT,
    private List<InvoiceItem> items;
    //private double subtotal;
    private double discount;
    private double total;
    //private double paid;
    //private double debt;
    private String status; // PAID, PARTIAL, UNPAID
    private String note;

    public Invoice(int id,int customerId, int pitchId, double discount, String note) {
        this.id = id;
        this.customerId = customerId;
        this.PichId = pitchId;
        this.createdAt = LocalDateTime.now();
        //this.type = type;
        this.items = new ArrayList<>();
        //this.subtotal = 0;
        this.discount = discount;
        this.total = 0;
        //this.paid = 0;
        //this.debt = 0;
        this.status = "UNPAID";
        this.note = note;
    }

    // Getters và Setters
    public int getId() {
        return id;
    }

    public int getPichId() {
        return PichId;
    }
    public int getCustomerId() {
        return customerId;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

  

    /* 
    public void setBranchId(int id){
        this.BranchId = id;
    }
    public int getBranchId(){
        return BranchId;
    }*/

    public List<InvoiceItem> getItems() {
        return items;
    }

    public void addItem(InvoiceItem item) {
        items.add(item);
        //calculateSubtotal();
    }

    public void removeItem(InvoiceItem item) {
        items.remove(item);
        //calculateSubtotal();
    }

    /*private void calculateSubtotal() {
        subtotal = 0;
        for (InvoiceItem item : items) {
            subtotal += item.getTotal();
        }
        calculateTotal();
    }*/


    /*public double getSubtotal() {
        return subtotal;
    }*/

    public double getDiscount() {
        return discount;
    }

    public void setDiscount(double discount) {
        this.discount = discount;
        //calculateTotal();
    }
    //TODO : rewrite this
    /*private void calculateTotal() {
        total = subtotal - discount;
        this.debt = total - paid;
        updateStatus();
    }*/

    public double getTotal() {
        return total;
    }

    /*public double getPaid() {
        return paid;
    }*/

    /*public void addPayment(double amount) {
        this.paid += amount;
        this.debt = total - paid;
        updateStatus();
    }*/

    /*public double getDebt() {
        return debt;
    }*/

    /*private void updateStatus() {
        if (debt <= 0) {
            status = "PAID";
        } else if (paid > 0) {
            status = "PARTIAL";
        } else {
            status = "UNPAID";
        }
    }*/

    public String getStatus() {
        return status;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }
}