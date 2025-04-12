package view;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;
import java.util.Date;
import java.util.List;
import java.util.Map;

public class RevenueChartView extends JFrame {
    private JLabel titleLabel;
    private JPanel chartPanel;
    private JPanel controlPanel;
    private JComboBox<String> chartTypeComboBox;
    private JComboBox<String> periodComboBox;
    private JLabel fromDateLabel, toDateLabel;
    private JSpinner fromDateSpinner, toDateSpinner;
    private JButton refreshButton, exportButton;
    
    public RevenueChartView() {
        initComponents();
        setupLayout();
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setSize(900, 600);
        setLocationRelativeTo(null);
        setTitle("Biểu đồ doanh thu");
    }
    
    private void initComponents() {
        titleLabel = new JLabel("Biểu đồ doanh thu");
        titleLabel.setFont(new Font("Arial", Font.BOLD, 18));
        
        // Chart panel
        chartPanel = new JPanel(new BorderLayout());
        chartPanel.setBorder(BorderFactory.createEtchedBorder());
        
        // Control components
        String[] chartTypes = {"Biểu đồ cột", "Biểu đồ đường", "Biểu đồ tròn"};
        chartTypeComboBox = new JComboBox<>(chartTypes);
        
        String[] periods = {"Theo ngày", "Theo tuần", "Theo tháng", "Theo quý", "Theo năm"};
        periodComboBox = new JComboBox<>(periods);
        
        fromDateLabel = new JLabel("Từ ngày:");
        toDateLabel = new JLabel("Đến ngày:");
        
        SpinnerDateModel fromModel = new SpinnerDateModel();
        fromDateSpinner = new JSpinner(fromModel);
        fromDateSpinner.setEditor(new JSpinner.DateEditor(fromDateSpinner, "dd/MM/yyyy"));
        
        SpinnerDateModel toModel = new SpinnerDateModel();
        toDateSpinner = new JSpinner(toModel);
        toDateSpinner.setEditor(new JSpinner.DateEditor(toDateSpinner, "dd/MM/yyyy"));
        
        refreshButton = new JButton("Cập nhật");
        exportButton = new JButton("Xuất biểu đồ");
        
        // Control panel
        controlPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        controlPanel.add(new JLabel("Loại biểu đồ:"));
        controlPanel.add(chartTypeComboBox);
        controlPanel.add(new JLabel("Thời gian:"));
        controlPanel.add(periodComboBox);
        controlPanel.add(fromDateLabel);
        controlPanel.add(fromDateSpinner);
        controlPanel.add(toDateLabel);
        controlPanel.add(toDateSpinner);
        controlPanel.add(refreshButton);
        controlPanel.add(exportButton);
    }
    
    private void setupLayout() {
        setLayout(new BorderLayout());
        
        JPanel headerPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        headerPanel.add(titleLabel);
        
        add(headerPanel, BorderLayout.NORTH);
        add(chartPanel, BorderLayout.CENTER);
        add(controlPanel, BorderLayout.SOUTH);
    }
    
    // Phương thức để hiển thị biểu đồ
    public void displayChart(JComponent chart) {
        chartPanel.removeAll();
        chartPanel.add(chart, BorderLayout.CENTER);
        chartPanel.revalidate();
        chartPanel.repaint();
    }
    
    // Phương thức để tạo biểu đồ cột đơn giản (dùng JPanel để mô phỏng)
    public JPanel createBarChart(Map<String, Double> data, String xAxisLabel, String yAxisLabel) {
        JPanel panel = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                Graphics2D g2d = (Graphics2D) g;
                
                int padding = 50;
                int labelPadding = 20;
                int width = getWidth() - 2 * padding;
                int height = getHeight() - 2 * padding;
                int barWidth = width / data.size() - 10;
                
                // Vẽ trục X và Y
                g2d.setColor(Color.BLACK);
                g2d.drawLine(padding, getHeight() - padding, padding, padding);
                g2d.drawLine(padding, getHeight() - padding, getWidth() - padding, getHeight() - padding);
                
                // Vẽ label của trục
                g2d.drawString(xAxisLabel, getWidth()/2 - 20, getHeight() - padding/2);
                
                // Xoay và vẽ label Y
                g2d.rotate(-Math.PI/2);
                g2d.drawString(yAxisLabel, -getHeight()/2 - 20, padding/2);
                g2d.rotate(Math.PI/2);
                
                // Tìm giá trị max để scale
                double maxValue = data.values().stream().max(Double::compare).orElse(0.0);
                
                // Vẽ các cột
                int x = padding + 5;
                for (Map.Entry<String, Double> entry : data.entrySet()) {
                    int barHeight = (int)((entry.getValue() / maxValue) * height);
                    
                    g2d.setColor(new Color(66, 134, 244));
                    g2d.fillRect(x, getHeight() - padding - barHeight, barWidth, barHeight);
                    
                    g2d.setColor(Color.BLACK);
                    g2d.drawRect(x, getHeight() - padding - barHeight, barWidth, barHeight);
                    
                    // Vẽ label
                    g2d.drawString(entry.getKey(), x, getHeight() - padding + labelPadding);
                    
                    // Vẽ giá trị
                    g2d.drawString(String.format("%.2f", entry.getValue()), 
                                   x, getHeight() - padding - barHeight - 5);
                    
                    x += barWidth + 10;
                }
            }
        };
        
        panel.setPreferredSize(new Dimension(800, 500));
        return panel;
    }
    
    // Phương thức để lấy thông tin từ form
    public String getSelectedChartType() {
        return (String) chartTypeComboBox.getSelectedItem();
    }
    
    public String getSelectedPeriod() {
        return (String) periodComboBox.getSelectedItem();
    }
    
    public Date getFromDate() {
        return (Date) fromDateSpinner.getValue();
    }
    
    public Date getToDate() {
        return (Date) toDateSpinner.getValue();
    }
    
    // Các phương thức set ActionListener
    public void setRefreshButtonListener(ActionListener listener) {
        refreshButton.addActionListener(listener);
    }
    
    public void setExportButtonListener(ActionListener listener) {
        exportButton.addActionListener(listener);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            RevenueChartView rc = new RevenueChartView();
            rc.setVisible(true);
        });
    }

}