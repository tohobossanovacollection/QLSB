package service;

import DAO.TransactionDAO;
import DAO.impl.TransactionDAOImpl;
import model.Transaction;

import java.time.LocalDate;
import java.util.List;

public class TransactionService {
    private TransactionDAO transactionDAO;
    
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
    
    public boolean addTransaction(Transaction transaction) {
        return transactionDAO.save(transaction);
    }
    
    public boolean updateTransaction(Transaction transaction) {
        return transactionDAO.update(transaction);
    }
    
    public boolean deleteTransaction(int id) {
        return transactionDAO.delete(id);
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