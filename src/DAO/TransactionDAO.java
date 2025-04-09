package DAO;

import model.Transaction;
import java.time.LocalDate;
import java.util.List;

public interface TransactionDAO extends GenericDAO<Transaction> {
    List<Transaction> findByType(String type);
    List<Transaction> findByCategory(String category);
    List<Transaction> findByDate(LocalDate date);
    List<Transaction> findByDateRange(LocalDate startDate, LocalDate endDate);
    List<Transaction> findByBranch(int branchId);
}