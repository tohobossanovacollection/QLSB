package service;

import DAO.InvoiceDAO;
import DAO.impl.InvoiceDAOImpl;
import model.Invoice;

import java.time.LocalDate;
import java.util.List;

public class InvoiceService {
    private InvoiceDAO invoiceDAO;
    
    public InvoiceService() {
        this.invoiceDAO = new InvoiceDAOImpl();
    }
    
    public Invoice getInvoiceById(int id) {
        return invoiceDAO.findById(id);
    }
    
    public List<Invoice> getAllInvoices() {
        return invoiceDAO.findAll();
    }
    
    public List<Invoice> getInvoicesByCustomer(int customerId) {
        return invoiceDAO.findByCustomer(customerId);
    }
    
    public List<Invoice> getInvoicesByDate(LocalDate date) {
        return invoiceDAO.findByDate(date);
    }
    
    public List<Invoice> getInvoicesByDateRange(LocalDate startDate, LocalDate endDate) {
        return invoiceDAO.findByDateRange(startDate, endDate);
    }
    
    public List<Invoice> getInvoicesByType(String type) {
        return invoiceDAO.findByType(type);
    }

    public List<Invoice> getInvoicesByPitch(int pitchId){
        return invoiceDAO.findByPitch(pitchId);
    }
    
    public boolean deleteInvoice(int id) {
        // Có thể cần xử lý các tác động liên quan trước khi xóa
        return invoiceDAO.delete(id);
    }
}