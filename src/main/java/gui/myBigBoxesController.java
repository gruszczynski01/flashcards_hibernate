package gui;

import boxes.BigBox;
import boxes.BigBoxRowTableView;
import database.DB;
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

import javax.persistence.EntityManager;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import static gui.ControllersCoordinator.*;
import static users.MainCoordinator.*;

public class myBigBoxesController implements Initializable {
    ObservableList<BigBoxRowTableView> rowTableViewObservableList = FXCollections.observableArrayList();

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        loggedUser.getUserBigBoxes().stream().forEach(BigBox -> {
            BigBoxRowTableView tmp = new BigBoxRowTableView(BigBox.getBigBoxId(), BigBox.getTitle(), BigBox.getCategory());
            rowTableViewObservableList.add(tmp);
        });
        nameColumn.setCellValueFactory(new PropertyValueFactory<BigBoxRowTableView, String>("title"));
        categoryColumn.setCellValueFactory(new PropertyValueFactory<BigBoxRowTableView, String>("category"));
        boxesTable.setItems(rowTableViewObservableList);
    }
    @FXML
    private TextField boxName;

    @FXML
    private TextField boxCategory;
    @FXML
    private TableView<BigBoxRowTableView> boxesTable;

    @FXML
    private TableColumn<BigBoxRowTableView, String> nameColumn;

    @FXML
    private TableColumn<BigBoxRowTableView, String> categoryColumn;

    @FXML
    void addButton(ActionEvent event) {
        String name = boxName.getText();
        String category = boxCategory.getText();
        boxName.clear();
        boxCategory.clear();
        BigBox newBigBox = loggedUser.addBigBox(name, category);
        rowTableViewObservableList.add(new BigBoxRowTableView(newBigBox.getBigBoxId(),
                newBigBox.getTitle(), newBigBox.getCategory()));
        boxName.clear();
        boxCategory.clear();
    }

    @FXML
    void backButton(ActionEvent event) {
        changeScreen(WELCOMESCREEN);
    }

    @FXML
    void deleteButton(ActionEvent event) {
        ObservableList<BigBoxRowTableView> bigBoxSelected, allBigBoxes;
        allBigBoxes = boxesTable.getItems();
        bigBoxSelected = boxesTable.getSelectionModel().getSelectedItems();

        EntityManager em =  DB.getInstance().getConnection();
        em.getTransaction().begin();
        bigBoxSelected.stream().forEach( BigBox -> {
            //System.out.println("ZAZNACZONO OBIEKT O ID: " + BigBox.getBigBoxId());
            loggedUser.removeBigBox(BigBox.getBigBoxId());
        });
        bigBoxSelected.forEach(allBigBoxes::remove);
        em.getTransaction().commit();
        em.close();
    }

    @FXML
    void editButton(ActionEvent event) {
        System.out.println("KLIKNIETO EDIT");
        long id;
        ObservableList<BigBoxRowTableView> bigBoxSelected = boxesTable.getSelectionModel().getSelectedItems();
        System.out.println("ROZMIAR: " + bigBoxSelected.size());
        bigBoxSelected.stream().forEach( BigBox -> {
            changeScreenEditBigBox(EDITBIGBOX, BigBox.getTitle(), BigBox.getCategory(), BigBox.getBigBoxId());
        });
    }
    public void changeScreenEditBigBox(String FXMLpath, String nameOLD, String categoryOLD, long bigBoxId){
        System.out.println("START");
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource(FXMLpath));
            root = (Pane) loader.load();
            editBigBoxController editBigBoxController = loader.getController();
            editBigBoxController.nameField.setText(nameOLD);
            editBigBoxController.categoryField.setText(categoryOLD);
            editBigBoxController.bigBoxId = bigBoxId;
            Scene scene = new Scene(root);
            stage.setScene(scene);
            stage.show();
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }


}
