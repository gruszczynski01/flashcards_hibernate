package gui;

import boxes.BigBox;
import database.DB;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import org.mindrot.jbcrypt.BCrypt;
import users.User;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import java.util.List;

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
        //query.setParameter("password", BCrypt.hashpw(passwordField.getText(), BCrypt.gensalt()));
        List result = query.getResultList();
        System.out.println(result.size());
        //result.stream().forEach(x -> System.out.println(x));
        em.getTransaction().commit();

        result.stream().forEach(  x ->
        {
            //if(tmp.getClass() == User.class){
                User user = (User)x;
                if(BCrypt.checkpw(passwordField.getText(), user.getPassword())){
                    System.out.println("Można logować");
                }else{
                    System.out.println("Błędne hasełko");
                }
//            }else{
//                System.out.println("WRONG TYPE ERROR");
//                System.out.println("YOUR TYPE" + );
//            }

        });

    }

}
