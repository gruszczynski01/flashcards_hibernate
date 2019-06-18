package gui;

import boxes.BigBox;
import boxes.BigBoxRowTableView;
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
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.MenuButton;
import javafx.scene.control.MenuItem;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.scene.text.Text;
import users.MainCoordinator;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import java.io.IOException;
import java.net.URL;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.ResourceBundle;
import java.util.stream.Collectors;

import static com.sun.javafx.scene.control.skin.Utils.getResource;
import static gui.ControllersCoordinator.*;
import static users.MainCoordinator.loggedUser;

public class chooseBigBoxController implements Initializable {
    Map<String, Long> uniqueCategory = new HashMap<>();
    long currentID = -1L;

    @FXML
    private Button showFCButton;

    @FXML
    private ChoiceBox<String> categoryChoiceBox;

    @FXML
    private ChoiceBox<String> bigBoxChoiceBox;
    @FXML
    private Text errorMessage;

    @FXML
    void showFC(ActionEvent event) {
        if (currentID == -1L){
            errorMessage.setVisible(true);
        }else{
            try{
                EntityManager em =  DB.getInstance().getConnection();
                em.getTransaction().begin();
                String hql = "select bb from BigBox bb where bb.title = :title";
                Query query =em.createQuery(hql);
                query.setParameter("title", bigBoxChoiceBox.getValue());
                List<BigBox> result = query.getResultList();
                em.getTransaction().commit();
                em.close();
                System.out.println("Przekazuje id rowne: " + result.get(0).getBigBoxId());
                changeScreenFlashcardsFromBigBox(FCFROMBB, result.get(0).getBigBoxId());
            }catch (Exception ex){
                System.out.println("wyjatek - nic nie wybrano");
                errorMessage.setVisible(true);
            }

        }

    }
    @FXML
    void backButton(ActionEvent event) {
        changeScreen(WELCOMESCREEN);
    }

    @SuppressWarnings("Duplicates")
    @Override
    public void initialize(URL location, ResourceBundle resources) {

        ObservableList<String>  categoriesOL = FXCollections.observableArrayList();
        categoryChoiceBox.setItems(categoriesOL);
        bigBoxChoiceBox.setOpacity(0.4);
        categoryChoiceBox.getItems().add("<Kategorie>");
        categoryChoiceBox.getSelectionModel().select("<Kategorie>");
        bigBoxChoiceBox.getItems().add("<Pudełka>");
        bigBoxChoiceBox.getSelectionModel().select("<Pudełka>");
        //categoryChoiceBox.getItems().add("Kategorie");
        //tu byla mapa
        loggedUser.getUserBigBoxes().forEach( BigBox -> {
            uniqueCategory.put(BigBox.getCategory(), BigBox.getBigBoxId());
        });
        uniqueCategory.entrySet().forEach( x -> {
            categoryChoiceBox.getItems().add(x.getKey());
        });
        ChangeListener<String> changeListener = new ChangeListener<String>() {

            @Override
            public void changed(ObservableValue<? extends String> observable, //
                                String oldValue, String newValue) {
                if (newValue != null && !newValue.equals("<Kategorie>")) {
                    bigBoxChoiceBox.getItems().remove(0, bigBoxChoiceBox.getItems().size());
                    BigBox.getBigBoxes(newValue).forEach( BigBox -> {
                        bigBoxChoiceBox.getItems().add(BigBox.getTitle());
                        currentID  = BigBox.getBigBoxId();
                    });
                    bigBoxChoiceBox.setOpacity(1);
                    bigBoxChoiceBox.show();

                }
            }
        };
        categoryChoiceBox.getSelectionModel().selectedItemProperty().addListener(changeListener);
    }
    public void changeScreenFlashcardsFromBigBox(String FXMLpath, long bigBoxId){
        System.out.println("DOSTALEM ID DO PRZEKAZANIA: " + bigBoxId);
        System.out.println("START");
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
