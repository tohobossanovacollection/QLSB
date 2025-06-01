package view;

import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;

public class tests {
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            // M
            MainView mainView = new MainView("ADMIN");
            mainView.setVisible(true);
            SupermarketPOSPanel view = new SupermarketPOSPanel();
            mainView.addPanel(view, "1");
            //view.setButtonForEdit(true);

            mainView.showPanel("1");
        });
    }
}
