import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Launcher extends Application {
    public static void main(String[] args) {
        Application.launch();
    }
    @Override
    public void start(Stage stage) throws Exception {
        Parent load = FXMLLoader.load(getClass().getResource("/view/startingPageForm.fxml"));
        stage.setScene(new Scene(load));
        stage.setTitle("Starting Page");
        stage.centerOnScreen();

        stage.show();
    }
}
