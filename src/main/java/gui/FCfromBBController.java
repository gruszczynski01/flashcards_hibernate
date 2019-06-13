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
import javafx.fxml.Initializable;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.text.Text;

import javax.persistence.EntityManager;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

public class FCfromBBController implements Initializable {
    ObservableList<FlashcardRowTableView> flashcardObservableList = FXCollections.observableArrayList();
    public Long bigBoxId;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        EntityManager em =  DB.getInstance().getConnection();
        em.getTransaction().begin();
        BigBox bigBox = em.find(BigBox.class, bigBoxId);
        List<Flashcard> result = bigBox.getFlashcards();
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

    }

    @FXML
    void backButton(ActionEvent event) {

    }

    @FXML
    void deleteButton(ActionEvent event) {

    }

    @FXML
    void editButton(ActionEvent event) {

    }


}
