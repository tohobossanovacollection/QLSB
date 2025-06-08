package view;

import java.awt.*;
import java.text.NumberFormat;
import java.util.Locale;
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;
import com.toedter.calendar.JDateChooser;

public class ReportView extends JPanel {
  private JPanel mainPanel;
  private DefaultTableModel tableModel;
  private JTable reportTable;
  private NumberFormat currencyFormat;
  private String[] columns = { "Hình thức", "Mục đích", "Số tiền", "Mã sân", "Thời gian" };
  private JComboBox<String> pitchComboBox;
  private JDateChooser startDateChooser;
  private JDateChooser endDateChooser;

  public ReportView() {
    // Initialize components
    currencyFormat = NumberFormat.getInstance(new Locale("vi", "VN"));

    // Initialize table
    tableModel = new DefaultTableModel(columns, 0) {
      @Override
      public boolean isCellEditable(int row, int column) {
        return false;
      }
    };
    reportTable = new JTable(tableModel);

    // Set up the main layout
    setLayout(new BorderLayout());
    createMainPanel();
    createHeaderPanel();
    createContentPanel();
    createTablePanel();

    // Add the main panel to this panel
    add(mainPanel, BorderLayout.CENTER);
  }

  private void createMainPanel() {
    mainPanel = new JPanel();
    mainPanel.setLayout(new BorderLayout(20, 20));
    mainPanel.setBorder(new EmptyBorder(20, 20, 20, 20));
    mainPanel.setBackground(Color.WHITE);
  }

  private void createHeaderPanel() {
    JPanel headerPanel = new JPanel(new GridLayout(1, 3, 20, 0));
    headerPanel.setBackground(mainPanel.getBackground());

    // Create summary boxes with static/dummy values
    Color incomeColor = new Color(46, 204, 113); // Green for positive
    JPanel incomeBox = createSummaryBox("Doanh thu", "0đ", "Tổng doanh thu", incomeColor);
    JPanel bookingBox = createSummaryBox("Lượt đặt sân", "0", "Tổng lượt sử dụng", new Color(52, 152, 219));
    JPanel serviceBox = createSummaryBox("Dịch vụ", "0", "Tổng dịch vụ sử dụng", new Color(155, 89, 182));

    headerPanel.add(incomeBox);
    headerPanel.add(bookingBox);
    headerPanel.add(serviceBox);

    mainPanel.add(headerPanel, BorderLayout.NORTH);
  }

  private void createContentPanel() {
    JPanel contentPanel = new JPanel(new GridLayout(1, 2, 20, 0));
    contentPanel.setBackground(mainPanel.getBackground());

    // Left panel - Pitch Statistics
    JPanel pitchStatsPanel = createStyledPanel("Thống kê theo sân");
    JPanel pitchContent = new JPanel(new FlowLayout(FlowLayout.LEFT, 10, 10));
    pitchContent.setBackground(Color.WHITE);

    pitchComboBox = new JComboBox<>(new String[] { "Tất cả", "1", "2", "3", "4", "5", "6", "7", "8", "9", "10" });
    pitchComboBox.setPreferredSize(new Dimension(150, 30));

    JButton filterPitchButton = createStyledButton("Lọc");
    JButton resetPitchButton = createStyledButton("Reset");

    pitchContent.add(pitchComboBox);
    pitchContent.add(filterPitchButton);
    pitchContent.add(resetPitchButton);
    pitchStatsPanel.add(pitchContent, BorderLayout.CENTER);

    // Right panel - Time Statistics
    JPanel timeStatsPanel = createStyledPanel("Thống kê theo thời gian");
    JPanel timeContent = new JPanel(new FlowLayout(FlowLayout.LEFT, 10, 10));
    timeContent.setBackground(Color.WHITE);

    startDateChooser = new JDateChooser();
    endDateChooser = new JDateChooser();
    startDateChooser.setPreferredSize(new Dimension(120, 30));
    endDateChooser.setPreferredSize(new Dimension(120, 30));
    startDateChooser.setDateFormatString("dd/MM/yyyy");
    endDateChooser.setDateFormatString("dd/MM/yyyy");

    JButton filterDateButton = createStyledButton("Lọc");
    JButton resetDateButton = createStyledButton("Reset");

    timeContent.add(new JLabel("Từ:"));
    timeContent.add(startDateChooser);
    timeContent.add(new JLabel("Đến:"));
    timeContent.add(endDateChooser);
    timeContent.add(filterDateButton);
    timeContent.add(resetDateButton);
    timeStatsPanel.add(timeContent, BorderLayout.CENTER);

    contentPanel.add(pitchStatsPanel);
    contentPanel.add(timeStatsPanel);
    mainPanel.add(contentPanel, BorderLayout.CENTER);
  }

