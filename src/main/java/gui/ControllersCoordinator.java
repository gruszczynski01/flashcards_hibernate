package gui;

import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

import java.io.IOException;

public class ControllersCoordinator {
    public static final int HEIGHT = 600;
    public static final int WIDTH = 800;
    public static Stage stage;
    public static Pane root;
    public static final String MYBIGBOXES = "/FXML/myBigBoxes.fxml";
    public static final String LOGINSCREEN = "/FXML/loginScreen.fxml";
    public static final String WELCOMESCREEN = "/FXML/welcomeScreen.fxml";
    public static final String EDITBIGBOX = "/FXML/editBigBox.fxml";
    public static final String CHOOSEBB = "/FXML/chooseBigBox.fxml";
    public static final String CHOOSEBBTOLEARN = "/FXML/chooseBigBoxToLearn.fxml";
    public static final String FCFROMBB = "/FXML/FFfromBB.fxml";
    public static final String EDITFLASHCARD = "/FXML/editFlashcard.fxml";
    public static final String LEARNINGSELFCONTROL = "/FXML/learningSelfcontrol.fxml";
    public static final String AFTERLEARNING = "/FXML/afterLearning.fxml";
    public static void changeScreen(String FXMLpath){
        try {
            root = FXMLLoader.load(ControllersCoordinator.class.getResource(FXMLpath));
            Scene scene = new Scene(root);
            stage.setScene(scene);
            stage.show();
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }



}
