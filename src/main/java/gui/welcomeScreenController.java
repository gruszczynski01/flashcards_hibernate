package gui;

import javafx.animation.TranslateTransition;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.util.Duration;
import users.MainCoordinator;

import static gui.ControllersCoordinator.*;
import static users.MainCoordinator.*;

public class welcomeScreenController {

    @FXML
    private ImageView backgroundImage;
    @FXML
    private Pane backgroundPane;

    @FXML
    void logOutButton(ActionEvent event) {
        changeScreen(LOGINSCREEN);
    }

    @FXML
    void modifyAccount(ActionEvent event) {
        System.out.println("login: " + loggedUser.getUserName());
        //editUserController.loginField.setText(loggedUser.getUserName());
        changeScreen(EDITUSER);
    }


    @FXML
    void addBigBox(ActionEvent event) {

    }

    @FXML
    void showIrregularVerbsBox(ActionEvent event) {
        changeScreen(CHOOSEBB);
    }

    @FXML
    void showMyBoxes(ActionEvent event) {
        changeScreen(MYBIGBOXES);
    }



    @FXML
    void writeAndCheck(ActionEvent event) {
        chooseBigBoxToLearnController.learnMode = true;
        changeScreen(CHOOSEBBTOLEARN);
    }

    @FXML
    void selfCheck(ActionEvent event) {
        chooseBigBoxToLearnController.learnMode = false;
        changeScreen(CHOOSEBBTOLEARN);

    }


    @FXML
    void buttonDown(ActionEvent event) {
        TranslateTransition transition = new TranslateTransition();
        transition.setDuration(Duration.seconds(1));
        transition.setToY(-600);
        transition.setNode(backgroundPane);
        transition.play();
    }

    @FXML
    void buttonDownBack(ActionEvent event) {
        TranslateTransition transition = new TranslateTransition();
        transition.setDuration(Duration.seconds(1));
        transition.setToY(0);
        transition.setNode(backgroundPane);
        transition.play();
    }

    @FXML
    void buttonLeft(ActionEvent event) {
        TranslateTransition transition = new TranslateTransition();
        transition.setDuration(Duration.seconds(1));
        transition.setToX(800);
        transition.setNode(backgroundPane);
        transition.play();
    }

    @FXML
    void buttonLeftBack(ActionEvent event) {
        TranslateTransition transition = new TranslateTransition();
        transition.setDuration(Duration.seconds(1));
        transition.setToX(0);
        transition.setNode(backgroundPane);
        transition.play();
    }

    @FXML
    void buttonRight(ActionEvent event) {
        TranslateTransition transition = new TranslateTransition();
        transition.setDuration(Duration.seconds(1));
        transition.setToX(-800);
        transition.setNode(backgroundPane);
        transition.play();
    }

    @FXML
    void buttonRightBack(ActionEvent event) {
        TranslateTransition transition = new TranslateTransition();
        transition.setDuration(Duration.seconds(1));
        transition.setToX(0);
        transition.setNode(backgroundPane);
        transition.play();
    }

    @FXML
    void buttonUp(ActionEvent event) {
        TranslateTransition transition = new TranslateTransition();
        transition.setDuration(Duration.seconds(1));
        transition.setToY(600);
        transition.setNode(backgroundPane);
        transition.play();
    }

    @FXML
    void buttonUpBack(ActionEvent event) {
        TranslateTransition transition = new TranslateTransition();
        transition.setDuration(Duration.seconds(1));
        transition.setToY(0);
        transition.setNode(backgroundPane);
        transition.play();
    }

}
