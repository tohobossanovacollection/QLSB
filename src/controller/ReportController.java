package controller;

import model.Transaction;
import service.PitchService;
import service.TransactionService;
import view.TransactionListView;

public class ReportController {
    private TransactionListView transactionListView;
    private TransactionService transactionService = new TransactionService();
    private PitchService pitchService = new PitchService();
    public ReportController(TransactionListView transactionListView) {
        // Initialize the controller with the view
        this.transactionListView = transactionListView;
    }

    public void LoadTransactionListData(int flag){
        switch (flag) {
            case 0: // Load all transactions
                transactionListView.loadDataToTable(transactionService.getAllTransactions());
                break;
            case 1:
                transactionListView.loadDataToTable(transactionService.getTransactionsByCategory("BOOKING"));
                break;
            case 2:
                transactionListView.loadDataToTable(transactionService.getTransactionsByCategory("PRODUCT_SALE"));
                break;
            case 3:
                transactionListView.loadDataToTable(transactionService.getTransactionsByType("EXPENSE"));
                break;    
            default:
                break;
        }
    }

    public void initdialog() {
        transactionListView.initdialog(pitchService.getAllPitches());
    }

    public boolean processSaveExpense() {
        Transaction transaction = transactionListView.getNewTransaction();
        System.out.println(transaction);
        if (transaction == null) {
            transactionListView.showMessage("Vui lòng nhập đầy đủ thông tin giao dịch!");
            return false;
        }
        if (transactionService.addTransaction(transaction)) {
            transactionListView.showMessage("Lưu giao dịch thành công!");
            LoadTransactionListData(3); // Refresh the expense list
            return true;
        } else {
            transactionListView.showMessage("Lưu giao dịch thất bại!");
            return false;
        }
    }
    public boolean processDeleteExpense(int id) {
        if (transactionService.deleteTransaction(id)) {
            transactionListView.showMessage("Xóa giao dịch thành công!");
            LoadTransactionListData(3);
            return true;
             // Refresh the expense list
        } else {
            transactionListView.showMessage("Xóa giao dịch thất bại!");
            return false;
        }
    }
}
