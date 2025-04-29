package view.components;

import javax.swing.*;
import java.awt.*;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

public class TimeSlot extends JPanel {
    private static final int SLOT_HEIGHT = 30;
    private static final int HOUR_WIDTH = 60;
    private static final int START_HOUR = 5; // 5:00 AM
    private static final int END_HOUR = 23;  // 11:00 PM
    
    private List<BookingSlot> bookingSlots = new ArrayList<>();
    private Color availableColor = new Color(220, 255, 220);
    private Color bookedColor = new Color(255, 200, 200);
    private Color selectedColor = new Color(200, 220, 255);
    
    private LocalTime selectedStartTime;
    private LocalTime selectedEndTime;
    
    public TimeSlot() {
        setLayout(null);
        setPreferredSize(new Dimension(HOUR_WIDTH * (END_HOUR - START_HOUR + 1), SLOT_HEIGHT * 2));
        
        // Thêm nhãn giờ
        for (int i = START_HOUR; i <= END_HOUR; i++) {
            JLabel timeLabel = new JLabel(String.format("%02d:00", i), JLabel.CENTER);
            timeLabel.setBounds((i - START_HOUR) * HOUR_WIDTH, 0, HOUR_WIDTH, 20);
            add(timeLabel);
        }
        
        // Xử lý sự kiện chuột để chọn khung giờ
        addMouseListener(new java.awt.event.MouseAdapter() {
            @Override
            public void mousePressed(java.awt.event.MouseEvent evt) {
                int x = evt.getX();
                int hour = START_HOUR + (x / HOUR_WIDTH);
                int minute = (x % HOUR_WIDTH) * 60 / HOUR_WIDTH;
                
                // Làm tròn theo 30 phút
                if (minute < 30) {
                    minute = 0;
                } else {
                    minute = 30;
                }
                
                selectedStartTime = LocalTime.of(hour, minute);
                selectedEndTime = null;
                repaint();
            }
            
            @Override
            public void mouseReleased(java.awt.event.MouseEvent evt) {
                if (selectedStartTime != null) {
                    int x = evt.getX();
                    int hour = START_HOUR + (x / HOUR_WIDTH);
                    int minute = (x % HOUR_WIDTH) * 60 / HOUR_WIDTH;
                    
                    // Làm tròn theo 30 phút
                    if (minute < 30) {
                        minute = 0;
                    } else {
                        minute = 30;
                    }
                    
                    LocalTime endTime = LocalTime.of(hour, minute);
                    
                    // Đảm bảo thời gian kết thúc sau thời gian bắt đầu
                    if (endTime.isAfter(selectedStartTime)) {
                        selectedEndTime = endTime;
                    } else {
                        selectedEndTime = selectedStartTime.plusHours(1);
                    }
                    
                    repaint();
                    
                    // Thông báo chọn thời gian hoàn tất
                    firePropertyChange("timeSelected", null, new Object[]{selectedStartTime, selectedEndTime});
                }
            }
        });
    }
    
    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2d = (Graphics2D) g;
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        
        // Vẽ nền
        g2d.setColor(Color.WHITE);
        g2d.fillRect(0, 20, getWidth(), SLOT_HEIGHT);
        
        // Vẽ đường kẻ phân chia giờ
        g2d.setColor(Color.LIGHT_GRAY);
        for (int i = START_HOUR; i <= END_HOUR; i++) {
            int x = (i - START_HOUR) * HOUR_WIDTH;
            g2d.drawLine(x, 20, x, 20 + SLOT_HEIGHT);
        }
        
        // Vẽ đường kẻ phân chia 30 phút
        g2d.setColor(new Color(230, 230, 230));
        for (int i = START_HOUR; i <= END_HOUR; i++) {
            int x = (i - START_HOUR) * HOUR_WIDTH + HOUR_WIDTH / 2;
            g2d.drawLine(x, 20, x, 20 + SLOT_HEIGHT);
        }
        
        // Vẽ các slot đã đặt
        for (BookingSlot slot : bookingSlots) {
            int startX = calculateXPosition(slot.getStartTime());
            int endX = calculateXPosition(slot.getEndTime());
            
            g2d.setColor(bookedColor);
            g2d.fillRect(startX, 20, endX - startX, SLOT_HEIGHT);
            g2d.setColor(Color.RED);
            g2d.drawRect(startX, 20, endX - startX, SLOT_HEIGHT);
            
            // Vẽ thông tin đặt sân
            if (endX - startX > 50) {
                g2d.setColor(Color.BLACK);
                String timeText = slot.getStartTime().format(DateTimeFormatter.ofPattern("HH:mm")) +
                                  "-" + slot.getEndTime().format(DateTimeFormatter.ofPattern("HH:mm"));
                g2d.drawString(timeText, startX + 5, 20 + SLOT_HEIGHT / 2 + 5);
            }
        }
        
        // Vẽ vùng đang chọn
        if (selectedStartTime != null) {
            int startX = calculateXPosition(selectedStartTime);
            int endX = selectedEndTime != null ? 
                      calculateXPosition(selectedEndTime) : 
                      startX + HOUR_WIDTH;
            
            g2d.setColor(selectedColor);
            g2d.fillRect(startX, 20, endX - startX, SLOT_HEIGHT);
            g2d.setColor(Color.BLUE);
            g2d.drawRect(startX, 20, endX - startX, SLOT_HEIGHT);
            
            // Vẽ thông tin thời gian đang chọn
            if (endX - startX > 50) {
                g2d.setColor(Color.BLACK);
                String startText = selectedStartTime.format(DateTimeFormatter.ofPattern("HH:mm"));
                String endText = selectedEndTime != null ? 
                               selectedEndTime.format(DateTimeFormatter.ofPattern("HH:mm")) : 
                               "...";
                g2d.drawString(startText + "-" + endText, startX + 5, 20 + SLOT_HEIGHT / 2 + 5);
            }
        }
    }
    
    private int calculateXPosition(LocalTime time) {
        int hour = time.getHour();
        int minute = time.getMinute();
        
        return (hour - START_HOUR) * HOUR_WIDTH + (minute * HOUR_WIDTH / 60);
    }
    

    public void setBookingSlots(List<BookingSlot> slots) {
        this.bookingSlots = slots;
        repaint();
    }
    
    public void addBookingSlot(LocalTime startTime, LocalTime endTime, String info) {
        bookingSlots.add(new BookingSlot(startTime, endTime, info));
        repaint();
    }
    
    public void clearSelection() {
        selectedStartTime = null;
        selectedEndTime = null;
        repaint();
    }
    
    public LocalTime getSelectedStartTime() {
        return selectedStartTime;
    }
    
    public LocalTime getSelectedEndTime() {
        return selectedEndTime;
    }

    public String getTimeString() {
        if (selectedStartTime == null) {
            return "No time selected";
        }
        String startText = selectedStartTime.format(DateTimeFormatter.ofPattern("HH:mm"));
        String endText = selectedEndTime != null 
                         ? selectedEndTime.format(DateTimeFormatter.ofPattern("HH:mm")) 
                         : "...";
        return startText + " - " + endText;
    }
    
    // Class đại diện cho một slot đặt sân
    public static class BookingSlot {
        private LocalTime startTime;
        private LocalTime endTime;
        private String info;
        
        public BookingSlot(LocalTime startTime, LocalTime endTime, String info) {
            this.startTime = startTime;
            this.endTime = endTime;
            this.info = info;
        }
        
        public LocalTime getStartTime() {
            return startTime;
        }
        
        public LocalTime getEndTime() {
            return endTime;
        }
        
        public String getInfo() {
            return info;
        }
    }
}
