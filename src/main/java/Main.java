import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import static gui.ControllersCoordinator.*;

public class Main extends Application {
    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        stage = primaryStage;
        root = FXMLLoader.load(this.getClass().getResource(LOGIN_SCREEN_FXML));
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }
}