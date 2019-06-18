package gui;

import boxes.BigBox;
import database.DB;
import javafx.animation.TranslateTransition;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.scene.text.Text;
import javafx.util.Duration;
import org.mindrot.jbcrypt.BCrypt;
import users.MainCoordinator;
import users.User;


import javax.persistence.EntityManager;
import javax.persistence.Query;
import java.io.IOException;
import java.util.List;


import static gui.ControllersCoordinator.*;
import static users.MainCoordinator.*;
import static users.MainCoordinator.loggedUser;

public class loginScreenController {

    @FXML
    private TextField loginField;

    @FXML
    private PasswordField passwordField;

    @FXML
    private Text wrongLoginOrPassword;

    @FXML
    private Text wrongLoginOrPasswordRegister;

    @FXML
    private AnchorPane registerPane;

    @FXML
    private TextField registerLogin;

    @FXML
    private PasswordField registerPassword;

    @FXML
    private PasswordField registerPasswordRepeat;

    @FXML
    private Button registerButton;

    @FXML
    void hideRegisterPanel(ActionEvent event) {
        TranslateTransition transition = new TranslateTransition();
        transition.setDuration(Duration.seconds(1));
        transition.setToX(620);
        transition.setNode(registerPane);
        transition.play();
    }

    @SuppressWarnings("Duplicates")
    @FXML
    void register(ActionEvent event) {
        if(wordPattern(registerLogin.getText()) && passwordPattern(registerPassword.getText())){
            System.out.println("Dane poprawnie zwalidowane pod wgledem regex");
            String hql = "SELECT user from User user where user.userName = :username";
            EntityManager em =  DB.getInstance().getConnection();
            em.getTransaction().begin();
            Query query =em.createQuery(hql);
            query.setParameter("username", registerLogin.getText());
            List result = query.getResultList();
            em.getTransaction().commit();
            em.close();
            if (result.size() == 0){
                if (registerPassword.getText().equals(registerPasswordRepeat.getText()))
                {

                    System.out.println("HASLA OKAY");
                    User.addUser(new User(registerLogin.getText(), registerPassword.getText()));
                    wrongLoginOrPasswordRegister.setVisible(false);
                    registerButton.setText("Zarejestrowano");
                }else{
                    wrongLoginOrPasswordRegister.setText("Hasła się różnią");
                    wrongLoginOrPasswordRegister.setVisible(true);

                }
            }else
            {
                wrongLoginOrPasswordRegister.setText("Ten login jest zajęty");
                wrongLoginOrPasswordRegister.setVisible(true);

            }
        }else{
            System.out.println("Dane  NIE poprawnie zwalidowane pod wgledem regex");
            if(!wordPattern(registerLogin.getText())){
                wrongLoginOrPasswordRegister.setText("Nieporawny login");
                wrongLoginOrPasswordRegister.setVisible(true);
            }else{
                wrongLoginOrPasswordRegister.setText("Niepoprawne hasło");
                wrongLoginOrPasswordRegister.setVisible(true);
            }
        }


    }

    @FXML
    void showRegisterPanel(ActionEvent event) {
        TranslateTransition transition = new TranslateTransition();
        transition.setDuration(Duration.seconds(1));
        transition.setToX(-620);
        transition.setNode(registerPane);
        transition.play();
    }

    @FXML
    void loginButtonAction(ActionEvent event) {
        System.out.println(loginField.getText());
        System.out.println(passwordField.getText());

        String hql = "SELECT user from User user where user.userName = :username";

        System.out.println(hql);

        EntityManager em =  DB.getInstance().getConnection();
        em.getTransaction().begin();
        Query query =em.createQuery(hql);
        query.setParameter("username", loginField.getText());
        List result = query.getResultList();
        System.out.println(result.size());
        em.getTransaction().commit();
        em.close();
        result.stream().forEach(  x ->
        {
                User user = (User)x;
                if(BCrypt.checkpw(passwordField.getText(), user.getPassword())){
                    System.out.println("Można logować");
                    wrongLoginOrPassword.setVisible(false);
                    loggedUser = user;
                    changeScreen(WELCOMESCREEN);
                    loginField.clear();
                    passwordField.clear();
                }else{
                    wrongLoginOrPassword.setText("Błędny login lub hasło");
                    wrongLoginOrPassword.setVisible(true);
                }
        });
    }
}
