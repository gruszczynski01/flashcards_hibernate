package gui;

import database.DB;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import org.mindrot.jbcrypt.BCrypt;
import users.User;

import javax.persistence.EntityManager;
import javax.persistence.Query;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

import static gui.ControllersCoordinator.WELCOMESCREEN;
import static gui.ControllersCoordinator.changeScreen;
import static users.MainCoordinator.loggedUser;

public class editUserController implements Initializable {

    @FXML
    private TextField loginField;

    @FXML
    private PasswordField passwordField;

    @FXML
    private PasswordField repeatPasswordField;

    @FXML
    void cancelButton(ActionEvent event) {
        changeScreen(WELCOMESCREEN);
    }

    @FXML
    void saveButton(ActionEvent event) {
        String hql = "SELECT user from User user where user.userName = :username";
        EntityManager em =  DB.getInstance().getConnection();
        em.getTransaction().begin();
        Query query =em.createQuery(hql);
        query.setParameter("username", loginField.getText());
        List result = query.getResultList();
        em.getTransaction().commit();
        em.close();
        if (result.size() == 0 || loggedUser.getUserName().equals(loginField.getText())){
            if (passwordField.getText().equals(repeatPasswordField.getText()))
            {

                System.out.println("HASLA OKAY");
                em =  DB.getInstance().getConnection();
                em.getTransaction().begin();
                loggedUser = em.find(User.class, loggedUser.getUserId());
                loggedUser.setPassword(BCrypt.hashpw(passwordField.getText(), BCrypt.gensalt()));
                em.merge(loggedUser);
                em.getTransaction().commit();
                em.close();
                changeScreen(WELCOMESCREEN);
            }else{
                System.out.println("Hasla sie roznia");
            }
        }else
        {
            System.out.println("Nazwa użytkownika jest zajęta");
        }
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        loginField.setText(loggedUser.getUserName());
    }
}
