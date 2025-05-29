package service;

import DAO.TransactionDAO;
import DAO.impl.TransactionDAOImpl;
import DAO.impl.InvoiceDAOImpl;
import DAO.InvoiceDAO;
import DAO.BranchDAO;
import DAO.impl.BranchDAOImpl;
import model.Invoice;
import model.Transaction;
import model.Booking;
import model.Branch;

import java.time.LocalDate;
import java.util.List;

public class TransactionService {
    private BranchService branchService = new BranchService();
    private TransactionDAO transactionDAO;
    private InvoiceDAO invoiceDAO;
    private BranchDAO branchDAO;
    public TransactionService() {
        this.transactionDAO = new TransactionDAOImpl();
    }
    
    public Transaction getTransactionById(int id) {
        return transactionDAO.findById(id);
    }
    
    public List<Transaction> getAllTransactions() {
        return transactionDAO.findAll();
    }
    
    public List<Transaction> getTransactionsByType(String type) {
        return transactionDAO.findByType(type);
    }
    
    public List<Transaction> getTransactionsByCategory(String category) {
        return transactionDAO.findByCategory(category);
    }
    
    public List<Transaction> getTransactionsByDate(LocalDate date) {
        return transactionDAO.findByDate(date);
    }
    
    public List<Transaction> getTransactionsByDateRange(LocalDate startDate, LocalDate endDate) {
        return transactionDAO.findByDateRange(startDate, endDate);
    }
    
    public List<Transaction> getTransactionsByBranch(int branchId) {
        return transactionDAO.findByBranch(branchId);
    }
    
    public boolean addTransaction(Object object) { 
        if(object instanceof Booking){
            Booking booking = (Booking) object;
            Branch branch = branchService.findByPitch(booking.getPitchId());
            Transaction transaction = new Transaction(booking.getId(), "INCOME", "BOOKING", 
            booking.getTotalPrice(), booking.getNote(), booking.getId(), branch.getId());
            return transactionDAO.save(transaction);
        }
        if(object instanceof Invoice){
            Invoice invoice = (Invoice) object;
            Branch branch = branchDAO.findByPitch(invoice.getPichId());
            Transaction transaction = new Transaction(invoice.getId(), "INCOME", "PRODUCT_SALE", 
            invoice.getTotal(), invoice.getNote(), invoice.getId(), branch.getId());
            return transactionDAO.save(transaction);
        }
        return false;
    }
    //FIXME : edit and delete
    public boolean updateTransaction(Object object) {
        if(object instanceof Booking){
            Booking booking = (Booking) object;
            Branch branch = branchService.findByPitch(booking.getPitchId());
            Transaction transaction = new Transaction(booking.getId(), "INCOME", "BOOKING", 
            booking.getTotalPrice(), booking.getNote(), booking.getId(), branch.getId());
            return transactionDAO.update(transaction);
        }
        if(object instanceof Invoice){
            Invoice invoice = (Invoice) object;
            Branch branch = branchDAO.findByPitch(invoice.getPichId());
            Transaction transaction = new Transaction(invoice.getId(), "INCOME", "PRODUCT_SALE", 
            invoice.getTotal(), invoice.getNote(), invoice.getId(), branch.getId());
            return transactionDAO.update(transaction);
        }
        return false;
    }

    public boolean deleteTransaction(Object object) {
        if(object instanceof Booking){
            Booking booking = (Booking) object;
            return transactionDAO.delete(booking.getId());
        }
        if(object instanceof Invoice){
            Invoice invoice = (Invoice) object;
            return transactionDAO.delete(invoice.getId());
        }
        return false;
    }
    
    public boolean createExpenseTransaction(int transactionId, String type, String category, double amount, String description, int branchId) {
        Transaction transaction = new Transaction(transactionId, type, category, amount, description, 0, branchId);
        return addTransaction(transaction);
    }
    //TODO fix it 
    public boolean createTransactionbyinvoice(Invoice invoice,int transactionId,String type, String category,String description)
    {
        Branch branch = branchDAO.findByPitch(invoice.getPichId());
        Transaction transaction = new Transaction(transactionId, type, category,12,description,invoice.getId(), branch.getId());
        return addTransaction(transaction);
    }
    // Tính tổng thu trong khoảng thời gian
    // + tinh tong thu tron ngay
    public double calculateTotalIncome(LocalDate startDate, LocalDate endDate) {
        List<Transaction> transactions = getTransactionsByDateRange(startDate, endDate);
        double total = 0;
        
        for (Transaction transaction : transactions) {
            if ("INCOME".equals(transaction.getType())) {
                total += transaction.getAmount();
            }

        }
        
        return total;
    }
    public double calculateTotalIncome(LocalDate date) {
        List<Transaction> transactions = getTransactionsByDate(date);
        double total = 0;
        
        for (Transaction transaction : transactions) {
            if ("INCOME".equals(transaction.getType())) {
                total += transaction.getAmount();
            }
        }
        
        return total;
    }

    // Tính tổng chi trong khoảng thời gian
    public double calculateTotalExpense(LocalDate startDate, LocalDate endDate) {
        List<Transaction> transactions = getTransactionsByDateRange(startDate, endDate);
        double total = 0;
        
        for (Transaction transaction : transactions) {
            if ("EXPENSE".equals(transaction.getType())) {
                total += transaction.getAmount();
            }
        }
        
        return total;
    }
    
    // Tính lợi nhuận trong khoảng thời gian
    public double calculateProfit(LocalDate startDate, LocalDate endDate) {
        double income = calculateTotalIncome(startDate, endDate);
        double expense = calculateTotalExpense(startDate, endDate);
        return income - expense;
    }
}