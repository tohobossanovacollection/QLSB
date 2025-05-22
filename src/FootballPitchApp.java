import javax.swing.SwingUtilities;
import controller.MainController;
import view.LoginView;
import view.MainView;

public class FootballPitchApp {
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            MainController mainController = new MainController();
            mainController.start();
        });
    }
}
