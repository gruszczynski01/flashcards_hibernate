package gui;

import database.DB;
import flashcards.Flashcard;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.TextField;
import javafx.scene.layout.Pane;
import javafx.scene.text.Text;
import users.User;

import javax.persistence.EntityManager;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import static gui.ControllersCoordinator.*;
import static gui.ControllersCoordinator.stage;
import static users.MainCoordinator.loggedUser;
import static users.MainCoordinator.wordPattern;

public class editFlashcardController implements Initializable {
    public static long flashcardId;

    @FXML
    public TextField frontsideField;

    @FXML
    public TextField backsideField;

    @FXML
    private Text errorMessage;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        EntityManager em = DB.getInstance().getConnection();
        em.getTransaction().begin();
        Flashcard editingFlashcard = em.find(Flashcard.class, flashcardId);
        em.getTransaction().commit();
        em.close();
        frontsideField.setText(editingFlashcard.getFrontSide());
        backsideField.setText(editingFlashcard.getBackSide());
    }

    @FXML
    void cancelButton(ActionEvent event) {
        changeScreen(WELCOME_SCREEN_FXML);
    }

    @FXML
    void saveButton(ActionEvent event) {
        errorMessage.setVisible(false);
        if (wordPattern(frontsideField.getText()) && wordPattern(backsideField.getText())) {
            EntityManager em = DB.getInstance().getConnection();
            em.getTransaction().begin();
            Flashcard flashcard = em.find(Flashcard.class, flashcardId);
            flashcard.setFrontSide(frontsideField.getText());
            flashcard.setBackSide(backsideField.getText());
            loggedUser = em.find(User.class, loggedUser.getUserId());
            em.getTransaction().commit();
            em.close();

            FCfromBBController.bigBoxId = flashcard.getBigBoxMother().getBigBoxId();
            changeScreen(FLASHCARDS_FROM_BIGBOX_FXML);
        }else{
            errorMessage.setText("Obydwie strony fiszki muszą zawierać od 2 do 20 znaków, bez znaków specjalnych");
            errorMessage.setVisible(true);
        }
    }
}
