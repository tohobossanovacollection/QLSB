package view;

import model.Pitch;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;
import java.util.Date;
import java.util.List;
import java.text.SimpleDateFormat;
import view.components.Calendar;
import view.components.TimeSlot;

public class PitchStatusView extends JPanel {
    private JPanel calendarPanel;
    private JPanel PitchsPanel;
    private JPanel timeSlotPanel;
    private JComboBox<String> viewModeComboBox;
    private JButton previousButton;
    private JButton nextButton;
    private JLabel dateRangeLabel;
    private Calendar calendarComponent;
    private List<TimeSlot> timeSlots;
    private Date currentDate;
    private SimpleDateFormat dateFormat;
    
    public PitchStatusView() {
        setLayout(new BorderLayout());
        
        // Top panel with controls
        JPanel controlPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 5));
        
        String[] viewModes = {"Ngày", "Tuần", "Tháng"};
        viewModeComboBox = new JComboBox<>(viewModes);
        
        previousButton = new JButton("<<");
        nextButton = new JButton(">>");
        dateRangeLabel = new JLabel("Hôm nay");
        
        controlPanel.add(previousButton);
        controlPanel.add(dateRangeLabel);
        controlPanel.add(nextButton);
        controlPanel.add(new JLabel("Chế độ xem:"));
        controlPanel.add(viewModeComboBox);
        
        add(controlPanel, BorderLayout.NORTH);
        
        // Calendar panel
        calendarPanel = new JPanel(new BorderLayout());
        calendarComponent = new Calendar();
        calendarPanel.add(calendarComponent, BorderLayout.CENTER);
        
        // Pitchs and time slots panel
        JPanel mainPanel = new JPanel(new BorderLayout());
        
        // Left side - Pitchs list
        PitchsPanel = new JPanel();
        PitchsPanel.setLayout(new BoxLayout(PitchsPanel, BoxLayout.Y_AXIS));
        JScrollPane PitchScrollPane = new JScrollPane(PitchsPanel);
        PitchScrollPane.setPreferredSize(new Dimension(150, 0));
        
        // Center - Time slots grid
        timeSlotPanel = new JPanel(new GridBagLayout());
        JScrollPane timeSlotScrollPane = new JScrollPane(timeSlotPanel);
        
        mainPanel.add(PitchScrollPane, BorderLayout.WEST);
        mainPanel.add(timeSlotScrollPane, BorderLayout.CENTER);
        
        add(calendarPanel, BorderLayout.CENTER);
        
        // Initially hide the calendar panel
        calendarPanel.setVisible(false);
        add(mainPanel, BorderLayout.CENTER);
        
        // Initialize current date and format
        currentDate = new Date();
        dateFormat = new SimpleDateFormat("dd/MM/yyyy");
        dateRangeLabel.setText(dateFormat.format(currentDate));
    }
    
    public void setPitchs(List<Pitch> Pitchs) {
        PitchsPanel.removeAll();
        
        // Add header for Pitchs column
        JLabel headerLabel = new JLabel("Danh sách sân");
        headerLabel.setFont(new Font("Arial", Font.BOLD, 14));
        headerLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        PitchsPanel.add(headerLabel);
        
        // Add Pitch names
        for (Pitch Pitch : Pitchs) {
            JLabel PitchLabel = new JLabel(Pitch.getName());
            PitchLabel.setFont(new Font("Arial", Font.PLAIN, 12));
            PitchLabel.setBorder(BorderFactory.createEmptyBorder(10, 5, 10, 5));
            PitchLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
            PitchsPanel.add(PitchLabel);
        }
        
        PitchsPanel.revalidate();
        PitchsPanel.repaint();
    }
    
    public void setTimeSlots(List<TimeSlot> slots) {
        timeSlotPanel.removeAll();
        this.timeSlots = slots;
        
        // Create time slot grid
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.fill = GridBagConstraints.BOTH;
        
        // Add header for time slots
        for (int i = 0; i < slots.size(); i++) {
            JLabel timeLabel = new JLabel(slots.get(i).getTimeString(), JLabel.CENTER);
            timeLabel.setFont(new Font("Arial", Font.BOLD, 12));
            timeLabel.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createMatteBorder(0, 0, 1, 1, Color.GRAY),
                BorderFactory.createEmptyBorder(5, 5, 5, 5)
            ));
            
            gbc.gridx = i + 1;
            timeSlotPanel.add(timeLabel, gbc);
        }
        
        timeSlotPanel.revalidate();
        timeSlotPanel.repaint();
    }
    
    public void updatePitchStatus(int PitchIndex, int timeSlotIndex, String status) {
        if (PitchIndex >= 0 && timeSlotIndex >= 0) {
            GridBagConstraints gbc = new GridBagConstraints();
            gbc.gridx = timeSlotIndex + 1;
            gbc.gridy = PitchIndex + 1;
            gbc.fill = GridBagConstraints.BOTH;
            
            JPanel slotPanel = new JPanel();
            slotPanel.setPreferredSize(new Dimension(80, 40));
            
            if (status.equals("available")) {
                slotPanel.setBackground(new Color(200, 255, 200)); // Light green
            } else if (status.equals("booked")) {
                slotPanel.setBackground(new Color(255, 200, 200)); // Light red
            } else if (status.equals("maintenance")) {
                slotPanel.setBackground(new Color(200, 200, 255)); // Light blue
            }
            
            slotPanel.setBorder(BorderFactory.createLineBorder(Color.GRAY));
            timeSlotPanel.add(slotPanel, gbc);
            
            timeSlotPanel.revalidate();
            timeSlotPanel.repaint();
        }
    }
    
    public String getViewMode() {
        return (String) viewModeComboBox.getSelectedItem();
    }
    
    public Date getCurrentDate() {
        return currentDate;
    }
    
    public void setCurrentDate(Date date) {
        this.currentDate = date;
        dateRangeLabel.setText(dateFormat.format(date));
    }
    
    public void setViewModeAction(ActionListener listener) {
        viewModeComboBox.addActionListener(listener);
    }
    
    public void setPreviousAction(ActionListener listener) {
        previousButton.addActionListener(listener);
    }
    
    public void setNextAction(ActionListener listener) {
        nextButton.addActionListener(listener);
    }
}
