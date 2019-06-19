import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import static gui.ControllersCoordinator.*;


/**
 * Główna klasa aplikacji, uruchamiamy w niej naszą apliakcję.
 */
public class Main extends Application {
    public static void main(String[] args) {
        launch(args);
    }

    /**
     * Metoda w ktorej ladujemy pierwsza scene naszej aplikacji.
     * @param primaryStage
     * @throws Exception
     */
    @Override
    public void start(Stage primaryStage) throws Exception {
        stage = primaryStage;
        stage.setResizable(false);
        root = FXMLLoader.load(this.getClass().getResource(LOGIN_SCREEN_FXML));
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }
}