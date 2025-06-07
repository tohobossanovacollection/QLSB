package service;
// : khi xóa 1 booking đi chẳng hạn , sẽ phải cần trả customer spent về lại ban đầu
// : write test cases for this
// : viết phần add transaction sau mỗi lần thêm bookings,invoice
// : viết method đặt các booking sau khi tới hạn từ pending -> complete,
//: với bookings bi huy( pending thì sẽ chuyển thành cancel
//: add on delete cascade cho transaction(code hoac database(can sua lai ca cai bang)) 

//Cân nhắc xóa cột status ở booking đi tuy thế transision sẽ phải thêm 1 cột là booking
//work flow có thể : xóa booking -> xóa transaction, thêm bookings hoặc invoice -> thêm transaction
//relate_id ở tranasaction sẽ chỉ của booking thôi

//maybe bỏ status trong invoice đi, và có thể làm invoice list như 1 dạng tuowgn tác read_only

//TLDR TODO : bỏ status booking, invoice, thay thay tên cột Branch_id thành pitch_id  thêm tương tác transaction vs bookking thêm và xóa , thêm trả lại customer spent khi hủy booking

import model.Booking;
import model.Invoice;
import model.InvoiceItem;
import model.Product;
import model.Transaction;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.YearMonth;
import java.util.*;
import java.util.stream.Collectors;

public class ReportService {

    private  BookingService bookingService;
    private  InvoiceService invoiceService;
    private  TransactionService transactionService;
    private  ProductService inventoryService;
    private InvoiceItemService invoiceItemService;

    public ReportService() {
        this.bookingService = new BookingService();
        this.invoiceService = new InvoiceService();
        this.transactionService = new TransactionService();
        this.inventoryService = new ProductService();
        this.invoiceItemService = new InvoiceItemService();
    }

    public Map<String, Double> getDailyRevenueReport(LocalDate date) {
        Map<String, Double> revenueReport = new HashMap<>();

        List<Invoice> dailyInvoices = invoiceService.getInvoicesByDate(date);
        List<Booking> dailyBookings = bookingService.getBookingsByDate(date);
        double PitchRevenue = dailyBookings.stream()
                //.filter(booking -> booking.getPitchId() == pitchId)
                .mapToDouble(Booking::getTotalPrice)
                .sum();

        double salesRevenue = dailyInvoices.stream()
                //.filter(invoice -> invoice.getPichId() == pitchId)
                .mapToDouble(Invoice::getTotal)
                .sum();

        List<Transaction> dailyTransactions = transactionService.getTransactionsByDate(date);
        double totalExpenses = dailyTransactions.stream()
                .filter(transaction -> "EXPENSE".equals(transaction.getType()))
                .mapToDouble(Transaction::getAmount)
                .sum();

        revenueReport.put("PitchBookings", PitchRevenue);
        revenueReport.put("productSales", salesRevenue);
        revenueReport.put("totalRevenue", PitchRevenue + salesRevenue);
        revenueReport.put("expenses", totalExpenses);
        revenueReport.put("netProfit", PitchRevenue + salesRevenue - totalExpenses);

        return revenueReport;
    }

    public Map<LocalDate, Map<String, Double>> getMonthlyRevenueReport(int year, int month) {
        Map<LocalDate, Map<String, Double>> monthlyReport = new TreeMap<>();

        YearMonth yearMonth = YearMonth.of(year, month);
        LocalDate startDate = yearMonth.atDay(1);
        LocalDate endDate = yearMonth.atEndOfMonth();

        LocalDate currentDate = startDate;
        while (!currentDate.isAfter(endDate)) {
            Map<String, Double> dailyReport = getDailyRevenueReport(currentDate);
            monthlyReport.put(currentDate, dailyReport);
            currentDate = currentDate.plusDays(1);
        }
        return monthlyReport;
    }

    public Map<Integer, Double> getRevenueByPitch(int pitchId) {
        Map<Integer, Double> PitchRevenue = new HashMap<>();

        List<Invoice> invoices = invoiceService.getInvoicesByPitch(pitchId);
        List<Booking> bookings = bookingService.getBookingsByPitch(pitchId);

        double bookingRevenue = bookings.stream()
        .mapToDouble(Booking::getTotalPrice).sum();

        double saleRevenue = invoices.stream()
        .mapToDouble(Invoice::getTotal).sum();
        System.out.println("b : " + bookingRevenue);
        System.out.println("s : " + saleRevenue);
        PitchRevenue.put(pitchId, bookingRevenue + saleRevenue);
        return PitchRevenue;
    }

