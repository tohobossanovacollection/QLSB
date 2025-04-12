package view;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;
import java.util.Date;

public class ReportView extends JFrame {
    private JLabel titleLabel;
    private JComboBox<String> reportTypeComboBox;
    private JPanel dateFilterPanel;
    private JLabel fromDateLabel, toDateLabel;
    private JSpinner fromDateSpinner, toDateSpinner;
    private JButton generateButton, exportButton, printButton;
    private JPanel reportContentPanel;
    private JScrollPane reportScrollPane;
    
    public ReportView() {
        initComponents();
        setupLayout();
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setSize(900, 700);
        setLocationRelativeTo(null);
        setTitle("Báo cáo thống kê");
    }
    
    private void initComponents() {
        titleLabel = new JLabel("Báo cáo thống kê");
        titleLabel.setFont(new Font("Arial", Font.BOLD, 18));
        
        // Loại báo cáo
        String[] reportTypes = {
            "Doanh thu theo ngày", 
            "Doanh thu theo tháng", 
            "Doanh thu theo sân", 
            "Thống kê khách hàng", 
            "Thống kê hàng tồn kho"
        };
        reportTypeComboBox = new JComboBox<>(reportTypes);
        
        // Bộ lọc thời gian
        fromDateLabel = new JLabel("Từ ngày:");
        toDateLabel = new JLabel("Đến ngày:");
        
        SpinnerDateModel fromModel = new SpinnerDateModel();
        fromDateSpinner = new JSpinner(fromModel);
        fromDateSpinner.setEditor(new JSpinner.DateEditor(fromDateSpinner, "dd/MM/yyyy"));
        
        SpinnerDateModel toModel = new SpinnerDateModel();
        toDateSpinner = new JSpinner(toModel);
        toDateSpinner.setEditor(new JSpinner.DateEditor(toDateSpinner, "dd/MM/yyyy"));
        
        // Date filter panel
        dateFilterPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        dateFilterPanel.add(fromDateLabel);
        dateFilterPanel.add(fromDateSpinner);
        dateFilterPanel.add(toDateLabel);
        dateFilterPanel.add(toDateSpinner);
        
        // Buttons
        generateButton = new JButton("Tạo báo cáo");
        exportButton = new JButton("Xuất Excel");
        printButton = new JButton("In báo cáo");
        
        // Report content
        reportContentPanel = new JPanel();
        reportContentPanel.setLayout(new BorderLayout());
        reportScrollPane = new JScrollPane(reportContentPanel);
    }
    
    private void setupLayout() {
        setLayout(new BorderLayout());
        
        JPanel headerPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        headerPanel.add(titleLabel);
        
        JPanel filterPanel = new JPanel(new BorderLayout());
        JPanel reportTypePanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        reportTypePanel.add(new JLabel("Loại báo cáo:"));
        reportTypePanel.add(reportTypeComboBox);
        
        filterPanel.add(reportTypePanel, BorderLayout.NORTH);
        filterPanel.add(dateFilterPanel, BorderLayout.CENTER);
        
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        buttonPanel.add(generateButton);
        buttonPanel.add(exportButton);
        buttonPanel.add(printButton);
        
        filterPanel.add(buttonPanel, BorderLayout.SOUTH);
        
        add(headerPanel, BorderLayout.NORTH);
        add(filterPanel, BorderLayout.SOUTH);
        add(reportScrollPane, BorderLayout.CENTER);
    }
    
    // Phương thức để hiển thị nội dung báo cáo
    public void displayReportContent(JComponent content) {
        reportContentPanel.removeAll();
        reportContentPanel.add(content, BorderLayout.CENTER);
        reportContentPanel.revalidate();
        reportContentPanel.repaint();
    }
    
    // Phương thức để lấy thông tin báo cáo từ form
    public String getSelectedReportType() {
        return (String) reportTypeComboBox.getSelectedItem();
    }
    
    public Date getFromDate() {
        return (Date) fromDateSpinner.getValue();
    }
    
    public Date getToDate() {
        return (Date) toDateSpinner.getValue();
    }
    
    // Các phương thức set ActionListener
    public void setGenerateButtonListener(ActionListener listener) {
        generateButton.addActionListener(listener);
    }
    
    public void setExportButtonListener(ActionListener listener) {
        exportButton.addActionListener(listener);
    }
    
    public void setPrintButtonListener(ActionListener listener) {
        printButton.addActionListener(listener);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            ReportView reportView = new ReportView();
            reportView.setVisible(true);
        });
    }
}
