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
import javafx.scene.text.Text;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

import static gui.ControllersCoordinator.*;
import static users.MainCoordinator.*;

public class myBigBoxesController implements Initializable {
    ObservableList<BigBoxRowTableView> rowTableViewObservableList = FXCollections.observableArrayList();

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
    private Text errorMessage;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        //PRZYGOTOWANIE TABELI
        loggedUser.getUserBigBoxes().stream().forEach(BigBox -> {
            BigBoxRowTableView tmp = new BigBoxRowTableView(BigBox.getBigBoxId(), BigBox.getTitle(), BigBox.getCategory());
            rowTableViewObservableList.add(tmp);
        });

        //MAPUJEMY TABELE
        nameColumn.setCellValueFactory(new PropertyValueFactory<BigBoxRowTableView, String>("title"));
        categoryColumn.setCellValueFactory(new PropertyValueFactory<BigBoxRowTableView, String>("category"));
        boxesTable.setItems(rowTableViewObservableList);
    }

    @FXML
    void addButton(ActionEvent event) {
        errorMessage.setVisible(false);

        if (wordPattern(boxName.getText()) && wordPattern(boxCategory.getText())){

            EntityManager em =  DB.getInstance().getConnection();
            em.getTransaction().begin();
            String hql = "select bb from BigBox bb where bb.title = :title AND bb.category = :category";
            Query query =em.createQuery(hql);
            query.setParameter("title", boxName.getText());
            query.setParameter("category", boxCategory.getText());
            List<BigBox> result = query.getResultList();
            em.getTransaction().commit();
            em.close();

            if (result.size() > 0){
                errorMessage.setText("Istnieje już pudełko o takiej nazwie w tej samej kategorii");
                errorMessage.setVisible(true);
            }else {
                String name = boxName.getText();
                String category = boxCategory.getText();
                boxName.clear();
                boxCategory.clear();
                BigBox newBigBox = loggedUser.addBigBox(name, category);
                rowTableViewObservableList.add(new BigBoxRowTableView(newBigBox.getBigBoxId(),
                        newBigBox.getTitle(), newBigBox.getCategory()));
            }
        }else{
            errorMessage.setText("Nazwa pudełka i kategoria muszą zawierać od 2 do 20 znaków, bez znaków specjalnych");
            errorMessage.setVisible(true);
        }
    }

    @FXML
    void backButton(ActionEvent event) {
        changeScreen(WELCOME_SCREEN_FXML);
        errorMessage.setVisible(false);
    }

    @FXML
    void deleteButton(ActionEvent event) {
        errorMessage.setVisible(false);

        ObservableList<BigBoxRowTableView> bigBoxSelected, allBigBoxes;
        allBigBoxes = boxesTable.getItems();
        bigBoxSelected = boxesTable.getSelectionModel().getSelectedItems();

        if (bigBoxSelected.size() != 0){
            EntityManager em =  DB.getInstance().getConnection();
            em.getTransaction().begin();
            bigBoxSelected.stream().forEach( BigBox -> {
                loggedUser.removeBigBox(BigBox.getBigBoxId());
            });
            bigBoxSelected.forEach(allBigBoxes::remove);
            em.getTransaction().commit();
            em.close();
        }else{
            errorMessage.setText("Nie wybrano pudełka");
            errorMessage.setVisible(true);
        }
    }

    @FXML
    void editButton(ActionEvent event) {
        errorMessage.setVisible(false);

        //System.out.println("KLIKNIETO EDIT");
        ObservableList<BigBoxRowTableView> bigBoxSelected = boxesTable.getSelectionModel().getSelectedItems();
        //System.out.println("ROZMIAR: " + bigBoxSelected.size());

        if (bigBoxSelected.size() != 0) {
            bigBoxSelected.stream().forEach(BigBox -> {
                editBigBoxController.bigBoxId = BigBox.getBigBoxId();
                changeScreen(EDIT_BIG_BOX_FXML);
            });
        }else{
            errorMessage.setText("Nie wybrano pudełka");
            errorMessage.setVisible(true);
        }
    }
}
