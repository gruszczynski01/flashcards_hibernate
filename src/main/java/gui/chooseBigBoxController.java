package gui;

import boxes.BigBox;
import boxes.BigBoxRowTableView;
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
import users.MainCoordinator;

import java.io.IOException;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;
import java.util.ResourceBundle;
import java.util.stream.Collectors;

import static com.sun.javafx.scene.control.skin.Utils.getResource;
import static gui.ControllersCoordinator.*;
import static users.MainCoordinator.loggedUser;

public class chooseBigBoxController implements Initializable {
    Map<String, Long> uniqueCategory = new HashMap<>();
    long currentID;

    @FXML
    private Button showFCButton;

    @FXML
    private ChoiceBox<String> categoryChoiceBox;

    @FXML
    private ChoiceBox<String> bigBoxChoiceBox;

    @FXML
    void showFC(ActionEvent event) {
        System.out.println("BEDE Szukał dla: " + bigBoxChoiceBox.getValue());
        System.out.println("DLUGOSC MAPY: " + uniqueCategory.size());
        uniqueCategory.entrySet().forEach( x -> {
            System.out.println("KEY: " + x.getKey() + ", VAL: " + x.getValue());
        });
        System.out.println("ID to: " + uniqueCategory.get(bigBoxChoiceBox.getValue()));
        System.out.println("Przekazuje id rowne: " + currentID);
        changeScreenFlashcardsFromBigBox(FCFROMBB, currentID);
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
