package gui;

import boxes.BigBox;
import boxes.BigBoxRowTableView;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.MenuButton;
import javafx.scene.control.MenuItem;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import users.MainCoordinator;

import java.net.URL;
import java.util.HashMap;
import java.util.Map;
import java.util.ResourceBundle;
import java.util.stream.Collectors;

import static users.MainCoordinator.loggedUser;

public class chooseBigBoxController implements Initializable {



    @FXML
    private Button showFCButton;

    @FXML
    private ChoiceBox<String> categoryChoiceBox;

    @FXML
    private ChoiceBox<?> bigBoxChoiceBox;

    @FXML
    void showFC(ActionEvent event) {

    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {

        ObservableList<String>  categoriesOL = FXCollections.observableArrayList();
        categoryChoiceBox.setItems(categoriesOL);
        bigBoxChoiceBox.setOpacity(0.4);
        categoryChoiceBox.getItems().add("<Kategorie>");
        categoryChoiceBox.getSelectionModel().select("<Kategorie>");
        //categoryChoiceBox.getItems().add("Kategorie");
        Map<String, Long> uniqueCategory = new HashMap<>();
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
                    System.out.println("STARA: " + oldValue + ", NOWA: " + newValue);

                    bigBoxChoiceBox.setOpacity(1);
                    bigBoxChoiceBox.show();
                }
            }
        };
        categoryChoiceBox.getSelectionModel().selectedItemProperty().addListener(changeListener);
    }


}
