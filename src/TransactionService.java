import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class TransactionService {
    private List<Transaction> transactions;

    public TransactionService() {
        transactions = new ArrayList<>();
    }

    public void addTransaction(Transaction transaction) {
        transactions.add(transaction);
    }

    public double calculateProfit(LocalDateTime date) {
        double income = 0, expense = 0;
        for (Transaction t : transactions) {
            if (t.getTransactionTime().toLocalDate().equals(date.toLocalDate())) {
                if (t.getType().equals("Thu")) income += t.getAmount();
                else if (t.getType().equals("Chi")) expense += t.getAmount();
            }
        }
        return income - expense;
    }
}