package gui;

import boxes.BigBox;
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
import static users.MainCoordinator.wordPattern;

public class FCfromBBController implements Initializable {
    ObservableList<FlashcardRowTableView> flashcardObservableList = FXCollections.observableArrayList();
    public static Long bigBoxId;
    BigBox bigBox;

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

    @Override
    public void initialize(URL location, ResourceBundle resources) {

        //System.out.println("DOSTAŁEM ID: " + bigBoxId);
        EntityManager em =  DB.getInstance().getConnection();
        em.getTransaction().begin();
        bigBox = em.find(BigBox.class, bigBoxId);
        List<Flashcard> result = bigBox.getFlashcards();
        em.persist(bigBox);
        em.getTransaction().commit();
        em.close();

        bigBoxTitleText.setText(bigBox.getTitle());

        //DODANIE DO LISTY WIERSZY
        result.forEach(Flashcard -> {
            flashcardObservableList.add(new FlashcardRowTableView(Flashcard.getFlascardId(), Flashcard.getFrontSide(), Flashcard.getBackSide(), Long.toString(Flashcard.getSmallBoxNumber())));
        });

        //MAPOWANIE TABELI
        frontsideColumn.setCellValueFactory(new PropertyValueFactory<>("frontSide"));
        backsideColumn.setCellValueFactory(new PropertyValueFactory<>("backSide"));
        smallboxNumberColumn.setCellValueFactory(new PropertyValueFactory<>("smallBoxNumber"));

        //DODANIE LISTY WIERSZY DO TABELI
        flashcardTable.setItems(flashcardObservableList);
    }

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
        changeScreen(WELCOME_SCREEN_FXML);
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
                //USUWAM FISZKĘ Z PRZEGLĄDANEGO PUDEŁKA
                bigBox.removeFlashcard(Flashcard.getFlashcardId());
            });

            //USUWAM Z LISTY WSZYSTKICH FISZEK TĘ KTORA BYŁA ZAZNACZONA
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
            flashcardSelected.stream().forEach(Flashcard -> {
                        editFlashcardController.flashcardId = Flashcard.getFlashcardId();
                        changeScreen(EDIT_FLASHCARD_FXML);
                    }
            );
        }else{
            errorMessage.setText("Nie wybrano fiszki");
            errorMessage.setVisible(true);
        }

    }
}
