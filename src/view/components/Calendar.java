package view.components;
//may not using this
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;
//import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.YearMonth;
import java.time.format.DateTimeFormatter;
//import java.time.format.TextStyle;
import java.util.Locale;

public class Calendar extends JPanel {
    private LocalDate currentDate;
    private LocalDate selectedDate;
    private JPanel calendarPanel;
    private JLabel monthYearLabel;
    private JButton[][] dayButtons;
    private ActionListener dateSelectionListener;

    public Calendar() {
        currentDate = LocalDate.now();
        selectedDate = currentDate;
        initComponents();
        updateCalendar();
    }

    private void initComponents() {
        setLayout(new BorderLayout());
        setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));

        // Tạo panel điều hướng (tháng/năm và các nút điều hướng)
        JPanel navigationPanel = new JPanel(new BorderLayout());
        
        JButton prevButton = new JButton("<");
        prevButton.addActionListener(e -> {
            currentDate = currentDate.minusMonths(1);
            updateCalendar();
        });
        
        JButton nextButton = new JButton(">");
        nextButton.addActionListener(e -> {
            currentDate = currentDate.plusMonths(1);
            updateCalendar();
        });
        
        monthYearLabel = new JLabel("", JLabel.CENTER);
        monthYearLabel.setFont(new Font(monthYearLabel.getFont().getName(), Font.BOLD, 14));
        
        JButton todayButton = new JButton("Hôm nay");
        todayButton.addActionListener(e -> {
            currentDate = LocalDate.now();
            selectedDate = currentDate;
            updateCalendar();
        });
        
        navigationPanel.add(prevButton, BorderLayout.WEST);
        navigationPanel.add(monthYearLabel, BorderLayout.CENTER);
        navigationPanel.add(nextButton, BorderLayout.EAST);
        
        JPanel navWithTodayPanel = new JPanel(new BorderLayout());
        navWithTodayPanel.add(navigationPanel, BorderLayout.CENTER);
        navWithTodayPanel.add(todayButton, BorderLayout.SOUTH);
        
        // Tạo panel chứa lịch
        calendarPanel = new JPanel(new GridLayout(7, 7, 2, 2));
        
        // Thêm tên các ngày trong tuần
        String[] dayNames = {"T2", "T3", "T4", "T5", "T6", "T7", "CN"};
        for (String dayName : dayNames) {
            JLabel label = new JLabel(dayName, JLabel.CENTER);
            label.setFont(new Font(label.getFont().getName(), Font.BOLD, 12));
            calendarPanel.add(label);
        }
        
        // Khởi tạo các nút ngày
        dayButtons = new JButton[6][7];
        for (int row = 0; row < 6; row++) {
            for (int col = 0; col < 7; col++) {
                dayButtons[row][col] = new JButton();
                dayButtons[row][col].setPreferredSize(new Dimension(40, 30));
                dayButtons[row][col].setMargin(new Insets(0, 0, 0, 0));
                dayButtons[row][col].setFocusPainted(false);
                
                //int finalRow = row;
                //int finalCol = col;
                dayButtons[row][col].addActionListener(e -> {
                    JButton source = (JButton) e.getSource();
                    if (!source.getText().isEmpty()) {
                        try {
                            int day = Integer.parseInt(source.getText());
                            selectedDate = LocalDate.of(
                                currentDate.getYear(),
                                currentDate.getMonth(),
                                day
                            );
                            updateCalendar();
                            
                            // Thông báo sự kiện chọn ngày
                            if (dateSelectionListener != null) {
                                dateSelectionListener.actionPerformed(e);
                            }
                        } catch (NumberFormatException ex) {
                            // Bỏ qua nếu không phải số
                        }
                    }
                });
                
                calendarPanel.add(dayButtons[row][col]);
            }
        }
        
        add(navWithTodayPanel, BorderLayout.NORTH);
        add(calendarPanel, BorderLayout.CENTER);
    }

    public void updateCalendar() {
        // Cập nhật tháng và năm
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MMMM yyyy", new Locale("vi", "VN"));
        monthYearLabel.setText(currentDate.format(formatter));
        
        // Lấy thông tin tháng hiện tại
        YearMonth yearMonth = YearMonth.of(currentDate.getYear(), currentDate.getMonth());
        int daysInMonth = yearMonth.lengthOfMonth();
        
        // Ngày đầu tiên của tháng
        LocalDate firstOfMonth = LocalDate.of(currentDate.getYear(), currentDate.getMonth(), 1);
        
        // Ngày trong tuần của ngày đầu tiên (0 = Monday, ... 6 = Sunday)
        int dayOfWeek = firstOfMonth.getDayOfWeek().getValue() - 1;
        
        // Xóa tất cả các ngày cũ
        for (int row = 0; row < 6; row++) {
            for (int col = 0; col < 7; col++) {
                dayButtons[row][col].setText("");
                dayButtons[row][col].setEnabled(false);
                dayButtons[row][col].setBackground(null);
            }
        }
        
        // Điền các ngày mới
        int day = 1;
        int row = 0;
        int column = dayOfWeek;
        
        while (day <= daysInMonth) {
            dayButtons[row][column].setText(String.valueOf(day));
            dayButtons[row][column].setEnabled(true);
            
            // Đánh dấu ngày được chọn
            LocalDate date = LocalDate.of(currentDate.getYear(), currentDate.getMonth(), day);
            if (date.equals(selectedDate)) {
                dayButtons[row][column].setBackground(new Color(66, 134, 244));
                dayButtons[row][column].setForeground(Color.WHITE);
            } else if (date.equals(LocalDate.now())) {
                dayButtons[row][column].setBackground(new Color(240, 240, 240));
                dayButtons[row][column].setForeground(Color.BLACK);
            } else {
                dayButtons[row][column].setBackground(null);
                dayButtons[row][column].setForeground(Color.BLACK);
            }
            
            day++;
            column++;
            if (column > 6) {
                column = 0;
                row++;
            }
        }
    }

    public void setDateSelectionListener(ActionListener listener) {
        this.dateSelectionListener = listener;
    }

    public LocalDate getSelectedDate() {
        return selectedDate;
    }

    public void setSelectedDate(LocalDate date) {
        this.selectedDate = date;
        this.currentDate = date;
        updateCalendar();
    }
}