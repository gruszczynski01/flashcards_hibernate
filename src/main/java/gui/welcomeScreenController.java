package gui;

import database.DB;
import javafx.animation.TranslateTransition;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.layout.Pane;
import javafx.util.Duration;
import users.User;
import javax.persistence.EntityManager;
import static gui.ControllersCoordinator.*;
import static users.MainCoordinator.*;

public class welcomeScreenController {

    @FXML
    private Pane backgroundPane;

    @FXML
    void logOutButton(ActionEvent event) {
        changeScreen(LOGIN_SCREEN_FXML);
    }

    @FXML
    void modifyAccount(ActionEvent event) {
        changeScreen(EDIT_USER_FXML);
    }

    @FXML
    void deleteAccount(ActionEvent event) {
        //USUWAM PROFIL UZYTKOWNIKA WRAZ Z JEGO PUDEŁKAMI I FISZKAMI
        EntityManager em =  DB.getInstance().getConnection();
        em.getTransaction().begin();
        em.remove(em.find(User.class, loggedUser.getUserId()));
        em.getTransaction().commit();
        em.close();
        changeScreen(LOGIN_SCREEN_FXML);
    }

    @FXML
    void showMyFlashcards(ActionEvent event) {
        changeScreen(CHOOSE_BIG_BOX_FXML);
    }

    @FXML
    void showMyBoxes(ActionEvent event) {
        changeScreen(MY_BIG_BOXES_FXML);
    }

    @FXML
    void writeAndCheck(ActionEvent event) {
        chooseBigBoxToLearnController.learnMode = true;
        changeScreen(CHOOSE_BIG_BOX_TO_LEARN_FXML);
    }

    @FXML
    void selfCheck(ActionEvent event) {
        chooseBigBoxToLearnController.learnMode = false;
        changeScreen(CHOOSE_BIG_BOX_TO_LEARN_FXML);
    }

    //PORUSZANIE SIĘ PO MENU GŁÓWNYM
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
