package gui;

import javafx.fxml.FXML;

import javafx.event.ActionEvent;

import static gui.ControllersCoordinator.WELCOME_SCREEN_FXML;
import static gui.ControllersCoordinator.changeScreen;

public class afterLearningController {

    @FXML
    void goHome(ActionEvent event) {
        changeScreen(WELCOME_SCREEN_FXML);
    }

}
