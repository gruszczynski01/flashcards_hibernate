
import database.DB;
import gui.ControllersCoordinator;
import javafx.animation.TranslateTransition;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import javafx.util.Duration;
import org.mindrot.jbcrypt.BCrypt;
import users.User;

import javax.persistence.EntityManager;

import static gui.ControllersCoordinator.*;

public class Main extends Application {
    public static User loginUser;
    public static void main(String[] args) {
        launch(args);
//        SecureRandom random = new SecureRandom();


//
//        String login = "Franek";
//        String password = "haslo";
//        User current = new User(login, password);
//        User.addUser(current);
//        current.addBigBox("Maine fiszki", "mojmme");
//        current.addFlashcard(0, "AAAA", "aaaa");
//        current.addFlashcard(0, "BBBBB", "bbbbb");
//**************************************************************
//
//        EntityManager em =  DB.getInstance().getConnection();
//
//        em.getTransaction().begin();
//        User myUser = em.find(User.class, 6L);
//        BigBox bigBox = myUser.getUserBigBoxes().get(0);
//        em.persist(myUser);
//        em.getTransaction().commit();
//        bigBox.addFlashcard("Dupa", "dupa");
//
        //**************************************************************
//        em.close();
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        stage = primaryStage;
        root = FXMLLoader.load(this.getClass().getResource(LEARNINGSELFCONTROL));
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }
}