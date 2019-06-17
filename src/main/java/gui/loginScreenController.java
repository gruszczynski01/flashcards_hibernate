package gui;

import boxes.BigBox;
import database.DB;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import org.mindrot.jbcrypt.BCrypt;
import users.User;


import javax.persistence.EntityManager;
import javax.persistence.Query;
import java.io.IOException;
import java.util.List;


import static gui.ControllersCoordinator.*;
import static users.MainCoordinator.loggedUser;

public class loginScreenController {

    @FXML
    private TextField loginField;

    @FXML
    private PasswordField passwordField;

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
                    loggedUser = user;
                    changeScreen(WELCOMESCREEN);
                    loginField.clear();
                    passwordField.clear();

                }else{
                    System.out.println("Błędne hasełko");
                }
        });
    }
}
