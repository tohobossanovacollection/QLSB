package service;

import DAO.InvoiceDAO;
import DAO.impl.InvoiceDAOImpl;
import model.Customer;
import model.Invoice;
//import model.InvoiceItem;
import model.Transaction;

import java.time.LocalDate;
import java.util.List;

public class InvoiceService {
    private InvoiceDAO invoiceDAO;
    private CustomerService customerService;
    private TransactionService transactionService;
    
    public InvoiceService() {
        this.invoiceDAO = new InvoiceDAOImpl();
        this.customerService = new CustomerService();
        this.transactionService = new TransactionService();
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
    
    public List<Invoice> getInvoicesByStatus(String status) {
        return invoiceDAO.findByStatus(status);
    }
    
    public List<Invoice> getInvoicesByType(String type) {
        return invoiceDAO.findByType(type);
    }
    
    public boolean createInvoice(Invoice invoice) {
        boolean saved = invoiceDAO.save(invoice);
        
        if (saved) {
            // Cập nhật công nợ khách hàng
            Customer customer = customerService.getCustomerById(invoice.getCustomerId());
            if (customer != null) {
                customer.addToDebt(invoice.getDebt());
                customer.addInvoice(invoice);
                customerService.updateCustomer(customer);
            }
            
            // Tạo giao dịch thu tương ứng
            if (invoice.getPaid() > 0) {
                Transaction transaction = new Transaction(
                    0, // ID sẽ được tạo trong DB
                    "INCOME",
                    invoice.getType(),
                    invoice.getPaid(),
                    "Payment for invoice #" + invoice.getId(),
                    invoice.getId(),
                    0 // Giả sử branch_id = 0 hoặc cần lấy từ context
                );
                transactionService.addTransaction(transaction);
            }
        }
        
        return saved;
    }
    
    public boolean updateInvoice(Invoice invoice) {
        Invoice oldInvoice = getInvoiceById(invoice.getId());
        boolean updated = invoiceDAO.update(invoice);
        
        if (updated && oldInvoice != null) {
            // Cập nhật công nợ khách hàng
            Customer customer = customerService.getCustomerById(invoice.getCustomerId());
            if (customer != null) {
                // Điều chỉnh công nợ dựa trên thay đổi
                double debtChange = invoice.getDebt() - oldInvoice.getDebt();
                if (debtChange != 0) {
                    customer.addToDebt(debtChange);
                    customerService.updateCustomer(customer);
                }
            }
            
            // Xử lý thay đổi thanh toán nếu cần
            double paymentChange = invoice.getPaid() - oldInvoice.getPaid();
            if (paymentChange > 0) {
                Transaction transaction = new Transaction(
                    0,
                    "INCOME",
                    invoice.getType(),
                    paymentChange,
                    "Additional payment for invoice #" + invoice.getId(),
                    invoice.getId(),
                    0
                );
                transactionService.addTransaction(transaction);
            }
        }
        
        return updated;
    }
    
    public boolean recordPayment(int invoiceId, double amount) {
        Invoice invoice = getInvoiceById(invoiceId);
        if (invoice != null) {
            // Cập nhật thông tin thanh toán
            invoice.addPayment(amount);
            boolean updated = invoiceDAO.update(invoice);
            
            if (updated) {
                // Cập nhật công nợ khách hàng
                Customer customer = customerService.getCustomerById(invoice.getCustomerId());
                if (customer != null) {
                    customer.payDebt(amount);
                    customerService.updateCustomer(customer);
                }
                
                // Tạo giao dịch thu
                Transaction transaction = new Transaction(
                    0,
                    "INCOME",
                    invoice.getType(),
                    amount,
                    "Payment for invoice #" + invoice.getId(),
                    invoice.getId(),
                    0
                );
                transactionService.addTransaction(transaction);
            }
            
            return updated;
        }
        
        return false;
    }
    
    public boolean deleteInvoice(int id) {
        // Có thể cần xử lý các tác động liên quan trước khi xóa
        return invoiceDAO.delete(id);
    }
}