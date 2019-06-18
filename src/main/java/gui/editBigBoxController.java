package gui;

import boxes.BigBox;
import database.DB;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;
import users.User;

import javax.persistence.EntityManager;
import javax.persistence.Query;

import java.util.List;

import static gui.ControllersCoordinator.*;
import static users.MainCoordinator.loggedUser;
import static users.MainCoordinator.wordPattern;

public class editBigBoxController {
    public long bigBoxId;

    @FXML
    public TextField nameField;

    @FXML
    public TextField categoryField;

    @FXML
    private Text errorMessage;

    @FXML
    void cancelButton(ActionEvent event) {
        changeScreen(MYBIGBOXES);
    }

    @FXML
    void removeAllFlashcardsButton(ActionEvent event) {
        EntityManager em =  DB.getInstance().getConnection();
        em.getTransaction().begin();
        BigBox bigBox = em.find(BigBox.class, bigBoxId);
        em.getTransaction().commit();
        em.close();
        bigBox.deleteAllFlashcards();
    }

    @FXML
    void saveButton(ActionEvent event) {
        if (wordPattern(nameField.getText()) && wordPattern(categoryField.getText())) {
            EntityManager em =  DB.getInstance().getConnection();
            em.getTransaction().begin();
            String hql = "select bb from BigBox bb where bb.title = :title AND bb.category = :category";
            Query query =em.createQuery(hql);
            query.setParameter("title", nameField.getText());
            query.setParameter("category", categoryField.getText());
            List<BigBox> result = query.getResultList();
            em.getTransaction().commit();
            em.close();
            if (result.size() > 0){
                errorMessage.setText("Istnieje już pudełko o takiej nazwie w tej samej kategorii");
                errorMessage.setVisible(true);
            }else{
                em = DB.getInstance().getConnection();
                em.getTransaction().begin();
                BigBox bigBox = em.find(BigBox.class, bigBoxId);
                bigBox.setTitle(nameField.getText());
                bigBox.setCategory(categoryField.getText());
                loggedUser = em.find(User.class, loggedUser.getUserId());
                em.getTransaction().commit();
                em.close();
                changeScreen(MYBIGBOXES);
            }
        }else{
        errorMessage.setText("Nazwa pudełka i kategoria muszą zawierać od 2 do 20 znaków, bez znaków specjalnych");
        errorMessage.setVisible(true);
    }
    }


}
