package DAO;

import model.Invoice;
import java.time.LocalDate;
import java.util.List;

public interface InvoiceDAO extends GenericDAO<Invoice> {
    List<Invoice> findByCustomer(int customerId);
    List<Invoice> findByDate(LocalDate date);
    List<Invoice> findByDateRange(LocalDate startDate, LocalDate endDate);
    List<Invoice> findByStatus(String status);
    List<Invoice> findByType(String type);
}
