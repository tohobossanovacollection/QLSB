import javax.swing.SwingUtilities;
import controller.MainController;
import model.User;
public class FootballPitchApp {
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            // MainController mainController = new MainController();
            // mainController.start();
            MainController mainController = new MainController();
            mainController.start("admin1");
        });
    }
}

