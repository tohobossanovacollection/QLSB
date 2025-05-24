import javax.swing.SwingUtilities;
import controller.MainController;
public class FootballPitchApp {
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            MainController mainController = new MainController();
            mainController.start();
        });
    }
}
