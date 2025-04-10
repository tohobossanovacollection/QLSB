package DAO.impl;

import java.time.LocalDate;
import java.util.List;

import DAO.TransactionDAO;
import model.Transaction;
public class TransactionDAOImpl implements TransactionDAO {
    @Override
    public Transaction findById(int id) {
        // Implementation code here
        return null;
    }

    @Override
    public List<Transaction> findAll() {
        // Implementation code here
        return null;
    }

    @Override
    public boolean save(Transaction entity) {
        // Implementation code here
        return false;
    }

    @Override
    public boolean update(Transaction entity) {
        // Implementation code here
        return false;
    }

    @Override
    public boolean delete(int id) {
        // Implementation code here
        return false;
    }
    
    @Override
    public List<Transaction> findByType(String type) {
        // Implementation code here
        return null;
    }

    @Override
    public List<Transaction> findByCategory(String category) {
        // Implementation code here
        return null;
    }

    @Override
    public List<Transaction> findByDate(LocalDate date) {
        // Implementation code here
        return null;
    }

    @Override
    public List<Transaction> findByDateRange(LocalDate startDate, LocalDate endDate) {
        // Implementation code here
        return null;
    }

    @Override
    public List<Transaction> findByBranch(int branchId) {
        // Implementation code here
        return null;
    }
}
