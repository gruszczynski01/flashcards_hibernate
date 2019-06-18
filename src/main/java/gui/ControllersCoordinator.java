package gui;

import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

import java.io.IOException;
/**
 * Klasa zawierajaca wskazania na pliki html kolejnych scen, wskazanie na glowna Scene aplikacji oraz aktualny dla sceny
 * glowny root
 */
public class ControllersCoordinator {
    public static Stage stage;
    public static Pane root;

    public static final String LOGIN_SCREEN_FXML = "/FXML/loginScreen.fxml";
    public static final String WELCOME_SCREEN_FXML = "/FXML/welcomeScreen.fxml";
    public static final String MY_BIG_BOXES_FXML = "/FXML/myBigBoxes.fxml";
    public static final String FLASHCARDS_FROM_BIGBOX_FXML = "/FXML/FFfromBB.fxml";
    public static final String EDIT_BIG_BOX_FXML = "/FXML/editBigBox.fxml";
    public static final String EDIT_FLASHCARD_FXML = "/FXML/editFlashcard.fxml";
    public static final String EDIT_USER_FXML = "/FXML/editUser.fxml";
    public static final String CHOOSE_BIG_BOX_FXML = "/FXML/chooseBigBox.fxml";
    public static final String CHOOSE_BIG_BOX_TO_LEARN_FXML = "/FXML/chooseBigBoxToLearn.fxml";
    public static final String LEARNING_SELFCONTROL_FXML = "/FXML/learningSelfcontrol.fxml";
    public static final String LEARNING_WRITE_AND_CHECK_FXML = "/FXML/learningWriteAndCheck.fxml";
    public static final String AFTER_LEARNING_FXML = "/FXML/afterLearning.fxml";
    /**
     * Metoda sluzaca do zmiany sceny
     */
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