  private void createTablePanel() {
    JPanel tablePanel = new JPanel(new BorderLayout(0, 10));
    tablePanel.setBackground(mainPanel.getBackground());

    // Table title
    JLabel tableTitle = new JLabel("Chi tiết báo cáo", SwingConstants.LEFT);
    tableTitle.setFont(new Font("Arial", Font.BOLD, 16));
    tablePanel.add(tableTitle, BorderLayout.NORTH);

    // Table with scroll pane
    reportTable.setRowHeight(30);
    reportTable.setFont(new Font("Arial", Font.PLAIN, 12));
    reportTable.getTableHeader().setFont(new Font("Arial", Font.BOLD, 12));
    reportTable.getTableHeader().setBackground(new Color(240, 240, 240));
    reportTable.setSelectionBackground(new Color(232, 241, 249));
    reportTable.setSelectionForeground(Color.BLACK);
    reportTable.setShowGrid(true);
    reportTable.setGridColor(new Color(230, 230, 230));

    // Add table to scroll pane
    JScrollPane scrollPane = new JScrollPane(reportTable);
    scrollPane.setBorder(BorderFactory.createLineBorder(new Color(230, 230, 230)));
    scrollPane.setPreferredSize(new Dimension(800, 410)); // tăng chiều cao từ 300 lên 350
    tablePanel.add(scrollPane, BorderLayout.CENTER);

    mainPanel.add(tablePanel, BorderLayout.SOUTH);
  }

  private JPanel createSummaryBox(String title, String value, String subtitle, Color color) {
    JPanel panel = new JPanel();
    panel.setLayout(new BorderLayout());
    panel.setBackground(color);
    panel.setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));

    // Title
    JLabel titleLabel = new JLabel(title);
    titleLabel.setForeground(Color.WHITE);
    titleLabel.setFont(new Font("Arial", Font.BOLD, 14));

    // Value
    JLabel valueLabel = new JLabel(value);
    valueLabel.setForeground(Color.WHITE);
    valueLabel.setFont(new Font("Arial", Font.BOLD, 24));

    // Subtitle
    JLabel subtitleLabel = new JLabel(subtitle);
    subtitleLabel.setForeground(new Color(255, 255, 255, 200));
    subtitleLabel.setFont(new Font("Arial", Font.PLAIN, 12));

    // Layout
    JPanel textPanel = new JPanel(new GridLayout(3, 1, 5, 5));
    textPanel.setBackground(color);
    textPanel.add(titleLabel);
    textPanel.add(valueLabel);
    textPanel.add(subtitleLabel);

    textPanel.setOpaque(false);
    panel.add(textPanel, BorderLayout.CENTER);
    return panel;
  }

  private JPanel createStyledPanel(String title) {
    JPanel panel = new JPanel(new BorderLayout(10, 10));
    panel.setBackground(Color.WHITE);
    panel.setBorder(BorderFactory.createCompoundBorder(
        BorderFactory.createLineBorder(new Color(230, 230, 230)),
        BorderFactory.createEmptyBorder(15, 15, 15, 15)));

    JLabel titleLabel = new JLabel(title);
    titleLabel.setFont(new Font("Arial", Font.PLAIN, 14));
    panel.add(titleLabel, BorderLayout.NORTH);

    return panel;
  }

  private JButton createStyledButton(String text) {
    JButton button = new JButton(text);
    button.setPreferredSize(new Dimension(80, 30));
    button.setBackground(new Color(52, 152, 219));
    button.setForeground(Color.WHITE);
    button.setFocusPainted(false);
    button.setBorderPainted(false);
    button.setCursor(new Cursor(Cursor.HAND_CURSOR));
    return button;
  }

  public void updateTableData(Object[][] data) {
    tableModel.setRowCount(0);
    if (data != null) {
      for (Object[] row : data) {
        tableModel.addRow(row);
      }
    }
  }

  private void loadInitialData() {
    Object[][] exampleData = {
        { "INCOME", "BOOKING", "500,000đ", "1", "2024-03-20 10:00:00" },
        { "INCOME", "PRODUCT_SALE", "150,000đ", "1", "2024-03-20 11:30:00" },
        { "EXPENSE", "MAINTENANCE", "200,000đ", "2", "2024-03-20 14:00:00" }
    };
    updateTableData(exampleData);
  }

  // public static void main(String[] args) {
  //   SwingUtilities.invokeLater(() -> {
  //     try {
  //       UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
  //     } catch (Exception e) {
  //       e.printStackTrace();
  //     }
  //     JFrame frame = new JFrame("Report View");
  //     frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
  //     frame.add(new ReportView());
  //     frame.pack();
  //     frame.setLocationRelativeTo(null);
  //     frame.setVisible(true);
  //   });
  // }
}