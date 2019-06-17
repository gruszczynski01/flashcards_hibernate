package gui;

import boxes.BigBox;
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

import java.io.IOException;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;
import java.util.ResourceBundle;

import static gui.ControllersCoordinator.*;
import static users.MainCoordinator.loggedUser;

public class chooseBigBoxToLearnController implements Initializable {
    Map<String, Long> uniqueCategory = new HashMap<>();
    long currentID;
    public static boolean learnMode; //if false - selfcontrol | if true - write and check

    @SuppressWarnings("Duplicates")
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
            public void changed(ObservableValue<? extends String> observable, //
                                String oldValue, String newValue) {
                if (newValue != null && !newValue.equals("<Kategorie>")) {
                    bigBoxChoiceBox.getItems().remove(0, bigBoxChoiceBox.getItems().size());
                    BigBox.getBigBoxes(newValue).forEach(BigBox -> {
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

    @FXML
    private ChoiceBox<String> categoryChoiceBox;

    @FXML
    private ChoiceBox<String> bigBoxChoiceBox;

    @FXML
    void play(ActionEvent event) {
        if(learnMode == false){
            changeScreenToLearn(LEARNINGSELFCONTROL, currentID);
        }else{
            learningWriteAndCheck.chosenBigBoxId = currentID;
            changeScreen(LEARNINGWRITEANDCHECK);
        }
    }
    public void changeScreenToLearn(String FXMLpath, long bigBoxId){
        System.out.println("START");
        try {
            learningSelfcontrolController.chosenBigBoxId = bigBoxId;
            FXMLLoader loader = new FXMLLoader(getClass().getResource(FXMLpath));
            root = (Pane) loader.load();
            learningSelfcontrolController selfcontrolController = loader.getController();
            selfcontrolController.chosenBigBoxId = bigBoxId;
            Scene scene = new Scene(root);
            stage.setScene(scene);
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
            System.out.println("cos nie tak ze zmiana sceny");
            System.out.println(e.getMessage());
        }
    }

}