    public Map<Integer, Integer> getPeakHoursAnalysis(LocalDate startDate, LocalDate endDate) {
        Map<Integer, Integer> hourlyBookings = new HashMap<>();

        for (int i = 0; i < 24; i++) {
            hourlyBookings.put(i, 0);
        }

        List<Booking> bookings = bookingService.getBookingsByDateRange(startDate, endDate);

        for (Booking booking : bookings) {
            int hour = booking.getStartTime().getHour();
            hourlyBookings.put(hour, hourlyBookings.get(hour) + 1);
        }
        

        return hourlyBookings;
    }

    public Map<Integer, Integer> getTopCustomersByBookingFrequency(int limit) {
        Map<Integer, Integer> customerFrequency = new HashMap<>();

        // List<Invoice> invoices = invoiceService.getInvoicesByDateRange(startDate, endDate);
        // List<Invoice> bookingInvoices = invoices.stream()
        //         .filter(invoice -> "BOOKING".equals(invoice.getType()))
        //         .collect(Collectors.toList());

        // for (Invoice invoice : bookingInvoices) {
        //     int customerId = invoice.getCustomerId();
        //     customerFrequency.put(customerId, customerFrequency.getOrDefault(customerId, 0) + 1);
        // }
        List<Booking> bookings = bookingService.getAllBookings();
        for(Booking booking : bookings){
            int customerId = booking.getCustomerId();
            customerFrequency.put(customerId,customerFrequency.getOrDefault(customerId, 0) + 1);
        }

        return customerFrequency.entrySet().stream()
                .sorted(Map.Entry.<Integer, Integer>comparingByValue().reversed())
                .limit(limit)
                .collect(Collectors.toMap(
                        Map.Entry::getKey,
                        Map.Entry::getValue,
                        (e1, e2) -> e1,
                        LinkedHashMap::new
                ));
    }

    public Map<Integer, Integer> getTopSellingProducts(LocalDate startDate, LocalDate endDate, int limit) {
        Map<Integer, Integer> productQuantities = new HashMap<>();

        //List<Invoice> invoices = invoiceService.getInvoicesByDateRange(startDate, endDate);
        List<InvoiceItem> items = invoiceItemService.getAllInvoiceItems();
        // List<Invoice> salesInvoices = invoices.stream()
        //         .filter(invoice -> "SALES".equals(invoice.getType()))
        //         .collect(Collectors.toList());

        for (InvoiceItem item : items) {
            
            int productId = item.getItemId();
            int quantity = item.getQuantity();
            productQuantities.put(productId, productQuantities.getOrDefault(productId, 0) + quantity);
            
        }

        return productQuantities.entrySet().stream()
                .sorted(Map.Entry.<Integer, Integer>comparingByValue().reversed())
                .limit(limit)
                .collect(Collectors.toMap(
                        Map.Entry::getKey,
                        Map.Entry::getValue,
                        (e1, e2) -> e1,
                        LinkedHashMap::new
                ));
    }

    public Map<Integer, Map<String, Object>> getInventorySalesEfficiencyReport(LocalDate startDate, LocalDate endDate) {
        // Get top selling products
        Map<Integer, Integer> productSales = getTopSellingProducts(startDate, endDate, Integer.MAX_VALUE);
        
        // Get current inventory levels
        List<Product> products = inventoryService.getAllProducts();
        
        Map<Integer, Map<String, Object>> efficiencyReport = new HashMap<>();
        
        for (Product product : products) {
            int productId = product.getId();
            int quantitySold = productSales.getOrDefault(productId, 0);
            int currentStock = product.getCurrentStock();
            double turnoverRate = currentStock > 0 ? (double) quantitySold / currentStock : 0;
            
            Map<String, Object> metrics = new HashMap<>();
            metrics.put("name", product.getName());
            metrics.put("quantitySold", quantitySold);
            metrics.put("currentStock", currentStock);
            metrics.put("turnoverRate", turnoverRate);
            metrics.put("revenue", quantitySold * product.getSellPrice());
            
            efficiencyReport.put(productId, metrics);
        }
        
        return efficiencyReport;
    }

