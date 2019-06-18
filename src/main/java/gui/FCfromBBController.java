package gui;

import boxes.BigBox;
import boxes.BigBoxRowTableView;
import database.DB;
import flashcards.Flashcard;
import flashcards.FlashcardRowTableView;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Tab;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.Pane;
import javafx.scene.text.Text;

import javax.persistence.EntityManager;
import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

import static gui.ControllersCoordinator.*;
import static gui.ControllersCoordinator.stage;
import static users.MainCoordinator.loggedUser;
import static users.MainCoordinator.wordPattern;

public class FCfromBBController implements Initializable {
    ObservableList<FlashcardRowTableView> flashcardObservableList = FXCollections.observableArrayList();
    public static Long bigBoxId;
    BigBox bigBox;

    @Override
    public void initialize(URL location, ResourceBundle resources) {

        System.out.println("DOSTAŁEM ID: " + bigBoxId);
        EntityManager em =  DB.getInstance().getConnection();
        em.getTransaction().begin();
        bigBox = em.find(BigBox.class, bigBoxId);
        List<Flashcard> result = bigBox.getFlashcards();
        em.persist(bigBox);
        em.getTransaction().commit();
        em.close();
        bigBoxTitleText.setText(bigBox.getTitle());
        result.forEach(Flashcard -> {
            flashcardObservableList.add(new FlashcardRowTableView(Flashcard.getFlascardId(), Flashcard.getFrontSide(), Flashcard.getBackSide(), Long.toString(Flashcard.getSmallBoxNumber())));
        });
        frontsideColumn.setCellValueFactory(new PropertyValueFactory<>("frontSide"));
        backsideColumn.setCellValueFactory(new PropertyValueFactory<>("backSide"));
        smallboxNumberColumn.setCellValueFactory(new PropertyValueFactory<>("smallBoxNumber"));
        flashcardTable.setItems(flashcardObservableList);
    }

    @FXML
    private Text bigBoxTitleText;

    @FXML
    private TableView<FlashcardRowTableView> flashcardTable;

    @FXML
    private TableColumn<FlashcardRowTableView, String> frontsideColumn;

    @FXML
    private TableColumn<FlashcardRowTableView, String> backsideColumn;

    @FXML
    private TableColumn<FlashcardRowTableView, String> smallboxNumberColumn;

    @FXML
    private TextField frontsideField;

    @FXML
    private TextField backsideField;

    @FXML
    private Text errorMessage;

    @FXML
    void addFlashcard(ActionEvent event) {
        errorMessage.setVisible(false);
        if (wordPattern(frontsideField.getText()) && wordPattern(backsideField.getText())) {
            String frontSide = frontsideField.getText();
            String backSide = backsideField.getText();
            frontsideField.clear();
            backsideField.clear();
            Flashcard newFlashcard = bigBox.addFlashcard(frontSide, backSide);
            flashcardObservableList.add(new FlashcardRowTableView(newFlashcard.getFlascardId(), newFlashcard.getFrontSide(), newFlashcard.getBackSide(), "0"));
        }else{
            errorMessage.setText("Obydwie strony fiszki muszą zawierać od 2 do 20 znaków, bez znaków specjalnych");
            errorMessage.setVisible(true);
        }
    }

    @FXML
    void backButton(ActionEvent event) {
        changeScreen(WELCOMESCREEN);
    }

    @FXML
    void deleteButton(ActionEvent event) {
        ObservableList<FlashcardRowTableView> flashcardSelected, allFlashcards;
        allFlashcards = flashcardTable.getItems();
        flashcardSelected = flashcardTable.getSelectionModel().getSelectedItems();

        if (flashcardSelected.size() != 0){
            EntityManager em =  DB.getInstance().getConnection();
            em.getTransaction().begin();
            flashcardSelected.stream().forEach( Flashcard -> {
                bigBox.removeFlashcard(Flashcard.getFlashcardId());
            });
            flashcardSelected.forEach(allFlashcards::remove);
            em.getTransaction().commit();
            em.close();
        }else{
            errorMessage.setText("Nie wybrano fiszki");
            errorMessage.setVisible(true);
        }

    }

    @FXML
    void editButton(ActionEvent event) {
        errorMessage.setVisible(false);
        ObservableList<FlashcardRowTableView> flashcardSelected = flashcardTable.getSelectionModel().getSelectedItems();

        if (flashcardSelected.size() != 0) {
            flashcardSelected.stream().forEach(Flashcard ->
                    changeScreenEditBigBox(EDITFLASHCARD, Flashcard.getFrontSide(), Flashcard.getBackSide(), Flashcard.getFlashcardId())
            );
        }else{
            errorMessage.setText("Nie wybrano fiszki");
            errorMessage.setVisible(true);
        }

    }
    public void changeScreenEditBigBox(String FXMLpath, String frontSideOLD, String backSideOLD, long flashcardId){
        System.out.println("START");
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource(FXMLpath));
            root = (Pane) loader.load();
            editFlashcardController editFlashcardController = loader.getController();
            editFlashcardController.frontsideField.setText(frontSideOLD);
            editFlashcardController.backsideField.setText(backSideOLD);
            editFlashcardController.flashcardId = flashcardId;
            Scene scene = new Scene(root);
            stage.setScene(scene);
            stage.show();
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }


}
