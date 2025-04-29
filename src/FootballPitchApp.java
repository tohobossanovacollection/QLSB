import javax.swing.SwingUtilities;

import controller.BookingController;
import controller.CustomerController;
import controller.MainController;
import controller.PitchController;
import service.BookingService;
import service.CustomerService;
import service.PitchService;
import service.UserService;
import view.BookingView;
import view.CustomerView;
import view.LoginView;
import view.MainView;
import view.PitchView;

public class FootballPitchApp {
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            MainController mainController = new MainController(new MainView(),new LoginView());
            mainController.start();
        });
    }
}
