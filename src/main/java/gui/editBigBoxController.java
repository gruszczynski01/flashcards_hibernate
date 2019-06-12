package gui;

import boxes.BigBox;
import database.DB;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import users.User;

import javax.persistence.EntityManager;

import static gui.ControllersCoordinator.*;
import static users.MainCoordinator.loggedUser;

public class editBigBoxController {
    public long bigBoxId;

    @FXML
    public TextField nameField;

    @FXML
    public TextField categoryField;

    @FXML
    void cancelButton(ActionEvent event) {
        changeScreen(MYBIGBOXES);
    }

    @FXML
    void removeAllFlashcardsButton(ActionEvent event) {
        //to do
    }

    @FXML
    void saveButton(ActionEvent event) {
        EntityManager em =  DB.getInstance().getConnection();
        em.getTransaction().begin();
        BigBox bigBox = em.find(BigBox.class, bigBoxId);
        bigBox.setTitle(nameField.getText());
        bigBox.setCategory(categoryField.getText());
        loggedUser = em.find(User.class, loggedUser.getUserId());
        em.getTransaction().commit();
        em.close();
        changeScreen(MYBIGBOXES);
    }

    @FXML
    void showFlashcardsButton(ActionEvent event) {
        //to do...
    }

}
