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
    void addFlashcard(ActionEvent event) {
        String frontSide = frontsideField.getText();
        String backSide = backsideField.getText();
        frontsideField.clear();
        backsideField.clear();
        Flashcard newFlashcard = bigBox.addFlashcard(frontSide, backSide);
        flashcardObservableList.add(new FlashcardRowTableView(newFlashcard.getFlascardId(), newFlashcard.getFrontSide(), newFlashcard.getBackSide(), "0"));
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
        EntityManager em =  DB.getInstance().getConnection();
        em.getTransaction().begin();
        flashcardSelected.stream().forEach( Flashcard -> {
            bigBox.removeFlashcard(Flashcard.getFlashcardId());
        });
        flashcardSelected.forEach(allFlashcards::remove);
        em.getTransaction().commit();
        em.close();
    }

    @FXML
    void editButton(ActionEvent event) {
        ObservableList<FlashcardRowTableView> flashcardSelected = flashcardTable.getSelectionModel().getSelectedItems();
        flashcardSelected.stream().forEach( Flashcard ->
            changeScreenEditBigBox(EDITFLASHCARD, Flashcard.getFrontSide(), Flashcard.getBackSide(), Flashcard.getFlashcardId())
        );

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
