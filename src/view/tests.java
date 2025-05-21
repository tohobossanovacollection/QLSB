package view;

import javax.swing.JOptionPane;
import javax.swing.SwingUtilities;

public class tests {
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            MainView mainView = new MainView();
            mainView.setVisible(true);
            mainView.addPanel(new SettingView(), "1");
            mainView.showPanel("1");
            int select = JOptionPane.showOptionDialog(mainView, "Em co chac ko?", "xac nhan", 2, JOptionPane.QUESTION_MESSAGE, null, new Object[]{"Co", "Khong"}, "Co");
            System.out.println(select);
        });
    }
}