    public Map<String, Double> getProfitAndLossStatement(LocalDate startDate, LocalDate endDate) {
        double totalIncome = transactionService.calculateTotalIncome(startDate, endDate);
        double totalExpense = transactionService.calculateTotalExpense(startDate, endDate);
        double netProfit = transactionService.calculateProfit(startDate, endDate);

        Map<String, Double> plStatement = new HashMap<>();
        plStatement.put("totalIncome", totalIncome);
        plStatement.put("totalExpenses", totalExpense);
        plStatement.put("netProfit", netProfit);

        return plStatement;
    }

    public double getFieldOccupancyRate(int fieldId, LocalDateTime startDate, LocalDateTime endDate, int hourStart, int hourEnd) {
        // Get all bookings for this field in the date range
        // Note: This assumes BookingService has a method to get bookings by field and date range
        List<Booking> fieldBookings = bookingService.getBookingsByPitchAndDateRange(fieldId, startDate, endDate);
        
        // Calculate total operational hours in the date range
        long days = endDate.toLocalDate().toEpochDay() - startDate.toLocalDate().toEpochDay() + 1;
        long totalOperationalMinutes = days * (hourEnd - hourStart) * 60;
        
        // Calculate booked minutes
        long bookedMinutes = 0;
        for (Booking booking : fieldBookings) {
            LocalDateTime bookingStart = booking.getStartTime();
            LocalDateTime bookingEnd = booking.getEndTime();
            
            // Skip bookings outside operational hours
            if (bookingStart.getHour() >= hourEnd || bookingEnd.getHour() <= hourStart) {
                continue;
            }
            
            // Adjust booking times to operational hours if needed
            if (bookingStart.getHour() < hourStart) {
                bookingStart = bookingStart.withHour(hourStart).withMinute(0);
            }
            if (bookingEnd.getHour() > hourEnd) {
                bookingEnd = bookingEnd.withHour(hourEnd).withMinute(0);
            }
            
            // Calculate minutes for this booking
            long bookingMinutes = (bookingEnd.getHour() * 60 + bookingEnd.getMinute()) - 
                                 (bookingStart.getHour() * 60 + bookingStart.getMinute());
            bookedMinutes += bookingMinutes;
        }
        
        // Calculate occupancy rate
        return (double) bookedMinutes / totalOperationalMinutes * 100;
    }

    public Map<Integer, Map<String, Object>> getBranchPerformanceComparison(LocalDate startDate, LocalDate endDate) {
        Map<Integer, Map<String, Object>> branchPerformance = new HashMap<>();
        
        // Get transactions by branch
        //List<Transaction> transactions = transactionService.getTransactionsByDateRange(startDate, endDate);
        
        // Group by branch and calculate metrics
        Map<Integer, List<Transaction>> transactionsByBranch = new HashMap<>();

        //FIXME: remake find transaction by branch
        // for (Transaction transaction : transactions) {
        //     //int branchId = transaction.getBranchId(); // Assuming this field exists
        //     if (!transactionsByBranch.containsKey(branchId)) {
        //         transactionsByBranch.put(branchId, new ArrayList<>());
        //     }

        //     transactionsByBranch.get(branchId).add(transaction);
        // }
        
        // Calculate performance metrics for each branch
        for (Map.Entry<Integer, List<Transaction>> entry : transactionsByBranch.entrySet()) {
            int branchId = entry.getKey();
            List<Transaction> branchTransactions = entry.getValue();
            
            double income = branchTransactions.stream()
                    .filter(t -> "INCOME".equals(t.getType()))
                    .mapToDouble(Transaction::getAmount)
                    .sum();
                    
            double expenses = branchTransactions.stream()
                    .filter(t -> "EXPENSE".equals(t.getType()))
                    .mapToDouble(Transaction::getAmount)
                    .sum();
                    
            double profit = income - expenses;
            double profitMargin = income > 0 ? (profit / income) * 100 : 0;
            
            Map<String, Object> metrics = new HashMap<>();
            metrics.put("totalRevenue", income);
            metrics.put("totalExpenses", expenses);
            metrics.put("profit", profit);
            metrics.put("profitMargin", profitMargin);
            
            branchPerformance.put(branchId, metrics);
        }
        return branchPerformance;
    }

    public static void main(String[] args) {
        ReportService reportService = new ReportService();
        System.out.println(reportService.getDailyRevenueReport(LocalDate.parse("2025-05-01")));
    }
}
