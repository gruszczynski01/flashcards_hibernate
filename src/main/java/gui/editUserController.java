package gui;

import database.DB;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;
import org.mindrot.jbcrypt.BCrypt;
import users.User;

import javax.persistence.EntityManager;
import javax.persistence.Query;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

import static gui.ControllersCoordinator.WELCOMESCREEN;
import static gui.ControllersCoordinator.changeScreen;
import static users.MainCoordinator.*;

public class editUserController implements Initializable {

    @FXML
    private TextField loginField;

    @FXML
    private PasswordField passwordField;

    @FXML
    private PasswordField repeatPasswordField;

    @FXML
    void cancelButton(ActionEvent event) {
        wrongLoginOrPassword.setVisible(false);
        changeScreen(WELCOMESCREEN);
    }
    @FXML
    private Text wrongLoginOrPassword;

    @FXML
    void saveButton(ActionEvent event) {
        if(wordPattern(loginField.getText()) && passwordPattern(passwordField.getText())) {
            System.out.println("Dane poprawnie zwalidowane pod wgledem regex");
            String hql = "SELECT user from User user where user.userName = :username";
            EntityManager em = DB.getInstance().getConnection();
            em.getTransaction().begin();
            Query query = em.createQuery(hql);
            query.setParameter("username", loginField.getText());
            List result = query.getResultList();
            em.getTransaction().commit();
            em.close();
            if (result.size() == 0 || loggedUser.getUserName().equals(loginField.getText())) {
                if (passwordField.getText().equals(repeatPasswordField.getText())) {

                    System.out.println("HASLA OKAY");
                    em = DB.getInstance().getConnection();
                    em.getTransaction().begin();
                    loggedUser = em.find(User.class, loggedUser.getUserId());
                    loggedUser.setPassword(BCrypt.hashpw(passwordField.getText(), BCrypt.gensalt()));
                    em.merge(loggedUser);
                    em.getTransaction().commit();
                    em.close();
                    wrongLoginOrPassword.setVisible(false);
                    changeScreen(WELCOMESCREEN);
                } else {
                    System.out.println("Hasla sie roznia");
                    wrongLoginOrPassword.setText("Hasła się różnią");
                    wrongLoginOrPassword.setVisible(true);
                }
            } else {
                System.out.println("Nazwa użytkownika jest zajęta");
                wrongLoginOrPassword.setText("Ta nazwa jest zajęta przez innego użytkownika");
                wrongLoginOrPassword.setVisible(true);
            }
        }else{
            System.out.println("Dane  NIE poprawnie zwalidowane pod wgledem regex");
            if(!wordPattern(loginField.getText())){
                wrongLoginOrPassword.setText("Nieporawny login");
                wrongLoginOrPassword.setVisible(true);
            }else{
                wrongLoginOrPassword.setText("Niepoprawne hasło");
                wrongLoginOrPassword.setVisible(true);
            }
        }
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        loginField.setText(loggedUser.getUserName());
    }
}
