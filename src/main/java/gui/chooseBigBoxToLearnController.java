package gui;

import boxes.BigBox;
import database.DB;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.ChoiceBox;
import javafx.scene.layout.Pane;
import javafx.scene.text.Text;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import java.io.IOException;
import java.net.URL;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.ResourceBundle;

import static gui.ControllersCoordinator.*;
import static users.MainCoordinator.loggedUser;
/**
 * Kontroler sceny, na ktorej wybieramy Pudelko, z ktorego chcemy sie uczyc
 */
public class chooseBigBoxToLearnController implements Initializable {
    Map<String, Long> uniqueCategory = new HashMap<>();
    public static boolean learnMode;

    @FXML
    private ChoiceBox<String> categoryChoiceBox;

    @FXML
    private ChoiceBox<String> bigBoxChoiceBox;
    @FXML
    private Text errorMessage;
    /**
     * Metoda inicjalizacyjna, w ktorej wykonowyane sa czynnosci przygotowawcze do wygenerowania sceny
     */
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        ObservableList<String> categoriesOL = FXCollections.observableArrayList();
        categoryChoiceBox.setItems(categoriesOL);
        bigBoxChoiceBox.setOpacity(0.4);
        categoryChoiceBox.getItems().add("<Kategorie>");
        categoryChoiceBox.getSelectionModel().select("<Kategorie>");
        bigBoxChoiceBox.getItems().add("<Pudełka>");
        bigBoxChoiceBox.getSelectionModel().select("<Pudełka>");
        loggedUser.getUserBigBoxes().forEach( BigBox -> {
            uniqueCategory.put(BigBox.getCategory(), BigBox.getBigBoxId());
        });
        uniqueCategory.entrySet().forEach( x -> {
            categoryChoiceBox.getItems().add(x.getKey());
        });
        ChangeListener<String> changeListener = new ChangeListener<String>() {

            @Override
            public void changed(ObservableValue<? extends String> observable,
                                String oldValue, String newValue) {
                if (newValue != null && !newValue.equals("<Kategorie>")) {
                    bigBoxChoiceBox.getItems().remove(0, bigBoxChoiceBox.getItems().size());
                    BigBox.getBigBoxes(newValue).forEach(BigBox -> {
                        bigBoxChoiceBox.getItems().add(BigBox.getTitle());
                    });
                    bigBoxChoiceBox.setOpacity(1);
                    bigBoxChoiceBox.show();
                }
            }
        };
        categoryChoiceBox.getSelectionModel().selectedItemProperty().addListener(changeListener);
    }

    /**
     * Metoda rozpoczynajaca gre w wybranym uprrzednio trybie. Metoda sprawdza czy gra na wybranym pudelku jest mozliwa
     * oraz czy w ogole wybrano pudelko
     */
    @FXML
    void play(ActionEvent event) {
        try{
            EntityManager em =  DB.getInstance().getConnection();
            em.getTransaction().begin();
            String hql = "select bb from BigBox bb where bb.title = :title";
            Query query =em.createQuery(hql);
            query.setParameter("title", bigBoxChoiceBox.getValue());
            List<BigBox> result = query.getResultList();
            em.getTransaction().commit();
            em.close();

            if (result.get(0).getFlashcards().size() == 0){
                errorMessage.setText("W tym pudełku nie ma fiszek!");
                errorMessage.setVisible(true);
            }else{
                if(learnMode == false){
                    learningSelfcontrolController.chosenBigBoxId = result.get(0).getBigBoxId();
                    changeScreen(LEARNING_SELFCONTROL_FXML);
                }else{
                    learningWriteAndCheck.chosenBigBoxId = result.get(0).getBigBoxId();
                    changeScreen(LEARNING_WRITE_AND_CHECK_FXML);
                }
            }
        }catch (Exception ex){
            errorMessage.setText("Nie wybrano pudełka");
            errorMessage.setVisible(true);
        }
    }

    @FXML
    void back(ActionEvent event) {
        changeScreen(WELCOME_SCREEN_FXML);
    }

}
