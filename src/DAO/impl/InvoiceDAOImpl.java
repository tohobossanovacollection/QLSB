package DAO.impl;

import java.time.LocalDate;
import java.util.List;

import DAO.InvoiceDAO;
import model.Invoice;
public class InvoiceDAOImpl implements InvoiceDAO {
    @Override
    public Invoice findById(int id) {
        // Implementation code here
        return null;
    }

    @Override
    public List<Invoice> findAll() {
        // Implementation code here
        return null;
    }

    @Override
    public boolean save(Invoice entity) {
        // Implementation code here
        return false;
    }

    @Override
    public boolean update(Invoice entity) {
        // Implementation code here
        return false;
    }

    @Override
    public boolean delete(int id) {
        // Implementation code here
        return false;
    }

    @Override
    public List<Invoice> findByCustomer(int customerId) {
        // Implementation code here
        return null;
    }

    @Override
    public List<Invoice> findByDate(LocalDate date) {
        // Implementation code here
        return null;
    }

    @Override
    public List<Invoice> findByDateRange(LocalDate startDate, LocalDate endDate) {
        // Implementation code here
        return null;
    }

    @Override
    public List<Invoice> findByStatus(String status) {
        // Implementation code here
        return null;
    }

    @Override
    public List<Invoice> findByType(String type) {
        // Implementation code here
        return null;
    }
}
