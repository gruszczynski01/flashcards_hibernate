package gui;

import boxes.BigBox;
import database.DB;
import flashcards.Flashcard;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.TextField;
import javafx.scene.layout.Pane;
import javafx.scene.text.Text;
import users.User;

import javax.persistence.EntityManager;

import java.io.IOException;

import static gui.ControllersCoordinator.*;
import static gui.ControllersCoordinator.stage;
import static users.MainCoordinator.loggedUser;
import static users.MainCoordinator.wordPattern;

public class editFlashcardController {
    public long flashcardId;
    @FXML
    public TextField frontsideField;

    @FXML
    public TextField backsideField;

    @FXML
    private Text errorMessage;

    @FXML
    void cancelButton(ActionEvent event) {
        changeScreen(WELCOMESCREEN);
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
            changeScreenFlashcardsFromBigBox(FCFROMBB, flashcard.getBigBoxMother().getBigBoxId());
            //changeScreen(MYBIGBOXES);
        }else{
            errorMessage.setText("Obydwie strony fiszki muszą zawierać od 2 do 20 znaków, bez znaków specjalnych");
            errorMessage.setVisible(true);
        }
    }
    @SuppressWarnings("Duplicates")
    public void changeScreenFlashcardsFromBigBox(String FXMLpath, long bigBoxId){
        try {
            FCfromBBController.bigBoxId = bigBoxId;
            FXMLLoader loader = new FXMLLoader(getClass().getResource(FXMLpath));
            root = (Pane) loader.load();
            FCfromBBController fcfromBBController = loader.getController();
            fcfromBBController.bigBoxId = bigBoxId;
            Scene scene = new Scene(root);
            stage.setScene(scene);
            stage.show();
        } catch (IOException e) {
            System.out.println("exception: " + e.getMessage());
            e.printStackTrace();
        }
    }
}
