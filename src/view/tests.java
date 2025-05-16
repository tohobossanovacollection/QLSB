package view;

import javax.swing.SwingUtilities;

public class tests {
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            MainView mainView = new MainView();
            mainView.setVisible(true);
            mainView.addPanel(new ManageFieldsView(), "1");
            mainView.showPanel("1");
        });
    }
}
